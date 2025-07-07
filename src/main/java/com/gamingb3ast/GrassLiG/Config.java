package com.gamingb3ast.GrassLiG;

import java.io.File;
import java.lang.reflect.Field;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.config.Configuration;

public class Config {
    public static float GrassGrowthSpeed = 0.5F;
    public static float GrassSpreadDeadzone = 50;
    public static Configuration configuration;


    public static void synchronizeConfiguration(File configFile) {
         configuration = new Configuration(configFile);

         GrassGrowthSpeed = configuration.getFloat("GrassGrowthSpeed", Configuration.CATEGORY_GENERAL, GrassGrowthSpeed, 0.01F, 25.0F, "How fast the grass will grow, can be set by in-game command");
         GrassSpreadDeadzone = configuration.getFloat("GrassSpreadDeadzone", Configuration.CATEGORY_GENERAL, GrassSpreadDeadzone, 1.0F, 500.0F, "How close to a player grass can generate (Grass cannot spawn within this distance to a player), can be set by in-game command");

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }

    public static void updateConfigProp(String propertyName, float value, EntityPlayer player)
    {
        try {
            Field field = Config.class.getField(propertyName);
            configuration.removeCategory(configuration.getCategory("general"));
            field.set(null, value);
            configuration.save();
            synchronizeConfiguration(GrassMod.CONFIGFILE);
            ChatComponentText message = new ChatComponentText("Updating Property " + propertyName + " New value:: " + field.get(null));
            message.getChatStyle().setColor(EnumChatFormatting.AQUA);
            player.addChatMessage(message);
        }
        catch (Exception e) {
            ChatComponentText message = new ChatComponentText(e.getLocalizedMessage());
            message.getChatStyle().setColor(EnumChatFormatting.RED);
            player.addChatMessage(message);
        }

    }

    public static void getConfigProp(String propertyName, EntityPlayer player) {
        try {
            Field field = Config.class.getField(propertyName);
            ChatComponentText message = new ChatComponentText("Current config value for " + propertyName + " is: " + field.get(null));
            message.getChatStyle().setColor(EnumChatFormatting.AQUA);
            player.addChatMessage(message);
        }
        catch (Exception e) {
            ChatComponentText message = new ChatComponentText("Property does not exist: " + e.getLocalizedMessage());
            message.getChatStyle().setColor(EnumChatFormatting.RED);
            player.addChatMessage(message);
        }
    }
}
