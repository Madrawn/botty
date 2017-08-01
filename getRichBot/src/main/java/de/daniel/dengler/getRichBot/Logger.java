package de.daniel.dengler.getRichBot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observer;

import org.apache.commons.cli.CommandLine;

public class Logger {

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

	public Logger() {
		super();
	}

}