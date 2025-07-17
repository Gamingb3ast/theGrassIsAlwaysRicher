package com.gamingb3ast.GrassLiG.mixins;

import java.util.Random;

import com.gamingb3ast.GrassLiG.GrassMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockGrass;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.gamingb3ast.GrassLiG.Config;
import com.llamalad7.mixinextras.sugar.Local;

@Mixin(BlockGrass.class)
public class MixinBlockGrass {

    @Inject(
        method = "updateTick",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;getBlock(III)Lnet/minecraft/block/Block;",
            ordinal = 1))
    private void spreadGrassMixin(World worldIn, int x, int y, int z, Random random, CallbackInfo ci,
        @Local Block block) {
        if (worldIn.getClosestPlayer(x, y, z, Config.GrassSpreadDeadzone) == null && block instanceof BlockAir) {
            boolean flag = true;
            for (int i = 0; i < 6; i++) { //TODO TEST IF AFTER A WHILE THE WORLD LOOKS NICE AND GRASSY
                int x1 = x + random.nextInt(7) - 3;
                int y1 = y + random.nextInt(2) - 1;
                int z1 = z + random.nextInt(7) - 3;
                block = worldIn.getBlock(x1, y1 + 1, z1);
                if (!(block instanceof BlockAir)) {
                    flag = false;
                }
            }
            if (flag && random.nextInt((int) (25.0F / Config.GrassGrowthSpeed * (worldIn.isRaining() ? Config.WeatherMultiplier : 1.0))) == 0) {
                Blocks.grass.func_149853_b(worldIn, random, x, y, z);
            }
        }

    }

     @Inject(
     method = "updateTick",
     at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/World;setBlock(IIILnet/minecraft/block/Block;)Z",
         ordinal = 1))
     private void increasteGrowthSpeedMixin(World worldIn, int x, int y, int z, Random random, CallbackInfo ci, @Local (name = "i1")  int i1, @Local(name = "j1") int j1, @Local(name = "k1") int k1) {
         if(worldIn.isRaining()) {//TODO Check it works with thunder
                 if(random.nextInt((int) (4.0F / Config.WeatherMultiplier)) == 0 || true) {
                     int xDiff = i1-x;
                     int zDiff = k1-z;
                     GrassMod.LOG.info("Growing rain");
                     for(int i = j1-1; i < j1+2; i++) {
                         if(worldIn.getBlock(i1 + xDiff, i, k1 + zDiff) == Blocks.dirt && worldIn.getBlockMetadata(i1 + xDiff, i, k1 + zDiff) == 0 && worldIn.getBlockLightValue(i1 + xDiff, i+1, k1 + zDiff) >= 4 && worldIn.getBlockLightOpacity(i1 + xDiff, i+1, k1 + zDiff) <= 2)
                            worldIn.setBlock(i1 + xDiff, i, k1 + zDiff, Blocks.grass);
                     }
                 }
             }
        }

}
