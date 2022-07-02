package mrunknown404.primalrework.mixin;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mrunknown404.primalrework.blocks.HarvestInfo;
import mrunknown404.primalrework.blocks.StagedBlock;
import mrunknown404.primalrework.blocks.HarvestInfo.DropInfo;
import mrunknown404.primalrework.utils.enums.ToolType;
import mrunknown404.primalrework.utils.helpers.BlockH;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;

@Mixin(AbstractBlock.class)
public class MixinAbstractBlock {
	@SuppressWarnings({ "unused", "static-method" })
	@Inject(at = @At("HEAD"), method = "getDrops(Lnet/minecraft/block/BlockState;Lnet/minecraft/loot/LootContext$Builder;)Ljava/util/List;", cancellable = true)
	private void getDrops(BlockState state, LootContext.Builder builder, CallbackInfoReturnable<List<ItemStack>> callback) {
		List<ItemStack> list = new ArrayList<ItemStack>();
		if (!(state.getBlock() instanceof StagedBlock)) {
			callback.setReturnValue(list);
			return;
		}
		
		StagedBlock block = (StagedBlock) state.getBlock();
		HarvestInfo info = BlockH.getBlockHarvestInfo(block, ToolType.NONE);
		
		if (info == null) {
			callback.setReturnValue(list);
			return;
		}
		
		if (info.hasDrops()) {
			for (DropInfo drop : info.drops) {
				ItemStack stack = drop.getItem();
				if (!stack.isEmpty()) {
					list.add(stack);
				}
			}
		} else {
			list.add(new ItemStack(block));
		}
		
		callback.setReturnValue(list);
	}
}
