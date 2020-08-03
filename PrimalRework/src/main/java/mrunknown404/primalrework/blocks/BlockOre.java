package mrunknown404.primalrework.blocks;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import mrunknown404.primalrework.blocks.util.BlockStaged;
import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.items.ItemOreNugget;
import mrunknown404.primalrework.util.enums.EnumAlloy;
import mrunknown404.primalrework.util.enums.EnumOreValue;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import mrunknown404.unknownlibs.utils.DoubleValue;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextComponentTranslation;

public class BlockOre extends BlockStaged {
	
	public static final DoubleValue<EnumToolType, EnumToolMaterial> COPPER_TYPES = new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.pickaxe, EnumToolMaterial.stone);
	
	protected final EnumAlloy alloy;
	protected final EnumOreValue value;
	
	public BlockOre(float hardness, float resistance, EnumStage stage, EnumAlloy alloy, DoubleValue<EnumToolType, EnumToolMaterial>... types) {
		super(alloy.toString() + "_block", Material.IRON, SoundType.METAL, hardness, resistance, stage, types);
		this.alloy = alloy;
		this.value = EnumOreValue.block;
	}
	
	public BlockOre(float hardness, float resistance, EnumStage stage, EnumAlloy alloy, EnumOreValue value, DoubleValue<EnumToolType, EnumToolMaterial>... types) {
		super(alloy.toString() + "_ore_" + value.toString(), Material.ROCK, SoundType.STONE, hardness, resistance, stage, types);
		this.alloy = alloy;
		this.value = value;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		if (value == EnumOreValue.block) {
			return super.getItemDropped(state, rand, fortune);
		}
		
		for (Item item1 : ModItems.ITEMS) {
			if (item1 instanceof ItemOreNugget) {
				ItemOreNugget item = (ItemOreNugget) item1;
				if (item.getAlloy() == alloy && item.getOreValue() == value) {
					return item1;
				}
			}
		}
		
		return super.getItemDropped(state, rand, fortune);
	}
	
	@Override
	public int getAmountOfTooltips() {
		return 1;
	}
	
	@Override
	public List<String> getTooltips(String name) {
		return Arrays.asList("" + value.units + " " + new TextComponentTranslation("item.ore_units.tooltip").getUnformattedText());
	}
}
