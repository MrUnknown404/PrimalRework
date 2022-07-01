package mrunknown404.primalrework.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import mrunknown404.primalrework.init.InitQuests;
import mrunknown404.primalrework.quests.Quest;
import mrunknown404.primalrework.utils.helpers.WordH;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.entity.player.PlayerEntity;

public class CommandQuest {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		LiteralArgumentBuilder<CommandSource> cmd = Commands.literal("quest").requires((src) -> src.hasPermission(4));
		
		cmd.then(Commands.literal("finish").then(Commands.argument("quest", StringArgumentType.word()).suggests((src, builder) -> getSuggestions(builder, false))
				.executes((src) -> finishQuest(src, InitQuests.getFromName(StringArgumentType.getString(src, "quest"))))));
		cmd.then(Commands.literal("forget").then(Commands.argument("quest", StringArgumentType.word()).suggests((src, builder) -> getSuggestions(builder, true))
				.executes((src) -> forgetQuest(src, InitQuests.getFromName(StringArgumentType.getString(src, "quest"))))));
		
		dispatcher.register(cmd);
	}
	
	private static CompletableFuture<Suggestions> getSuggestions(SuggestionsBuilder builder, boolean wantFinished) {
		List<String> list = new ArrayList<String>();
		for (Quest quest : InitQuests.getQuests()) {
			if (quest.isFinished() == wantFinished) {
				list.add(quest.getName());
			}
		}
		return ISuggestionProvider.suggest(list, builder);
	}
	
	private static int finishQuest(CommandContext<CommandSource> src, Quest quest) {
		if (quest.isFinished()) {
			src.getSource().sendSuccess(WordH.translate("commands.quest.already_finished").append(WordH.string(" '" + quest.getName() + "'")), false);
			return 1;
		}
		
		quest.finishQuest(src.getSource().getServer().overworld(),
				src.getSource().getEntity() != null && src.getSource().getEntity() instanceof PlayerEntity ? (PlayerEntity) src.getSource().getEntity() : null);
		
		src.getSource().sendSuccess(WordH.translate("commands.quest.finish").append(WordH.string(" '" + quest.getName() + "' ")), false);
		return 1;
	}
	
	private static int forgetQuest(CommandContext<CommandSource> src, Quest quest) {
		if (!quest.isFinished()) {
			src.getSource().sendSuccess(WordH.translate("commands.quest.already_forgotten").append(WordH.string(" '" + quest.getName() + "'")), false);
			return 1;
		}
		
		quest.forgetQuest(src.getSource().getServer().overworld());
		src.getSource().sendSuccess(WordH.translate("commands.quest.forget").append(WordH.string(" '" + quest.getName() + "' ")), false);
		return 1;
	}
}
