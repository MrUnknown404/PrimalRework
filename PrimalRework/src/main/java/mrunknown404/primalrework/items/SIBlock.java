package mrunknown404.primalrework.items;

import java.util.List;
import java.util.Map;

import mrunknown404.primalrework.blocks.StagedBlock;
import mrunknown404.primalrework.utils.enums.ToolMaterial;
import mrunknown404.primalrework.utils.enums.ToolType;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class SIBlock extends StagedItem {
	private final StagedBlock block;
	
	public SIBlock(StagedBlock block) {
		super(block.stage, block.stackSize, ToolType.NONE, ToolMaterial.HAND, block.tab, Rarity.COMMON, null, false, false, ItemType.block);
		this.block = block;
		
		if (!block.getElements().isEmpty()) {
			this.elements.putAll(block.getElements());
		}
		
		if (block.usesVanillaNamespaceItem()) {
			useVanillaNamespace();
		}
	}
	
	@Override
	public List<ITextComponent> getTooltips() {
		return block.getTooltips();
	}
	
	@Override
	public ItemType getItemType() {
		return block.getItemType();
	}
	
	@Override
	public ActionResultType useOn(ItemUseContext context) {
		ActionResultType actionresulttype = place(new BlockItemUseContext(context));
		return !actionresulttype.consumesAction() && isEdible() ? use(context.getLevel(), context.getPlayer(), context.getHand()).getResult() : actionresulttype;
	}
	
	private ActionResultType place(BlockItemUseContext context) {
		if (!context.canPlace()) {
			return ActionResultType.FAIL;
		}
		
		BlockState blockstate = getPlacementState(context);
		if (blockstate == null || !context.getLevel().setBlock(context.getClickedPos(), blockstate, 11)) {
			return ActionResultType.FAIL;
		}
		
		BlockPos blockpos = context.getClickedPos();
		World world = context.getLevel();
		PlayerEntity playerentity = context.getPlayer();
		ItemStack itemstack = context.getItemInHand();
		BlockState blockstate1 = world.getBlockState(blockpos);
		Block block = blockstate1.getBlock();
		if (block == blockstate.getBlock()) {
			blockstate1 = updateBlockStateFromTag(blockpos, world, itemstack, blockstate1);
			updateCustomBlockEntityTag(world, playerentity, blockpos, itemstack);
			block.setPlacedBy(world, blockpos, blockstate1, playerentity, itemstack);
			
			if (playerentity instanceof ServerPlayerEntity) {
				CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) playerentity, blockpos, itemstack);
			}
		}
		
		SoundType soundtype = blockstate1.getSoundType(world, blockpos, context.getPlayer());
		world.playSound(playerentity, blockpos, getPlaceSound(blockstate1, world, blockpos, context.getPlayer()), SoundCategory.BLOCKS, (soundtype.getVolume() + 1f) / 2f,
				soundtype.getPitch() * 0.8f);
		if (playerentity == null || !playerentity.abilities.instabuild) {
			itemstack.shrink(1);
		}
		
		return ActionResultType.sidedSuccess(world.isClientSide);
	}
	
	private static SoundEvent getPlaceSound(BlockState state, World world, BlockPos pos, PlayerEntity entity) {
		return state.getSoundType(world, pos, entity).getPlaceSound();
	}
	
	protected BlockState getPlacementState(BlockItemUseContext context) {
		BlockState blockstate = getBlock().getStateForPlacement(context);
		return blockstate != null && canPlace(context, blockstate) ? blockstate : null;
	}
	
	private static BlockState updateBlockStateFromTag(BlockPos pos, World world, ItemStack item, BlockState state) {
		BlockState blockstate = state;
		CompoundNBT compoundnbt = item.getTag();
		if (compoundnbt != null) {
			CompoundNBT compoundnbt1 = compoundnbt.getCompound("BlockStateTag");
			StateContainer<Block, BlockState> statecontainer = state.getBlock().getStateDefinition();
			
			for (String s : compoundnbt1.getAllKeys()) {
				Property<?> property = statecontainer.getProperty(s);
				if (property != null) {
					blockstate = updateState(blockstate, property, compoundnbt1.get(s).getAsString());
				}
			}
		}
		
		if (blockstate != state) {
			world.setBlock(pos, blockstate, 2);
		}
		
		return blockstate;
	}
	
	private static <T extends Comparable<T>> BlockState updateState(BlockState state, Property<T> prop, String str) {
		return prop.getValue(str).map((val) -> state.setValue(prop, val)).orElse(state);
	}
	
	private static boolean canPlace(BlockItemUseContext ctx, BlockState state) {
		PlayerEntity player = ctx.getPlayer();
		return state.canSurvive(ctx.getLevel(), ctx.getClickedPos()) &&
				ctx.getLevel().isUnobstructed(state, ctx.getClickedPos(), player == null ? ISelectionContext.empty() : ISelectionContext.of(player));
	}
	
	private static boolean updateCustomBlockEntityTag(World world, PlayerEntity player, BlockPos pos, ItemStack item) {
		MinecraftServer server = world.getServer();
		if (server == null) {
			return false;
		}
		
		CompoundNBT nbt = item.getTagElement("BlockEntityTag");
		if (nbt != null) {
			TileEntity tileentity = world.getBlockEntity(pos);
			if (tileentity != null) {
				if (!world.isClientSide && tileentity.onlyOpCanSetNbt() && (player == null || !player.canUseGameMasterBlocks())) {
					return false;
				}
				
				CompoundNBT compoundnbt1 = tileentity.save(new CompoundNBT());
				CompoundNBT compoundnbt2 = compoundnbt1.copy();
				compoundnbt1.merge(nbt);
				compoundnbt1.putInt("x", pos.getX());
				compoundnbt1.putInt("y", pos.getY());
				compoundnbt1.putInt("z", pos.getZ());
				
				if (!compoundnbt1.equals(compoundnbt2)) {
					tileentity.load(world.getBlockState(pos), compoundnbt1);
					tileentity.setChanged();
					return true;
				}
			}
		}
		
		return false;
	}
	
	@Override
	public String getDescriptionId() {
		return getBlock().getDescriptionId();
	}
	
	@Override
	public void fillItemCategory(ItemGroup tab, NonNullList<ItemStack> stack) {
		if (allowdedIn(tab)) {
			getBlock().fillItemCategory(tab, stack);
		}
	}
	
	public StagedBlock getBlock() {
		return block == null ? null : (StagedBlock) block.delegate.get();
	}
	
	public void registerBlocks(Map<Block, Item> map, Item item) {
		map.put(getBlock(), item);
	}
	
	public void removeFromBlockToItemMap(Map<Block, Item> blockToItemMap) {
		blockToItemMap.remove(getBlock());
	}
}
