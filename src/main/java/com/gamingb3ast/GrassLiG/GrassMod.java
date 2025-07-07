package com.gamingb3ast.GrassLiG;

import com.gamingb3ast.GrassLiG.common.CommonProxy;
import com.gamingb3ast.GrassLiT.Tags;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = GrassMod.MODID, version = Tags.VERSION, name = "Grass! Let It Grow!", acceptedMinecraftVersions = "[1.7.10]")

public class GrassMod {
    public static final String MODID = "GrassLiG";
    public static File CONFIGFILE;
    public static final Logger LOG = LogManager.getLogger(MODID);

    @SidedProxy(serverSide = "com.gamingb3ast.GrassLiG.common.CommonProxy", clientSide = "com.gamingb3ast.GrassLiG.common.ClientProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }
    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }
}
