package com.gamingb3ast.GrassIsRicher.common.util;

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
    public String toString() {return "X: " + x + ", Z: " + z; }
}
