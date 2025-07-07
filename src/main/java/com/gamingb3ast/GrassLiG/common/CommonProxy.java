package com.gamingb3ast.GrassLiG.common;

import com.gamingb3ast.GrassLiG.Config;
import com.gamingb3ast.GrassLiG.GrassMod;
import com.gamingb3ast.GrassLiG.common.commands.CommandsGrassMod;
import com.gamingb3ast.GrassLiT.Tags;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        Config.synchronizeConfiguration(event.getSuggestedConfigurationFile());
        GrassMod.CONFIGFILE = event.getSuggestedConfigurationFile();
        GrassMod.LOG.info("Hello! I'm \"Grass! Let it Grow!\" at version:: " + Tags.VERSION);
    }
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandsGrassMod());
        GrassMod.LOG.info("Hmmmm, what's this?? Commands?!");
    }

}

