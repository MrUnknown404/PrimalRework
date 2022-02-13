package mrunknown404.primalrework.utils.helpers;

public class MathH {
	public static int floor(double value) {
		int i = (int) value;
		return value < i ? i - 1 : i;
	}
	
	public static int floor(float value) {
		return floor((double) value);
	}
	
	public static int ceil(double value) {
		int i = (int) value;
		return value > i ? i + 1 : i;
	}
	
	public static int ceil(float value) {
		return ceil((double) value);
	}
	
	public static int fClamp(double num, double min, double max) {
		return floor(clamp(num, min, max));
	}
	
	public static int fClamp(float num, float min, float max) {
		return floor(clamp(num, min, max));
	}
	
	public static double clamp(double num, double min, double max) {
		return num < min ? min : (num > max ? max : num);
	}
	
	public static float clamp(float num, float min, float max) {
		return (float) clamp((double) num, (double) min, (double) max);
	}
	
	public static int clamp(int num, int min, int max) {
		return fClamp(num, min, max);
	}
	
	public static double roundTo(double number, int decimal) {
		double tempDecimal = Math.pow(10, decimal);
		return Math.round(number * tempDecimal) / tempDecimal;
	}
	
	public static float roundTo(float number, int decimal) {
		float tempDecimal = (float) Math.pow(10, decimal);
		return Math.round(number * tempDecimal) / tempDecimal;
	}
	
	public static double normalize(double number, double min, double max) {
		if (number > max) {
			System.out.println("The specified number is more than the specified max!");
			return max;
		} else if (number < min) {
			System.out.println("The specified number cannot be less than the specified min!");
			return 0;
		}
		
		return (number - min) / (max - min);
	}
	
	public static float normalize(float number, float min, float max) {
		if (number > max) {
			System.out.println("The specified number is more than the specified max!");
			return max;
		} else if (number < min) {
			System.out.println("The specified number cannot be less than the specified min!");
			return 0;
		}
		
		return (number - min) / (max - min);
	}
	
	public static double percentage(double number, double max) {
		return (number / max) * 100f;
	}
	
	public static float percentage(float number, float max) {
		return (number / max) * 100f;
	}
	
	public static boolean within(int value, int min, int max) {
		return value >= min && value <= max;
	}
	
	public static boolean within(float value, float min, float max) {
		return value >= min && value <= max;
	}
	
	public static boolean within(double value, double min, double max) {
		return value >= min && value <= max;
	}
}
