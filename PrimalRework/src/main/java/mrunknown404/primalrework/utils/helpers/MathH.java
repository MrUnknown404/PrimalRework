package mrunknown404.primalrework.utils.helpers;

//TODO write the javadocs for all the math
/** A bunch of utility methods involving Math
 * @author -Unknown-
 */
public class MathH {
	public static int floor(double value) {
		int i = (int) value;
		return value < i ? i - 1 : i;
	}
	
	public static int floor(float value) {
		int i = (int) value;
		return value < i ? i - 1 : i;
	}
	
	public static int ceil(double value) {
		int i = (int) value;
		return value > i ? i + 1 : i;
	}
	
	public static int ceil(float value) {
		int i = (int) value;
		return value > i ? i + 1 : i;
	}
	
	public static double clamp(double num, double min, double max) {
		return num < min ? min : (num > max ? max : num);
	}
	
	public static float clamp(float num, float min, float max) {
		return num < min ? min : (num > max ? max : num);
	}
	
	public static int clamp(int num, int min, int max) {
		return num < min ? min : (num > max ? max : num);
	}
	
	public static long clamp(long num, long min, long max) {
		return num < min ? min : (num > max ? max : num);
	}
	
	public static double roundTo(double number, int decimal) {
		double tempDecimal = Math.pow(10, decimal);
		return Math.round(number * tempDecimal) / tempDecimal;
	}
	
	public static float roundTo(float number, int decimal) {
		float tempDecimal = (float) Math.pow(10, decimal);
		return Math.round(number * tempDecimal) / tempDecimal;
	}
	
	public static double percentage(double number, double max) {
		return (number / max) * 100;
	}
	
	public static float percentage(float number, float max) {
		return (number / max) * 100f;
	}
	
	public static float percentage(int number, int max) {
		return ((float) number / (float) max) * 100f;
	}
	
	public static double percentage(long number, long max) {
		return ((double) number / (double) max) * 100d;
	}
}
