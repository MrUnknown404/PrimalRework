package mrunknown404.primalrework.mixin;

import java.util.concurrent.CompletableFuture;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.ItemParser;
import net.minecraft.item.Item;
import net.minecraft.tags.ITagCollection;
import net.minecraftforge.registries.ForgeRegistries;

@Mixin(ItemParser.class)
public class MixinItemParser {
	@Shadow
	public final boolean forTesting = false;
	
	//TODO redo this. i don't like it.
	
	@Inject(at = @At("HEAD"), method = "suggestItemIdOrTag(Lcom/mojang/brigadier/suggestion/SuggestionsBuilder;Lnet/minecraft/tags/ITagCollection;)Ljava/util/concurrent/CompletableFuture;", cancellable = true)
	private void suggestItemIdOrTag(SuggestionsBuilder builder, ITagCollection<Item> tags, CallbackInfoReturnable<CompletableFuture<Suggestions>> callable) {
		callable.setReturnValue(forTesting ? ISuggestionProvider.suggestResource(tags.getAvailableTags(), builder, String.valueOf('#')) :
				ISuggestionProvider.suggestResource(ForgeRegistries.ITEMS.getKeys().stream().filter((r) -> !r.getNamespace().equals("minecraft")), builder));
		
	}
}
