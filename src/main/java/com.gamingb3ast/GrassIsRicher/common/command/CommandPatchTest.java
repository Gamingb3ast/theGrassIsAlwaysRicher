package com.gamingb3ast.GrassIsRicher.common.command;


import com.gamingb3ast.GrassIsRicher.common.util.CoordUtil;
import com.gamingb3ast.GrassIsRicher.common.util.MathUtil;
import net.minecraft.block.BlockGrass;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class CommandPatchTest extends CommandBase {
    @Override
    public String getCommandName() {
        return "simulateGrassTestPointGeneration";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "/simulateGrassTestPointGeneration <amount of grass patch to simulate>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if(sender instanceof EntityPlayer)
        {
            EntityPlayer player = getCommandSenderAsPlayer(sender);
            if(args.length > 0)
            {
                int amount = parseInt(sender, args[0]+"");
                player.addChatMessage(new ChatComponentText("Beginning Grass patch simulation... Amount of patches:: " + amount));
                MathUtil.addCoords(player, amount);
                while(!MathUtil.queue.isEmpty()) {
                    CoordUtil coords = MathUtil.queue.peek();
                    World w = sender.getEntityWorld();
                    int y = coords.getTopBlockTypeY(w, Blocks.grass);
                    if (w.getBlock(coords.getX(), y, coords.getZ()) instanceof BlockGrass)
                    {
                            Blocks.grass.func_149853_b(w, w.rand, coords.getX(), y, coords.getZ());
                            player.addChatMessage(new ChatComponentText("Growing at " + coords));
                            MathUtil.queue.poll();
                    }
                }
            }
            else
            {
                player.addChatMessage(new ChatComponentText(getCommandUsage(sender)));
            }
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        return args.length == 1 ? Arrays.asList("0", "1", "2", "3", "4","5", "etc.") : null;
    }
}
