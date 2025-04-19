package com.gamingb3ast.GrassIsRicher.common.util;

import net.minecraft.entity.player.EntityPlayer;
import java.util.LinkedList;
import java.util.Queue;

public class MathUtil {

    /**
     * TODO: Later pull from configs
     * The different methods for grabbing the points
     */
    private static final String spreadMethod = "DEFAULT";
    private static final int patchDiameter = 20;
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
                    double radius = 20+(Math.random()*100);
                    double radian = Math.random()*(2*Math.PI);
                    System.out.println("Grass Gen: Radian = " + radian);
                    queue.add(
                                unitCircleCoords(radius, radian)
                                .addXZ((int)player.posX, (int)player.posZ)
                    );
                }
                break;
            case "SPLIT":
                //Divides the circle into 'num' sections and selects one per section
                /**
                 * ArcLength of each section
                 */
                float sectArcLen = (float) (2.00*Math.PI/num);
                for(int i = 0; i < num; i++)
                {
                    double radius = 20+(Math.random()*100);
                    double radian = (i*sectArcLen)+Math.random()*sectArcLen;
                    System.out.println("Grass Gen: Radian = " + radian);
                    queue.add(
                            unitCircleCoords(radius, radian)
                                    .addXZ((int)player.posX, (int)player.posZ)
                    );
                }
                break;
            case "PATCHES":
                /**Essentially more chunky and large RANDOM
                 * Gets closets round number to √'num'
                 *    (if its 30, it gives us 5, if its 31, it gives us 6)
                 * and creates √num patches with √num selected coords in each patch
                 * This requires another variable in the configs for the size of the patch.
                 */
                //Checks decimal and then rounds
                int size = (Math.sqrt(num)-(int)Math.sqrt(num) >=0.5  ? (int)(Math.sqrt(num)+1) : (int)Math.sqrt(num));
                System.out.println("Grass Gen: Size = " + size);
                for(int i = 0; i < size; i++) {
                    double radius = 20 + (Math.random() * 100);
                    double radian = Math.random() * (2 * Math.PI);

                    for(int j = 0; j < size; j++) {
                        queue.add(
                                unitCircleCoords(radius, radian)
                                        .addXZ((int) player.posX, (int) player.posZ).getRandomCoordInRange(patchDiameter)
                        );
                    }
                }
                

            case "SEQUENTIAL":
                //Grabs num amount of initial coordinates, then spreads from those coordinates
                //Each next patch will be a maximum of a certain distance away from previous (Use same com.gamingb3ast.GrassIsRicher.common.config variable as size of patch)
                //This needs more than just this one method, but this method will be a start.
                break;
        }
        return queue.peek();
    }


    public static CoordUtil unitCircleCoords(double radius, double radianAngle)
    {
        double x = radius*Math.cos(radianAngle);
        double z = radius*Math.sin(radianAngle);
        return new CoordUtil((int)x, (int)z);
    }
}
