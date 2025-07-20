package com.gamingb3ast.GrassLiG.mixins;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
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
        @Local(name = "i1") int i1, @Local(name = "k1") int k1, @Local Block block) {

        if (worldIn.isRaining()) {// TODO Check it works with thunder
            for (int i = 0; i < 7; i++) {
                i1 = x + random.nextInt(3) - 1;
                k1 = z + random.nextInt(3) - 1;

                for (int y1 = y - 1; y1 < y + 2; y1++) {
                    if (1.0F / (random.nextFloat() * Config.WeatherMultiplier + 2) < 0.4F) {

                        if (worldIn.getBlock(i1, y1, k1) == Blocks.dirt && worldIn.getBlockMetadata(i1, y1, k1) == 0
                            && worldIn.getBlockLightValue(i1, y1 + 1, k1) >= 4
                            && worldIn.getBlockLightOpacity(i1, y1 + 1, k1) <= 2) {
                            worldIn.setBlock(i1, y1, k1, Blocks.grass);
                            return;
                        }
                    }
                }
            }
        }

        if (worldIn.getClosestPlayer(x, y, z, Config.GrassSpreadDeadzone) == null && block instanceof BlockAir) {
            for (int i = 0; i < Config.GrassGrowthChecks; i++) { // TODO TEST IF AFTER A WHILE THE WORLD LOOKS NICE AND
                                                                 // GRASSY
                int x1 = x + random.nextInt(Config.GrassCheckRange * 2 + 1) - (Config.GrassCheckRange);
                int y1 = y + random.nextInt(2) - 1;
                int z1 = z + random.nextInt(Config.GrassCheckRange * 2 + 1) - (Config.GrassCheckRange);
                block = worldIn.getBlock(x1, y1 + 1, z1);
                if (!(block instanceof BlockAir)) {
                    return;
                }
            }
            int bound = (int) (25.0F / Config.GrassGrowthSpeed
                * (worldIn.isRaining() ? 1.0F / (random.nextFloat() * Config.WeatherMultiplier + 1) : 1.0));
            if (bound == 0 || random.nextInt(bound) == 0) {
                Blocks.grass.func_149853_b(worldIn, random, x, y, z);
            }
        }

    }

}
