package com.gamingb3ast.GrassLiG.mixins;


import com.gamingb3ast.GrassLiG.Config;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockGrass;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import java.util.Random;


@Mixin(BlockGrass.class)
public class MixinBlockGrass {



    @Inject(method = "updateTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getBlock(III)Lnet/minecraft/block/Block;"))
    private void spreadGrassMixin(World worldIn, int x, int y, int z, Random random, CallbackInfo ci, @Local Block block) {
        if (worldIn.getClosestPlayer(x, y, z, Config.GrassSpreadDeadzone) == null && block instanceof BlockAir) {
            boolean flag = true;
            for (int i = 0; i < 6; i++) {
                int x1 = x + random.nextInt(7)-3;
                int y1 = y + random.nextInt(2) - 1;
                int z1 = z + random.nextInt(7)-3;
                block = worldIn.getBlock(x1, y1+1, z1);
                if(!(block instanceof BlockAir)) {
                    flag = false;
                }
            }
            if(flag && random.nextInt((int) (25.0F / Config.GrassGrowthSpeed)) == 0) {
                Blocks.grass.func_149853_b(worldIn, random, x, y, z);
            }
        }
    }



}
