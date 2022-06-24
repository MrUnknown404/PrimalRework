package mrunknown404.primalrework.blocks;

import java.util.Random;

import mrunknown404.primalrework.blocks.utils.SBContainer;
import mrunknown404.primalrework.items.utils.StagedItem.ItemType;
import mrunknown404.primalrework.registries.PRStages;
import mrunknown404.primalrework.tileentities.TEICampFire;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.enums.BlockInfo;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class SBCampFire extends SBContainer {
	private static final VoxelShape SHAPE = box(2, 0, 2, 14, 14, 14);
	private static final VoxelShape COL_SHAPE = box(2, 0, 2, 14, 2, 14);
	
	public SBCampFire() {
		super("campfire", PRStages.STAGE_1, 64, Material.DECORATION, SoundType.WOOD, true, false, 0, BlockInfo.SOFT_WOOD, false, BlockStateType.lit, BlockModelType.none,
				HarvestInfo.HAND, HarvestInfo.AXE_MIN);
		registerDefaultState(defaultBlockState().setValue(BlockStateProperties.LIT, false));
	}
	
	@Override
	public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {
		if (state.getValue(BlockStateProperties.LIT) && !entity.fireImmune() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entity)) {
			entity.setSecondsOnFire(5);
		}
	}
	
	@Override
	public void animateTick(BlockState state, World world, BlockPos pos, Random r) {
		if (state.getValue(BlockStateProperties.LIT)) {
			for (int i = 0; i < 5; i++) {
				double xAdd = r.nextInt(21) / 100f, yAdd = r.nextInt(11) / 100f, zAdd = r.nextInt(21) / 100f;
				double xsAdd = r.nextInt(11) / 5000f, ysAdd = r.nextInt(11) / 5000f, zsAdd = r.nextInt(11) / 5000f;
				
				if (r.nextBoolean()) {
					xAdd = -xAdd;
				}
				if (r.nextBoolean()) {
					zAdd = -zAdd;
				}
				
				world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5 + xAdd, pos.getY() + 0.2 + yAdd, pos.getZ() + 0.5 + zAdd, xsAdd, 0.02 + ysAdd, zsAdd);
				if (i == 1) {
					world.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5 + xAdd, pos.getY() + 0.2 + yAdd, pos.getZ() + 0.5 + zAdd, xsAdd, 0.02 + ysAdd, zsAdd);
				}
			}
		}
	}
	
	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult ray) {
		if (world.isClientSide) {
			return ActionResultType.SUCCESS;
		}
		
		TileEntity tileentity = world.getBlockEntity(pos);
		if (tileentity instanceof TEICampFire) {
			NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileentity);
		}
		return ActionResultType.CONSUME;
	}
	
	@Override
	public TileEntity makeTileEntity(BlockState state, IBlockReader world) {
		return new TEICampFire();
	}
	
	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		return state.getValue(BlockStateProperties.LIT) ? 15 : 0;
	}
	
	@Override
	public void setPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack) {
		if (stack.hasCustomHoverName()) {
			TileEntity tileentity = world.getBlockEntity(pos);
			if (tileentity instanceof TEICampFire) {
				((TEICampFire) tileentity).setCustomName(stack.getHoverName());
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState state, World world, BlockPos pos, BlockState state2, boolean flag) {
		if (!state.is(state2.getBlock())) {
			TileEntity tileentity = world.getBlockEntity(pos);
			if (tileentity instanceof TEICampFire) {
				InventoryHelper.dropContents(world, pos, (IInventory) tileentity);
			}
			
			super.onRemove(state, world, pos, state2, flag);
		}
	}
	
	@Override
	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.MODEL;
	}
	
	@Override
	public ItemType getItemType() {
		return ItemType.generated;
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return defaultBlockState().setValue(BlockStateProperties.LIT, false);
	}
	
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.LIT);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState p_220071_1_, IBlockReader p_220071_2_, BlockPos p_220071_3_, ISelectionContext p_220071_4_) {
		return COL_SHAPE;
	}
}
