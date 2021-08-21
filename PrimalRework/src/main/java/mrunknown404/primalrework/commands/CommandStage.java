package mrunknown404.primalrework.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import mrunknown404.primalrework.helpers.StageH;
import mrunknown404.primalrework.helpers.WordH;
import mrunknown404.primalrework.utils.enums.EnumStage;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class CommandStage {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		LiteralArgumentBuilder<CommandSource> cmd = Commands.literal("stage").requires((commandSource) -> commandSource.hasPermission(4));
		cmd.then(Commands.literal("get").executes(c -> {
			return getStage(c);
		}));
		
		LiteralArgumentBuilder<CommandSource> set = Commands.literal("set");
		for (EnumStage stage : EnumStage.values()) {
			if (stage != EnumStage.no_show) {
				set.then(Commands.literal(stage.name()).executes(c -> {
					return setStage(c, stage);
				}));
			}
		}
		
		cmd.then(set);
		dispatcher.register(cmd);
	}
	
	private static int setStage(CommandContext<CommandSource> source, EnumStage stage) {
		StageH.setStage(stage);
		source.getSource().sendSuccess(WordH.translate("commands.stage.success").append(WordH.string(" " + stage.getName())), false);
		return 1;
	}
	
	private static int getStage(CommandContext<CommandSource> source) {
		source.getSource().sendSuccess(WordH.translate("commands.stage.current").append(WordH.string(" " + StageH.getStage().getName())), false);
		return 1;
	}
}
