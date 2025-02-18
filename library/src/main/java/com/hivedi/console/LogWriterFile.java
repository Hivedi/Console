package com.hivedi.console;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class LogWriterFile implements LogWriterBase {
	
	private final String extraSpaces = "                               ";
	private final File lf;
	
	public LogWriterFile(File logFile) {
		lf = logFile;
	}
	
	private void saveLog(String s, String typeStr) {
		try (BufferedWriter out = new BufferedWriter(new FileWriter(lf, true))) {
			out.write("[" + now() + "]" + (typeStr != null ? "[" + typeStr + "]" : "") + " : ");
			out.write(s.replace("\n", "\n" + extraSpaces));
			out.newLine();
			out.flush();
		} catch (IOException e) {
			// ignore
		}
	} 
	
	@Override
	public void saveHandler(String data, int type, Exception e) {
		String logSaveTxt = data;
		if (e != null) {
			StringWriter sw = new StringWriter(); 
			PrintWriter pw = new PrintWriter(sw); 
			e.printStackTrace(pw);
			logSaveTxt += extraSpaces + "\n--- STACK TRACE ---\n" + sw.toString().replace("\n", "\n" + extraSpaces);
		}
		String logType = null;
		switch(type) {
			case 1: logType = "ERROR"; break;
			case 2: logType = "DEBUG"; break;
			case 3: logType = "WARN "; break;
			case 4: logType = "INFO "; break;
			case 0:
			default: logType = "VERB "; break;
		}
		saveLog(logSaveTxt, logType);
	}
	
	public static String now() { 
		return new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss", 
			Locale.getDefault()
		).format(Calendar.getInstance().getTime()); 
	}
}
