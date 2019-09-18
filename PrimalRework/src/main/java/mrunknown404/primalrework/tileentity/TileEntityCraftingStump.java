package mrunknown404.primalrework.tileentity;

import mrunknown404.primalrework.inventory.InventoryCraftingStump;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityCraftingStump extends TileEntityBase implements ITickable {

	public EnumFacing facing;
	protected float[] itemRotation = {0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F};
	protected int itemJump[] = {0, 0, 0, 0, 0, 0, 0, 0, 0};
	protected int itemJumpPrev[] = {0, 0, 0, 0, 0, 0, 0, 0, 0};
	protected boolean hit = false;
	protected int strikes = 0;
	protected InventoryCrafting craftMatrix;
	
	public TileEntityCraftingStump() {
		super(9);
	}
	
	@Override
	public void update() {
		if (gethit()) {
			if (getWorld().isRemote) {
				for(int count = 0; count < 9; count++) {
					itemJump[count] = 1 + getWorld().rand.nextInt(5);
					getItemRotation()[count] = (getWorld().rand.nextFloat() - getWorld().rand.nextFloat()) * 25F;
				}
			}
			
			if (!getWorld().isRemote) {
				getWorld().playSound(null, pos, SoundEvents.BLOCK_WOOD_HIT, SoundCategory.BLOCKS, 1F, 0.75F);
			}
			
			setHit(false);
		} else {
			if (getWorld().isRemote) {
				for (int i = 0; i < getSizeInventory(); i++) {
					if (itemJump[i] > 0) {
						itemJump[i]--;
					}
				}
			}
		}
		
		if (!getWorld().isRemote && strikes == 3) {
			craftMatrix = new InventoryCraftingStump(new Container() {
				@Override
				public boolean canInteractWith(EntityPlayer player) {
					return true;
				}
			}, this);
			
			IRecipe rec = CraftingManager.findMatchingRecipe(craftMatrix, getWorld());
			if (rec == null) {
				setStrikes(0);
				return;
			}
			
			ItemStack output = rec.getRecipeOutput();
			if (output.isEmpty()) {
				setStrikes(0);
				return;
			}
			
			NonNullList<ItemStack> stuffLeft = CraftingManager.getRemainingItems(craftMatrix, getWorld());
			for (int index = 0; index < 9; index++) {
				if (!stuffLeft.get(index).isEmpty()) {
					setInventorySlotContents(index, stuffLeft.get(index));
				} else {
					decrStackSize(index, 1);
				}
			}
			
			spawnItemStack(getWorld(), pos.getX() + 0.5D, pos.getY() + 1D, pos.getZ() + 0.5D, output);
			getWorld().playSound(null, pos, SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.BLOCKS, 1F, 1.75F);
			setStrikes(0);
		}
	}
	
	public static void spawnItemStack(World world, double x, double y, double z, ItemStack stack) {
		EntityItem entityitem = new EntityItem(world, x, y, z, stack);
		entityitem.motionX = 0D;
		entityitem.motionY = 0D;
		entityitem.motionZ = 0D;
		entityitem.setPickupDelay(20);
		world.spawnEntity(entityitem);
	}
	
	public boolean gethit() {
		return hit;
	}
	
	public void setHit(boolean isHit) {
		hit = isHit;
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}
	
	public void markForUpdate() {
		IBlockState state = this.getWorld().getBlockState(this.getPos());
		getWorld().notifyBlockUpdate(this.getPos(), state, state, 2);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("strikes", strikes);
		nbt.setBoolean("hit", hit);
		nbt.setString("facing", facing.toString());
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		strikes = nbt.getInteger("strikes");
		hit = nbt.getBoolean("hit");
		facing = EnumFacing.valueOf(nbt.getString("facing").toUpperCase());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbt = new NBTTagCompound();
		return writeToNBT(nbt);
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return new SPacketUpdateTileEntity(pos, 0, nbt);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		readFromNBT(packet.getNbtCompound());
	}
	
	@Override
	public String getName() {
		return "container.crafting_stump";
	}
	
	public void setStrikes(int strikes) {
		this.strikes = strikes;
		markForUpdate();
	}
	
	public int getStrikes() {
		return strikes;
	}
	
	public float[] getItemRotation() {
		return itemRotation;
	}
	
	public int[] getItemJump() {
		return itemJump;
	}
	
	public int[] getItemJumpPrev() {
		return itemJumpPrev;
	}
}
