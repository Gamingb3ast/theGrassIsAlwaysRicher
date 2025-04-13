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

        for (k = 3; !(w.getBlock(x, k, z) == block); k++)
        {
            ;
        }

        return k;
    }
    public String toString() {return "X: " + x + ", Z: " + z; }
}
