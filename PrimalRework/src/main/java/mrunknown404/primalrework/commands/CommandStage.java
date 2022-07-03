package mrunknown404.primalrework.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import mrunknown404.primalrework.init.InitRegistry;
import mrunknown404.primalrework.init.InitStages;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.helpers.StageH;
import mrunknown404.primalrework.utils.helpers.WordH;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class CommandStage {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		LiteralArgumentBuilder<CommandSource> cmd = Commands.literal("stage").requires((src) -> src.hasPermission(4));
		cmd.then(Commands.literal("get").executes(c -> getStage(c)));
		
		LiteralArgumentBuilder<CommandSource> set = Commands.literal("set");
		InitRegistry.getStages().stream().forEach((s) -> {
			if (s != InitStages.NO_SHOW) {
				set.then(Commands.literal(s.get().getNameID()).executes(c -> setStage(c, s.get())));
			}
		});
		
		cmd.then(set);
		dispatcher.register(cmd);
	}
	
	private static int setStage(CommandContext<CommandSource> source, Stage stage) {
		StageH.setStage(source.getSource().getServer().overworld(), stage);
		source.getSource().sendSuccess(WordH.translate("commands.stage.success").append(WordH.string(" " + stage.getName())), false);
		return 1;
	}
	
	private static int getStage(CommandContext<CommandSource> source) {
		source.getSource().sendSuccess(WordH.translate("commands.stage.current").append(WordH.string(" " + StageH.getStage().getName())), false);
		return 1;
	}
}
