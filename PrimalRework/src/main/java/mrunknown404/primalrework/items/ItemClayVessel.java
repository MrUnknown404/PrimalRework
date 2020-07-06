package mrunknown404.primalrework.items;

import java.util.HashMap;
import java.util.Map;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.capabilities.ClayVesselInventoryProvider;
import mrunknown404.primalrework.items.util.ItemBase;
import mrunknown404.primalrework.util.enums.EnumAlloy;
import mrunknown404.primalrework.util.enums.EnumStage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.IItemHandler;

public class ItemClayVessel extends ItemBase {
	public ItemClayVessel() {
		super("clay_vessel", 1, EnumStage.stage2);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (!world.isRemote) {
			player.openGui(Main.main, Main.GUI_ID_CLAY_VESSEL, world, (int) player.posX, (int) player.posY, (int) player.posZ);
		}
		
		return super.onItemRightClick(world, player, hand);
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new ClayVesselInventoryProvider(9);
	}
	
	public static Map<EnumAlloy, Integer> convertInventoryToMap(IItemHandler handler) {
		Map<EnumAlloy, Integer> map = new HashMap<EnumAlloy, Integer>();
		for (int i = 0; i < handler.getSlots(); i++) {
			ItemStack item1 = handler.getStackInSlot(i);
			
			if (item1.getItem() instanceof ItemOreNugget) {
				ItemOreNugget item = (ItemOreNugget) item1.getItem();
				
				for (int j = 0; j < item1.getCount(); j++) {
					boolean flag = map.containsKey(item.getAlloy());
					map.put(item.getAlloy(), flag ? map.get(item.getAlloy()) + item.getOreValue().units : item.getOreValue().units);
				}
			}
		}
		
		return map;
	}
}
