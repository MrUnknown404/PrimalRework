package mrunknown404.primalrework.tileentity;

import mrunknown404.unknownlibs.tileentity.TileEntityInventory;

public class TileEntityClayPot extends TileEntityInventory {
	
	public TileEntityClayPot() {
		super(9);
	}
	
	@Override
	public String getName() {
		return "container.clay_pot";
	}
}
