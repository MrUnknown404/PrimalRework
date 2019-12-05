package mrunknown404.primalrework.items;

import mrunknown404.primalrework.items.util.ItemBase;
import mrunknown404.primalrework.util.enums.EnumStage;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemChangeWithWater extends ItemBase {
	
	private final Item changeInto;
	
	public ItemChangeWithWater(String name, Item changeInto, EnumStage stage) {
		super(name, stage);
		this.changeInto = changeInto;
	}
	
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack item = player.getHeldItem(hand);
		RayTraceResult ray = rayTrace(world, player, true);
		
		if (ray == null) {
			return new ActionResult<ItemStack>(EnumActionResult.PASS, item);
		} else if (ray.typeOfHit != RayTraceResult.Type.BLOCK) {
			return new ActionResult<ItemStack>(EnumActionResult.PASS, item);
		} else {
			if (world.getBlockState(ray.getBlockPos()).getMaterial() == Material.WATER) {
				player.addItemStackToInventory(new ItemStack(changeInto));
				item.shrink(1);
				
				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
			}
		}
		
		return new ActionResult<ItemStack>(EnumActionResult.FAIL, item);
	}
}
