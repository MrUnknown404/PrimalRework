package mrunknown404.primalrework.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.helpers.StageHelper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class CommandStage extends CommandBase {
	
	@Override
	public String getName() {
		return "stage";
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 3;
	}
	
	@Override
	public String getUsage(ICommandSender sender) {
		return "commands.stage.usage";
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length == 2) {
			if (args[0].equalsIgnoreCase("set")) {
				for (EnumStage stage : EnumStage.values()) {
					if (args[1].equalsIgnoreCase(stage.toString())) {
						sender.sendMessage(new TextComponentString("Set stage to \"" + args[1] + "\""));
						StageHelper.setStage(stage);
						return;
					}
				}
			}
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("get")) {
				sender.sendMessage(new TextComponentString("Stage is \"" + StageHelper.getStage().getName() + "\""));
				return;
			}
		}
		
		throw new WrongUsageException(getUsage(sender), new Object[0]);
	}
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, Arrays.asList("set", "get")) :
				args.length == 2 && args[0].equalsIgnoreCase("set") ? getListOfStringsMatchingLastWord(args, EnumStage.getStringList()) : Collections.emptyList();
	}
}
