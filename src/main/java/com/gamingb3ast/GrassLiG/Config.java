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
            put("GrassGrowthSpeed", 0.7F);
            put("WeatherMultiplier", 2.3F);
            put("GrassSpreadDeadzone", 50F);
            put("GrassGrowthChecks", 7);
            put("GrassCheckRange", 10);
        }
    };
    public static float GrassGrowthSpeed = (float) defaultValues.get("GrassGrowthSpeed");
    public static float WeatherMultiplier = (float) defaultValues.get("WeatherMultiplier");;
    public static float GrassSpreadDeadzone = (float) defaultValues.get("GrassSpreadDeadzone");
    public static int GrassGrowthChecks = (int) defaultValues.get("GrassGrowthChecks");
    public static int GrassCheckRange = (int) defaultValues.get("GrassCheckRange");
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
        GrassGrowthChecks = configuration.getInt(
            "GrassGrowthChecks",
            Configuration.CATEGORY_GENERAL,
            GrassGrowthChecks,
            1,
            12,
            "How many times a random block is checked for existing grass (Higher values means less grass plants growing near each other)");
        GrassCheckRange = configuration.getInt(
            "GrassCheckRange",
            Configuration.CATEGORY_GENERAL,
            GrassCheckRange,
            1,
            12,
            "The radius around a grass block that are checked for non-air blocks (Higher values means greater distance between grass plants growing");
        if (configuration.hasChanged()) {
            configuration.save();
        }
    }

    public static void updateConfigProp(String propertyName, Object value, EntityPlayer player) {
        try {
            Field field = Config.class.getField(propertyName);
            configuration.removeCategory(configuration.getCategory("general"));
            field.set(null, convertType(field.getType(), value));
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

    private static Object convertType(Class<?> targetType, Object value) {
        if (targetType == Integer.class || targetType == int.class) {
            if (value instanceof Number) return ((Number) value).intValue();
            return Integer.parseInt(value.toString());
        } else if (targetType == Float.class || targetType == float.class) {
            if (value instanceof Number) return ((Number) value).floatValue();
            return Float.parseFloat(value.toString());
        } else if (targetType == Double.class || targetType == double.class) {
            if (value instanceof Number) return ((Number) value).doubleValue();
            return Double.parseDouble(value.toString());
        } else if (targetType == Boolean.class || targetType == boolean.class) {
            return Boolean.parseBoolean(value.toString());
        } else if (targetType == String.class) {
            return value.toString();
        }

        // fallback
        if (targetType.isAssignableFrom(value.getClass())) {
            return value;
        }

        throw new IllegalArgumentException("Cannot convert " + value.getClass() + " to " + targetType);
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

    public static void getPropComment(String propertyName, EntityPlayer player) {
        String comment = configuration.getCategory("general")
            .get(propertyName).comment;
        if (comment != null) {
            ChatComponentText message = new ChatComponentText(comment);
            message.getChatStyle()
                .setColor(EnumChatFormatting.GREEN);
            player.addChatMessage(message);
        } else {
            ChatComponentText message = new ChatComponentText("ERROR: PROPERTY DOES NOT EXIST, CHECK USAGE FOR INFO");
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
