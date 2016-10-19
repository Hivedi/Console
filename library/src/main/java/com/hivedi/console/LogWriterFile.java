package com.hivedi.console;

import android.support.annotation.Nullable;

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
	
	private final String extraSpaces = "                                   ";
	private File lf;
	
	public LogWriterFile(File logFile) {
		lf = logFile;
	}
	
	private void saveLog(String s, String typeStr) {
		BufferedWriter out = null; 
		try { 
			out = new BufferedWriter(new FileWriter(lf, true)); 
			out.write("[" + now() + "]" + (typeStr != null ? "[" + typeStr + "]" : "") + " : "); 
			out.write(s.replace("\n", "\n" + extraSpaces)); 
			out.newLine(); 
			out.flush(); 
		} catch (IOException e) { 
			// ignore
		} finally {
			if (out != null) 
			try { 
				out.close(); 
			} catch (IOException ioe2) {
                // ignore
			}
		} 
	} 

	private static String now() {
		return new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.SSS",
			Locale.getDefault()
		).format(Calendar.getInstance().getTime()); 
	}

    @Override
    public void writeLogLine(@Nullable String tag, @Nullable String log, @Nullable Throwable e, @Console.LogLevel int lvl) {
        String logSaveTxt = log;
        if (e != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            logSaveTxt += extraSpaces + "\n--- STACK TRACE ---\n" + sw.toString().replace("\n", "\n" + extraSpaces);
        }
        String logType;
        switch(lvl) {
            default:
            case Console.TYPE_VERBOSE : logType = "VERB "; break;
            case Console.TYPE_ERROR   : logType = "ERROR"; break;
            case Console.TYPE_DEBUG   : logType = "DEBUG"; break;
            case Console.TYPE_WARNING : logType = "WARN "; break;
            case Console.TYPE_INFO    : logType = "INFO "; break;
        }
        saveLog(logSaveTxt, logType);
    }
}
