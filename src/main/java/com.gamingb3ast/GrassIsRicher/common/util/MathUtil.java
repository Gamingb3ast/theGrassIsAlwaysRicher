package com.gamingb3ast.GrassIsRicher.common.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class MathUtil {

    /**
     * TODO: Later pull from configs
     * The different methods for grabbing the points
     */
    private static final String spreadMethod = "RANDOM";
    public static Queue<CoordUtil> queue = new LinkedList<>();

    /**
     * Uses the selected spread method (in the com.gamingb3ast.GrassIsRicher.common.config)
     * to select a certain number of coordinates to add to
     * the queue for spreading.
     * @param player player, the selected player around which the grass will spread.
     * @param num A certain number of coordinates to add
     *            (will not always be exact as some methods require rounding)
     * @return CoordUtil, returns the next coordinates in the queue.
     */
    public static CoordUtil addCoords(EntityPlayer player, int num) {
        switch (spreadMethod) {
            case "RANDOM":
                for(int i = 0; i < num; i++)
                {
                    queue.add(
                                unitCircleCoords((int)(50+Math.random()*350), (int)(Math.random()*Math.PI*2))
                                .addXZ((int)player.posX, (int)player.posY)
                    );
                }
                break;
            case "SPLIT":
                //Divides the circle into 'num' sections and selects one per section
                break;
            case "PATCHES":
                //Gets closets round number to √'num'
                //   (if its 30, it gives us 5, if its 31, it gives us 6)
                //and creates √num patches with √num selected coords in each patch
                // This requires another variable in the configs for the size of the patch.
            case "SEQUENTIAL":
                //Grabs num amount of initial coordinates, then spreads from those coordinates
                //Each next patch will be a maximum of a certain distance away from previous (Use same com.gamingb3ast.GrassIsRicher.common.config variable as size of patch)
                //This needs more than just this one method, but this method will be a start.
                break;
        }
        return queue.peek();
    }


    public static CoordUtil unitCircleCoords(int radius, int radianAngle)
    {
        int x = radius*(int)Math.cos(radianAngle);
        int z = radius*(int)Math.sin(radianAngle);
        return new CoordUtil(x, z);
    }
}
