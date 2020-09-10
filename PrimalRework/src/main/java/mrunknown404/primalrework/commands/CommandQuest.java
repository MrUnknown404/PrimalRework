package mrunknown404.primalrework.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import mrunknown404.primalrework.init.InitQuests;
import mrunknown404.primalrework.quests.Quest;
import mrunknown404.primalrework.util.ModConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class CommandQuest extends CommandBase {
	
	@Override
	public String getName() {
		return "quest";
	}
	
	@Override
	public String getUsage(ICommandSender sender) {
		return "commands.quest.usage";
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length == 2) {
			List<Quest> quests = new ArrayList<Quest>();
			if (!args[1].equalsIgnoreCase("*")) {
				quests.add(InitQuests.findQuest(args[1]));
			} else {
				quests = InitQuests.QUESTS;
			}
			
			int check = 0, amountOfQuests = 0;
			for (Quest q : quests) {
				if (q != null) {
					if (args[0].equalsIgnoreCase("finish")) {
						if (q.isFinished()) {
							if (!args[1].equalsIgnoreCase("*")) {
								sender.sendMessage(new TextComponentString("Quest '" + q.getName() + "' (" + q.getFancyName() + ") is already finished!"));
							}
						} else {
							amountOfQuests++;
							q.finishQuest(server.getEntityWorld(), (sender instanceof EntityPlayer) ? (EntityPlayer) sender : null);
							
							if (!args[1].equalsIgnoreCase("*")) {
								sender.sendMessage(new TextComponentString("Quest '" + q.getName() + "' (" + q.getFancyName() + ") was finished!"));
							}
						}
					} else if (args[0].equalsIgnoreCase("check")) {
						sender.sendMessage(
								new TextComponentString("Quest '" + q.getName() + "' (" + q.getFancyName() + ") is " + (q.isFinished() ? "finished!" : "not finished!")));
						check++;
						
						if (check == ModConfig.maxQuestsToCheck) {
							sender.sendMessage(new TextComponentString("Too many quests to check!"));
							return;
						}
						
					} else if (args[0].equalsIgnoreCase("forget")) {
						if (!q.isFinished()) {
							if (!args[1].equalsIgnoreCase("*")) {
								sender.sendMessage(
										new TextComponentString("Quest '" + q.getName() + "' (" + q.getFancyName() + ") cannot be forgotten because it was never finished!"));
							}
						} else {
							amountOfQuests++;
							q.forgetQuest(server.getEntityWorld());
							
							if (!args[1].equalsIgnoreCase("*")) {
								sender.sendMessage(new TextComponentString("Quest '" + q.getName() + "' (" + q.getFancyName() + ") was forgotten!"));
							}
						}
					}
				}
			}
			
			if (args[1].equalsIgnoreCase("*")) {
				if (args[0].equalsIgnoreCase("finish")) {
					sender.sendMessage(new TextComponentString(amountOfQuests + "/" + InitQuests.QUESTS.size() + " quests were completed!"));
				} else if (args[0].equalsIgnoreCase("forget")) {
					sender.sendMessage(new TextComponentString(amountOfQuests + "/" + InitQuests.QUESTS.size() + " quests were forgotten!"));
				}
			}
			
			return;
		}
		
		throw new WrongUsageException(getUsage(sender), new Object[0]);
	}
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		List<String> list = InitQuests.getAllQuestNames();
		list.add("*");
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, Arrays.asList("finish", "check", "forget")) :
				args.length == 2 ? getListOfStringsMatchingLastWord(args, list) : Collections.emptyList();
	}
}
