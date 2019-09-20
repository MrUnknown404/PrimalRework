package mrunknown404.primalrework.tileentity;

import mrunknown404.primalrework.inventory.ContainerPrimalEnchanting;

public class TileEntityPrimalEnchanting extends TileEntityBase {

	private ContainerPrimalEnchanting container;
	
	public TileEntityPrimalEnchanting() {
		super(2);
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
