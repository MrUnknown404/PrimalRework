package mrunknown404.primalrework.commands;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import mrunknown404.primalrework.quests.QuestState;
import mrunknown404.primalrework.utils.helpers.WordH;
import mrunknown404.primalrework.world.savedata.WSDQuestStates;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.entity.player.PlayerEntity;

public class CommandQuest {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		LiteralArgumentBuilder<CommandSource> cmd = Commands.literal("quest").requires((src) -> src.hasPermission(4));
		
		cmd.then(Commands.literal("finish").then(Commands.argument("quest", StringArgumentType.word()).suggests((src, builder) -> getSuggestions(src, builder, false))
				.executes((src) -> finishQuest(src, WSDQuestStates.getQuestState(StringArgumentType.getString(src, "quest"))))));
		cmd.then(Commands.literal("forget").then(Commands.argument("quest", StringArgumentType.word()).suggests((src, builder) -> getSuggestions(src, builder, true))
				.executes((src) -> forgetQuest(src, WSDQuestStates.getQuestState(StringArgumentType.getString(src, "quest"))))));
		
		dispatcher.register(cmd);
	}
	
	private static CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSource> src, SuggestionsBuilder builder, boolean wantFinished) {
		return ISuggestionProvider.suggest(
				WSDQuestStates.get(src.getSource().getServer()).stream().filter(q -> q.isFinished() == wantFinished).map((q) -> q.getName()).collect(Collectors.toList()), builder);
	}
	
	private static int finishQuest(CommandContext<CommandSource> src, QuestState quest) {
		if (quest.isFinished()) {
			src.getSource().sendSuccess(WordH.translate("commands.quest.already_finished").append(WordH.string(" '" + quest.getName() + "'")), false);
			return 1;
		}
		
		quest.finishQuest(src.getSource().getLevel(),
				src.getSource().getEntity() != null && src.getSource().getEntity() instanceof PlayerEntity ? (PlayerEntity) src.getSource().getEntity() : null);
		
		src.getSource().sendSuccess(WordH.translate("commands.quest.finish").append(WordH.string(" '" + quest.getName() + "' ")), false);
		return 1;
	}
	
	private static int forgetQuest(CommandContext<CommandSource> src, QuestState quest) {
		if (!quest.isFinished()) {
			src.getSource().sendSuccess(WordH.translate("commands.quest.already_forgotten").append(WordH.string(" '" + quest.getName() + "'")), false);
			return 1;
		}
		
		quest.forgetQuest(src.getSource().getLevel());
		src.getSource().sendSuccess(WordH.translate("commands.quest.forget").append(WordH.string(" '" + quest.getName() + "' ")), false);
		return 1;
	}
}
