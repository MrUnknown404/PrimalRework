package mrunknown404.primalrework.handlers;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.client.gui.GuiFirePit;
import mrunknown404.primalrework.inventory.ContainerFirePit;
import mrunknown404.primalrework.tileentity.TileEntityFirePit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == Main.GUI_ID_FIRE_PIT) {
			return new ContainerFirePit(player.inventory, (TileEntityFirePit) world.getTileEntity(new BlockPos(x, y, z)));
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == Main.GUI_ID_FIRE_PIT) {
			return new GuiFirePit(player.inventory, (TileEntityFirePit) world.getTileEntity(new BlockPos(x, y, z)));
		}
		
		return null;
	}

}
