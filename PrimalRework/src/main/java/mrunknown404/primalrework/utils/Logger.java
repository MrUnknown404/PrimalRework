package mrunknown404.primalrework.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.logging.log4j.LogManager;

import mrunknown404.primalrework.utils.helpers.MathH;

public final class Logger {
	private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger("PrimalRework");
	
	public static void multiLine(String line, String... lines) {
		List<String> list = new ArrayList<String>();
		list.add(line);
		for (String s : lines) {
			list.add(s);
		}
		multiLine(list);
	}
	
	public static void multiLine(List<String> multiLine) {
		if (multiLine == null || multiLine.isEmpty()) {
			warn("Tried to display a MultiLine text message but was provided an empty list");
			return;
		}
		
		int longestLine = multiLine.stream().max(Comparator.comparingInt(String::length)).get().length() + 4;
		StringBuilder topBottom = new StringBuilder();
		for (int i = 0; i < MathH.ceil(longestLine / 2f); i++) {
			topBottom.append("=");
			topBottom.append("-");
		}
		topBottom.append("=");
		
		StringBuilder spacer = new StringBuilder();
		info(topBottom.toString());
		multiLine.stream().forEach(s -> {
			spacer.setLength(0);
			for (int i = 0; i < longestLine - s.length() - 3; i++) {
				spacer.append(" ");
			}
			info("|  " + s + spacer + (longestLine % 2 == 0 ? "|" : " |"));
		});
		info(topBottom.toString());
	}
	
	public static void fatal(String msg) {
		LOGGER.fatal(msg);
	}
	
	public static void fatal(String msg, Throwable throwable) {
		LOGGER.fatal(msg, throwable);
	}
	
	public static void error(String msg) {
		LOGGER.error(msg);
	}
	
	public static void error(String msg, Throwable throwable) {
		LOGGER.error(msg, throwable);
	}
	
	public static void warn(String msg) {
		LOGGER.warn(msg);
	}
	
	public static void info(String msg) {
		LOGGER.info(msg);
	}
	
	public static void debug(String msg) {
		LOGGER.debug(msg);
	}
}
