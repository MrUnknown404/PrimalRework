package mrunknown404.primalrework.tileentity;

public class TileEntityLoom extends TileEntityBase {

	public TileEntityLoom() {
		super(9);
	}
	
	@Override
	public String getName() {
		return "container.loom";
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 1;
	}
}
