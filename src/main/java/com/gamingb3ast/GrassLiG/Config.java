package com.gamingb3ast.GrassLiG;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class Config extends Configuration {

    private static HashMap<String, Object> defaultValues = new HashMap<>() {

        {
            put("GrassGrowthSpeed", 0.5F);
            put("WeatherMultiplier", 1.0F);
            put("GrassSpreadDeadzone", 50F);
        }
    };
    public static float GrassGrowthSpeed = (float) defaultValues.get("GrassGrowthSpeed");
    public static float WeatherMultiplier = (float) defaultValues.get("WeatherMultiplier");;
    public static float GrassSpreadDeadzone = (float) defaultValues.get("GrassSpreadDeadzone");
    public static Configuration configuration;

    public Config(File file) {
        super(file);
    }

    public static void synchronizeConfiguration(File configFile) {
        configuration = new Config(configFile);

        GrassGrowthSpeed = configuration.getFloat(
            "GrassGrowthSpeed",
            Configuration.CATEGORY_GENERAL,
            GrassGrowthSpeed,
            0.01F,
            25.0F,
            "How fast the grass will grow, can be set by in-game command");
        WeatherMultiplier = configuration.getFloat(
            "WeatherMultiplier",
            Configuration.CATEGORY_GENERAL,
            WeatherMultiplier,
            0.0F,
            25.0F,
            "How much faster the grass will grow in rain, can be set by in-game command");
        GrassSpreadDeadzone = configuration.getFloat(
            "GrassSpreadDeadzone",
            Configuration.CATEGORY_GENERAL,
            GrassSpreadDeadzone,
            1.0F,
            500.0F,
            "How close to a player grass can generate (Grass cannot spawn within this distance to a player), can be set by in-game command");

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }

    public static void updateConfigProp(String propertyName, Object value, EntityPlayer player) {
        try {
            Field field = Config.class.getField(propertyName);
            configuration.removeCategory(configuration.getCategory("general"));
            field.set(null, value);
            configuration.save();
            synchronizeConfiguration(GrassMod.CONFIGFILE);
            ChatComponentText message = new ChatComponentText(
                "Updating Property " + propertyName + " New value:: " + field.get(null));
            message.getChatStyle()
                .setColor(EnumChatFormatting.AQUA);
            player.addChatMessage(message);
        } catch (Exception e) {
            ChatComponentText message = new ChatComponentText(e.getLocalizedMessage());
            message.getChatStyle()
                .setColor(EnumChatFormatting.RED);
            player.addChatMessage(message);
        }

    }

    public static void resetConfigProp(String propertyName, EntityPlayer player) {
        try {
            Field field = Config.class.getField(propertyName);
            configuration.removeCategory(configuration.getCategory("general"));
            field.set(null, defaultValues.get(propertyName));
            configuration.save();
            synchronizeConfiguration(GrassMod.CONFIGFILE);
            ChatComponentText message = new ChatComponentText(
                "Updating Property " + propertyName + " New value:: " + field.get(null));
            message.getChatStyle()
                .setColor(EnumChatFormatting.AQUA);
            player.addChatMessage(message);
        } catch (Exception e) {
            ChatComponentText message = new ChatComponentText(e.getLocalizedMessage());
            message.getChatStyle()
                .setColor(EnumChatFormatting.RED);
            player.addChatMessage(message);
        }
    }

    public static void getConfigProp(String propertyName, EntityPlayer player) {
        try {
            Field field = Config.class.getField(propertyName);
            ChatComponentText message = new ChatComponentText(
                "Current config value for " + propertyName + " is: " + field.get(null));
            message.getChatStyle()
                .setColor(EnumChatFormatting.AQUA);
            player.addChatMessage(message);
        } catch (Exception e) {
            ChatComponentText message = new ChatComponentText("Property does not exist: " + e.getLocalizedMessage());
            message.getChatStyle()
                .setColor(EnumChatFormatting.RED);
            player.addChatMessage(message);
        }
    }

    @Override
    public float getFloat(String name, String category, float defaultValue, float minValue, float maxValue,
        String comment) {
        GrassMod.LOG.info("Using overwritten method from: " + super.getClass());
        Property prop = this.get(category, name, Float.toString(defaultValue), name);
        prop.setLanguageKey(name);
        prop.comment = comment + " [range: "
            + minValue
            + " ~ "
            + maxValue
            + ", default: "
            + defaultValues.get(name)
            + "]";
        prop.setMinValue(minValue);
        prop.setMaxValue(maxValue);
        try {
            return Float.parseFloat(prop.getString()) < minValue ? minValue
                : (Float.parseFloat(prop.getString()) > maxValue ? maxValue : Float.parseFloat(prop.getString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }
}
