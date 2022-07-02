package mrunknown404.primalrework.utils.enums;

import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

import mrunknown404.primalrework.blocks.BlockInfo;
import mrunknown404.primalrework.blocks.BlockInfo.Hardness;
import mrunknown404.primalrework.init.InitStages;
import mrunknown404.primalrework.stage.Stage;
import net.minecraftforge.common.util.Lazy;

public enum Metal {
	//@formatter:off
	UNKNOWN(BlockInfo.with(BlockInfo.METAL, Hardness.MEDIUM_0), InitStages.STAGE_3, ToolMaterial.STONE,  null),
	COPPER (BlockInfo.with(BlockInfo.METAL, Hardness.MEDIUM_0), InitStages.STAGE_3, ToolMaterial.STONE,  new Color(255, 145, 0)),
	TIN    (BlockInfo.with(BlockInfo.METAL, Hardness.SOFT_2),   InitStages.STAGE_3, ToolMaterial.STONE,  new Color(255, 237, 251)),
	BRONZE (BlockInfo.with(BlockInfo.METAL, Hardness.MEDIUM_1), InitStages.STAGE_4, ToolMaterial.COPPER, new Color(208, 101, 0));
	//@formatter:on
	
	//TODO add melting/boiling temp
	
	//These are only used for block/item creation
	public final BlockInfo blockInfo;
	public final Supplier<Stage> stage;
	public final ToolMaterial toolMat;
	public final Color ingotColor;
	
	private final Lazy<Boolean> isAlloy = Lazy.of(() -> ordinal() == 0 || getElements().size() > 1);
	private final Lazy<ElementHashMap> elements = Lazy.of(() -> {
		ElementHashMap m = new ElementHashMap();
		switch (this) {
			case BRONZE:
				return m.chain(Element.COPPER).chain(Element.TIN);
			case COPPER:
				return m.chain(Element.COPPER);
			case TIN:
				return m.chain(Element.TIN);
			case UNKNOWN:
				return m.chain(Element.UNKNOWN);
		}
		return m;
	});
	
	private Metal(BlockInfo blockInfo, Supplier<Stage> stage, ToolMaterial toolMat, Color ingotColor) {
		this.blockInfo = blockInfo;
		this.stage = stage;
		this.toolMat = toolMat;
		this.ingotColor = ingotColor;
	}
	
	public boolean isAlloy() {
		return isAlloy.get();
	}
	
	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
	
	public Map<Element, Integer> getElements() {
		return elements.get();
	}
	
	public class ElementHashMap extends LinkedHashMap<Element, Integer> {
		private static final long serialVersionUID = 3809877206660043700L;
		
		public ElementHashMap chain(Element key) {
			put(key, 1);
			return this;
		}
	}
}
