package mrunknown404.primalrework.mixin;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mrunknown404.primalrework.blocks.utils.StagedBlock;
import mrunknown404.primalrework.stage.VanillaRegistry;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.HarvestInfo.DropInfo;
import mrunknown404.primalrework.utils.enums.EnumToolType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;

@Mixin(AbstractBlock.class)
public class MixinAbstractBlock {
	@SuppressWarnings({ "unused", "static-method" })
	@Inject(at = @At("HEAD"), method = "getDrops(Lnet/minecraft/block/BlockState;Lnet/minecraft/loot/LootContext$Builder;)Ljava/util/List;", cancellable = true)
	private void getDrops(BlockState state, LootContext.Builder builder, CallbackInfoReturnable<List<ItemStack>> callback) {
		List<ItemStack> list = new ArrayList<ItemStack>();
		
		Block block = state.getBlock();
		HarvestInfo info = block instanceof StagedBlock ? ((StagedBlock) block).getHarvest().get(EnumToolType.none) : VanillaRegistry.getHarvestInfo(block, EnumToolType.none);
		
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
