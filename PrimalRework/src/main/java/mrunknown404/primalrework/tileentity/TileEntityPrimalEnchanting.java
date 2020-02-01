package mrunknown404.primalrework.tileentity;

import java.util.Random;

import mrunknown404.primalrework.inventory.ContainerPrimalEnchanting;
import mrunknown404.unknownlibs.tileentity.TileEntityInventory;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;

public class TileEntityPrimalEnchanting extends TileEntityInventory implements ITickable {
	
	private ContainerPrimalEnchanting container;
	
	public TileEntityPrimalEnchanting() {
		super(2);
	}
	
	@Override
	public void update() {
		if (world.isRemote && !getStackInSlot(0).isEmpty() && !getStackInSlot(1).isEmpty()) {
			Random r = new Random();
			float yspd = r.nextInt(10) * 0.01f;
			float xspd = (r.nextInt(10) - 5) * 0.01f;
			float zspd = (r.nextInt(10) - 5) * 0.01f;
			
			if (r.nextInt(8) == 0) {
				world.spawnParticle(EnumParticleTypes.ITEM_CRACK, pos.getX() + 0.5, pos.getY() + 0.7, pos.getZ() + 0.5, xspd, yspd, zspd,
						Item.getIdFromItem(getStackInSlot(1).getItem()));
			}
		}
	}
	
	@Override
	public String getName() {
		return "container.primal_enchantment_table";
	}
	
	public TileEntityPrimalEnchanting setContainer(ContainerPrimalEnchanting container) {
		this.container = container;
		return this;
	}
	
	public ContainerPrimalEnchanting getContainer() {
		return container;
	}
}
