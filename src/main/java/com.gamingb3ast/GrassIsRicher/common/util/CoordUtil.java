package com.gamingb3ast.GrassIsRicher.common.util;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class CoordUtil {

    private int x;
    private int z;
    public CoordUtil(int x, int z)
    {
        this.x = x;
        this.z = z;
    }

    public CoordUtil addXZ(int x, int z) {
        this.x += x;
        this.z += z;
        return this;
    }
    public int getX() { return x; }
    public int getZ() { return z; }
    public int getTopBlockTypeY(World w, Block block)
    {
        int k;
        //todo: instead of configurable k value, have it be the player y level and then check a few blocks in either direction (configurable distance) 

//For example k=60, k+=1, k-=2, k+=3, k-=4 and so on until it checks k+=n where n is configurable
        for (k = 3; !(w.getBlock(x, k, z) == block); k++)
        {
            ;
        }

        return k;
    }
    public CoordUtil getRandomCoordInRange(int diameter)
    {
        return this.addXZ((int)(Math.random()*diameter)-(diameter/2), (int)(Math.random()*diameter)-(diameter/2));
    }
    public String toString() {return "X: " + x + ", Z: " + z; }
}
