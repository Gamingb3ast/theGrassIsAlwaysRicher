package com.gamingb3ast.GrassLiG.common.commands;

import java.util.Arrays;
import java.util.List;

import net.minecraft.block.BlockGrass;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.gamingb3ast.GrassLiG.Config;
import com.gamingb3ast.GrassLiG.common.util.CoordUtil;
import com.gamingb3ast.GrassLiG.common.util.MathUtil;

public class CommandsGrassMod extends CommandBase {

    @Override
    public String getCommandName() {
        return "grassMod";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/grassMod [subcommand] <value>(Optional)";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (sender instanceof EntityPlayer) {
            EntityPlayer player = getCommandSenderAsPlayer(sender);
            if (args.length > 0) {
                if (args[0].equals("simulateGrassPatches")) {
                    try {
                        int amount = parseInt(sender, args[1]);
                        MathUtil.addCoords(player, amount);
                        ChatComponentText message = new ChatComponentText(
                            "Beginning Grass patch simulation... Amount of patches:: " + amount);
                        message.getChatStyle()
                            .setColor(EnumChatFormatting.AQUA);
                        player.addChatMessage(message);
                        while (!MathUtil.queue.isEmpty()) {
                            CoordUtil coords = MathUtil.queue.peek();
                            World w = sender.getEntityWorld();
                            int y = coords.getTopBlockTypeY(w, Blocks.grass);
                            if (w.getBlock(coords.getX(), y, coords.getZ()) instanceof BlockGrass) {
                                Blocks.grass.func_149853_b(w, w.rand, coords.getX(), y, coords.getZ());
                                ChatComponentText message1 = new ChatComponentText("Growing at " + coords);
                                message1.getChatStyle()
                                    .setColor(EnumChatFormatting.RED);
                                player.addChatMessage(message1);
                                MathUtil.queue.poll();
                            }
                        }
                    } catch (IndexOutOfBoundsException e) {
                        ChatComponentText message = new ChatComponentText("Not enough arguments! Check command usage");
                        message.getChatStyle()
                            .setColor(EnumChatFormatting.RED);
                        player.addChatMessage(message);
                    } catch (NumberInvalidException e) {
                        ChatComponentText message = new ChatComponentText(
                            "Second argument needs to be an integer! Check command usage");
                        message.getChatStyle()
                            .setColor(EnumChatFormatting.RED);
                        player.addChatMessage(message);
                    }

                } else if (args[0].equals("setConfigValue")) {
                    try {
                        String name = args[1];
                        // TODO: See what can be done around this and strings (Try using Object class, see what happens
                        // when changed and read)
                        float value = (float) parseDouble(sender, args[2]);
                        Config.updateConfigProp(name, value, player);
                    } catch (IndexOutOfBoundsException e) {
                        ChatComponentText message = new ChatComponentText("Not enough arguments! Check command usage");
                        message.getChatStyle()
                            .setColor(EnumChatFormatting.RED);
                        player.addChatMessage(message);
                    } catch (NumberInvalidException e) {
                        // TODO: See what can be done around this and strings
                        ChatComponentText message = new ChatComponentText(
                            "Second argument needs to be an integer! Check command usage");
                        message.getChatStyle()
                            .setColor(EnumChatFormatting.RED);
                        player.addChatMessage(message);
                    }
                } else if (args[0].equals("resetConfigValue")) {
                    try {
                        String name = args[1];
                        Config.resetConfigProp(name, player);
                    } catch (IndexOutOfBoundsException e) {
                        ChatComponentText message = new ChatComponentText("Not enough arguments! Check command usage");
                        message.getChatStyle()
                            .setColor(EnumChatFormatting.RED);
                        player.addChatMessage(message);
                    }
                } else if (args[0].equals("getConfigValue")) {
                    try {
                        String name = args[1];
                        Config.getConfigProp(name, player);
                    } catch (IndexOutOfBoundsException e) {
                        ChatComponentText message = new ChatComponentText("Not enough arguments! Check command usage");
                        message.getChatStyle()
                            .setColor(EnumChatFormatting.RED);
                        player.addChatMessage(message);
                    }
                }
            } else {
                ChatComponentText message = new ChatComponentText(getCommandUsage(sender));
                message.getChatStyle()
                    .setColor(EnumChatFormatting.YELLOW);
                player.addChatMessage(message);
            }
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        switch (args.length) {
            case 1:
                return Arrays.asList("setConfigValue", "getConfigValue", "resetConfigValue", "simulateGrassPatches");
            case 2:
                if (args[0].equals("setConfigValue")) return Config.configuration.getCategory("general")
                    .getPropertyOrder();
                if (args[0].equals("simulateGrassPatches")) return Arrays.asList("0", "1", "2", "3", "4", "5", "etc.");
                if (args[0].equals("getConfigValue")) return Config.configuration.getCategory("general")
                    .getPropertyOrder();
                if (args[0].equals("resetConfigValue")) return Config.configuration.getCategory("general")
                    .getPropertyOrder();
        }
        return null;
    }
}
