package mrunknown404.primalrework.client;

public class ColorH {
	public static int rgba2Int(int r, int g, int b, int a) {
		return (a << 24) + (r << 16) + (g << 8) + (b);
	}
	
	public static int rgba2Int(int r, int g, int b) {
		return rgba2Int(r, g, b, 255);
	}
}
