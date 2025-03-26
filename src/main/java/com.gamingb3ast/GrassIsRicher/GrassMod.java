package com.gamingb3ast.GrassIsRicher;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
@Mod(modid = GrassMod.MODID, version = GrassMod.VERSION)
public class GrassMod {
    public static final String MODID = "GrassIsRicher";
    public static final String VERSION = "1.0";

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        System.out.println("The Grass Is Always Richer has been initialized!");
    }
}
