package mrunknown404.primalrework.utils;

import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

import mrunknown404.primalrework.blocks.BlockInfo;
import mrunknown404.primalrework.blocks.BlockInfo.Hardness;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.enums.Element;
import mrunknown404.primalrework.utils.enums.ToolMaterial;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class Metal extends ForgeRegistryEntry<Metal> {
	//These are only used for block/item creation
	public final BlockInfo blockInfo;
	public final Supplier<Stage> stage;
	public final ToolMaterial toolMat;
	public final Color color;
	public final boolean isAlloy;
	
	/** In Kelvin */
	public final int meltingPoint, boilingPoint;
	public final Map<Element, Integer> elements;
	
	private Metal(Hardness hardness, Supplier<Stage> stage, ToolMaterial toolMat, Color color, int meltingPoint, int boilingPoint, Map<Element, Integer> elements) {
		this.blockInfo = BlockInfo.with(BlockInfo.METAL, hardness);
		this.stage = stage;
		this.toolMat = toolMat;
		this.color = color;
		this.isAlloy = elements.size() > 1 || elements.size() == 0;
		this.meltingPoint = meltingPoint;
		this.boilingPoint = boilingPoint;
		this.elements = elements;
	}
	
	public static Metal elemental(Hardness hardness, Supplier<Stage> stage, ToolMaterial toolMat, Color ingotColor, Element element) {
		return new Metal(hardness, stage, toolMat, ingotColor, element.getMeltingPoint(), element.getBoilingPoint(), toMap(element));
	}
	
	public static Metal elementalAlloy(Hardness hardness, Supplier<Stage> stage, ToolMaterial toolMat, Color ingotColor, int meltingPoint, int boilingPoint, Element element0,
			Element element1, Element... elements) {
		return new Metal(hardness, stage, toolMat, ingotColor, meltingPoint, boilingPoint, toMap(element0, element1, elements));
	}
	
	public static Metal metal(Hardness hardness, Supplier<Stage> stage, ToolMaterial toolMat, Color ingotColor, int meltingPoint, int boilingPoint) {
		return new Metal(hardness, stage, toolMat, ingotColor, meltingPoint, boilingPoint, new LinkedHashMap<Element, Integer>());
	}
	
	public static Metal elemental(Hardness hardness, Supplier<Stage> stage, ToolMaterial toolMat, Color ingotColor, Pair<Element, Integer> element) {
		return new Metal(hardness, stage, toolMat, ingotColor, element.getL().getMeltingPoint(), element.getL().getBoilingPoint(), toMap(element));
	}
	
	@SuppressWarnings("unchecked")
	public static Metal elementalAlloy(Hardness hardness, Supplier<Stage> stage, ToolMaterial toolMat, Color ingotColor, int meltingPoint, int boilingPoint,
			Pair<Element, Integer> element0, Pair<Element, Integer> element1, Pair<Element, Integer>... elements) {
		return new Metal(hardness, stage, toolMat, ingotColor, meltingPoint, boilingPoint, toMap(element0, element1, elements));
	}
	
	private static Map<Element, Integer> toMap(Element element0, Element element1, Element[] elements) {
		Map<Element, Integer> map = new LinkedHashMap<Element, Integer>();
		map.put(element0, 1);
		map.put(element1, 1);
		for (Element e : elements) {
			map.put(e, 1);
		}
		return map;
	}
	
	private static Map<Element, Integer> toMap(Element element) {
		Map<Element, Integer> map = new LinkedHashMap<Element, Integer>();
		map.put(element, 1);
		return map;
	}
	
	private static Map<Element, Integer> toMap(Pair<Element, Integer> element0, Pair<Element, Integer> element1, Pair<Element, Integer>[] elements) {
		Map<Element, Integer> map = new LinkedHashMap<Element, Integer>();
		map.put(element0.getL(), element0.getR());
		map.put(element1.getL(), element1.getR());
		for (Pair<Element, Integer> e : elements) {
			map.put(e.getL(), e.getR());
		}
		return map;
	}
	
	private static Map<Element, Integer> toMap(Pair<Element, Integer> element) {
		Map<Element, Integer> map = new LinkedHashMap<Element, Integer>();
		map.put(element.getL(), element.getR());
		return map;
	}
	
	@Override
	public String toString() {
		return getRegistryName().getPath();
	}
}
