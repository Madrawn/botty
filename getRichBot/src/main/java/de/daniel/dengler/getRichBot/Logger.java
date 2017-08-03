package de.daniel.dengler.getRichBot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observer;

import org.apache.commons.cli.CommandLine;

public class Logger {

	public static final String ANSI_RESET = (char)27 + "[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = (char)27 + "[31m";
	public static final String ANSI_GREEN = (char)27 + "[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	
	
	static boolean verbose = false, quiet = false, debug = false;

	protected static void log(String string) {
		String timeStamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
		System.out.println(String.format("[%s]: %s", timeStamp, string));
	}

	public static void warningLog(String string) {
		String warning = "[WARNING] ";
		log(warning + string);

	}

	public static void verboseLog(String string) {
		if (verbose)
			log(string);
	}

	public static void debugLog(String string) {
		if (debug)
			log("[DEBUG]: " + string);
	}

	public static void normalLog(String string) {
		if (!quiet)
			log(string);
	}
	
	public static String colorBool(boolean toColor){
		if (toColor) {
			return ANSI_GREEN+toColor+ANSI_RESET;
		}else{
			return ANSI_RED+toColor+ANSI_RESET;
		}
	}

	public Logger() {
		super();
	}

}