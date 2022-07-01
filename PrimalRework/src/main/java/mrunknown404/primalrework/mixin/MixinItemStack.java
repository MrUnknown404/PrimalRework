package mrunknown404.primalrework.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mrunknown404.primalrework.items.raw.StagedItem;
import mrunknown404.primalrework.utils.helpers.StageH;
import mrunknown404.primalrework.utils.helpers.WordH;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

@Mixin(ItemStack.class)
public class MixinItemStack {
	private static final ITextComponent UNKNOWN_ITEM = WordH.string("???");
	
	@Inject(at = @At("HEAD"), method = "getHoverName()Lnet/minecraft/util/text/ITextComponent;", cancellable = true)
	public void getHoverName(CallbackInfoReturnable<ITextComponent> callback) {
		Item item = getItem();
		if (item instanceof StagedItem) {
			StagedItem si = (StagedItem) item;
			if (!StageH.hasAccessToStage(si.stage.get())) {
				callback.setReturnValue(UNKNOWN_ITEM);
			}
		} else {
			callback.setReturnValue(UNKNOWN_ITEM);
		}
	}
	
	@Shadow
	public Item getItem() {
		return null;
	}
}
