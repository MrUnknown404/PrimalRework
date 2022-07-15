package mrunknown404.primalrework.blocks;

import mrunknown404.primalrework.init.InitStages;
import mrunknown404.primalrework.tileentities.TEICraftingTable;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class SBCraftingTable extends SBContainer {
	public SBCraftingTable() {
		super(InitStages.STAGE_STONE, BlockInfo.of(BlockInfo.PRIMAL_CRAFTING_TABLE), BlockStateType.normal, BlockModelType.normal, HarvestInfo.AXE_MIN);
	}
	
	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult ray) {
		if (world.isClientSide) {
			return ActionResultType.SUCCESS;
		}
		
		TileEntity tileentity = world.getBlockEntity(pos);
		if (tileentity instanceof TEICraftingTable) {
			NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileentity);
		}
		return ActionResultType.CONSUME;
	}
	
	@Override
	public TileEntity makeTileEntity(BlockState state, IBlockReader world) {
		return new TEICraftingTable();
	}
	
	@Override
	public void setPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack) {
		if (stack.hasCustomHoverName()) {
			TileEntity tileentity = world.getBlockEntity(pos);
			if (tileentity instanceof TEICraftingTable) {
				((TEICraftingTable) tileentity).setCustomName(stack.getHoverName());
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState state0, World world, BlockPos pos, BlockState state1, boolean flag) {
		if (!state0.is(state1.getBlock())) {
			TileEntity tileentity = world.getBlockEntity(pos);
			if (tileentity instanceof TEICraftingTable) {
				for (int i = 0; i < 9; i++) {
					InventoryHelper.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), ((IInventory) tileentity).getItem(i));
				}
			}
			
			super.onRemove(state0, world, pos, state1, flag);
		}
	}
	
	@Override
	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.MODEL;
	}
}
