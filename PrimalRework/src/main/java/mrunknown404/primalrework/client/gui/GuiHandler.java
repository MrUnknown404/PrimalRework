package mrunknown404.primalrework.client.gui;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.inventory.ContainerEnchantmentReplace;
import mrunknown404.primalrework.inventory.ContainerFirePit;
import mrunknown404.primalrework.inventory.ContainerLoom;
import mrunknown404.primalrework.inventory.ContainerPrimalEnchanting;
import mrunknown404.primalrework.tileentity.TileEntityFirePit;
import mrunknown404.primalrework.tileentity.TileEntityLoom;
import mrunknown404.primalrework.tileentity.TileEntityPrimalEnchanting;
import net.minecraft.client.gui.GuiEnchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == Main.GUI_ID_FIRE_PIT) {
			return new ContainerFirePit(player.inventory, (TileEntityFirePit) world.getTileEntity(new BlockPos(x, y, z)));
		} else if (ID == Main.GUI_ID_ENCHANTING) {
			return new ContainerEnchantmentReplace(player.inventory, world, new BlockPos(x, y, z));
		} else if (ID == Main.GUI_ID_PRIMAL_ENCHANTING) {
			return new ContainerPrimalEnchanting(player.inventory, (TileEntityPrimalEnchanting) world.getTileEntity(new BlockPos(x, y, z)));
		} else if (ID == Main.GUI_ID_LOOM) {
			return new ContainerLoom(player.inventory, (TileEntityLoom) world.getTileEntity(new BlockPos(x, y, z)));
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == Main.GUI_ID_FIRE_PIT) {
			return new GuiFirePit(player.inventory, (TileEntityFirePit) world.getTileEntity(new BlockPos(x, y, z)));
		} else if (ID == Main.GUI_ID_ENCHANTING) {
			return new GuiEnchantment(player.inventory, world, (IWorldNameable) world.getTileEntity(new BlockPos(x, y, z)));
		} else if (ID == Main.GUI_ID_PRIMAL_ENCHANTING) {
			return new GuiPrimalEnchantment(player.inventory, (TileEntityPrimalEnchanting) world.getTileEntity(new BlockPos(x, y, z)));
		} else if (ID == Main.GUI_ID_LOOM) {
			return new GuiLoom(player.inventory, (TileEntityLoom) world.getTileEntity(new BlockPos(x, y, z)));
		}
		
		return null;
	}

}
