package mrunknown404.primalrework.utils.helpers;

import java.awt.Color;

public class ColorH {
	public static int rgba2Int(int r, int g, int b, int a) {
		return (a << 24) + (r << 16) + (g << 8) + (b);
	}
	
	public static int rgba2Int(int r, int g, int b) {
		return rgba2Int(r, g, b, 255);
	}
	
	public static int rgba2Int(Color color) {
		return rgba2Int(color.getRed(), color.getGreen(), color.getBlue(), 255);
	}
}
