package mrunknown404.primalrework.blocks;

import mrunknown404.primalrework.blocks.utils.SBContainer;
import mrunknown404.primalrework.init.InitItemGroups;
import mrunknown404.primalrework.tileentities.TEIPrimalCraftingTable;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.enums.EnumStage;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
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

public class SBPrimalCraftingTable extends SBContainer {
	public SBPrimalCraftingTable() {
		super("primal_crafting_table", EnumStage.do_later, 64, InitItemGroups.MACHINES, Material.WOOD, SoundType.WOOD, true, 0, 3, 3, false, BlockStateType.normal,
				BlockModelType.normal, HarvestInfo.SAW_MIN);
	}
	
	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult ray) {
		if (world.isClientSide) {
			return ActionResultType.SUCCESS;
		}
		
		TileEntity tileentity = world.getBlockEntity(pos);
		if (tileentity instanceof TEIPrimalCraftingTable) {
			NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileentity);
		}
		return ActionResultType.CONSUME;
	}
	
	@Override
	public TileEntity newBlockEntity(IBlockReader reader) {
		return new TEIPrimalCraftingTable();
	}
	
	@Override
	public void setPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack) {
		if (stack.hasCustomHoverName()) {
			TileEntity tileentity = world.getBlockEntity(pos);
			if (tileentity instanceof TEIPrimalCraftingTable) {
				((TEIPrimalCraftingTable) tileentity).setCustomName(stack.getHoverName());
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState state, World world, BlockPos pos, BlockState state2, boolean flag) {
		if (!state.is(state2.getBlock())) {
			TileEntity tileentity = world.getBlockEntity(pos);
			if (tileentity instanceof TEIPrimalCraftingTable) {
				InventoryHelper.dropContents(world, pos, (IInventory) tileentity);
			}
			
			super.onRemove(state, world, pos, state2, flag);
		}
	}
	
	@Override
	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.MODEL;
	}
}
