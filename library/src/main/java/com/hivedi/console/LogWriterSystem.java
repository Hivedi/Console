package com.hivedi.console;

import android.support.annotation.Nullable;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class LogWriterSystem implements LogWriterBase {
	
	private String extraSpaces = "                                                ";
	private boolean showTag = true, showType = true;
	
	public LogWriterSystem() {}
	public LogWriterSystem(boolean showTag, boolean showType) {
		this.showTag = showTag; 
		this.showType = showType; 
		
		extraSpaces = "                        ";
		
		if (this.showTag) {
			if (Console.getTag() != null && Console.getTag().length() > 0)
			for(int i=0; i<Console.getTag().length(); i++) 
				extraSpaces += " ";
			extraSpaces += "  ";
		}
		if (this.showType) {
			extraSpaces += "         ";
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
        final String TAG = Console.getTag();
        String typeString = null;
        if (showType) {
            switch(lvl) {
                default:
                case Console.TYPE_VERBOSE : typeString = "VERBOSE"; break;
                case Console.TYPE_ERROR   : typeString = "ERROR  "; break;
                case Console.TYPE_DEBUG   : typeString = "DEBUG  "; break;
                case Console.TYPE_WARNING : typeString = "WARNING"; break;
                case Console.TYPE_INFO    : typeString = "INFO   "; break;
            }
        }

        String tagStr = showTag ? "[" + TAG + "]"  :"";

        String logSaveTxt = "[" + now() + "]" + tagStr + "" + (typeString != null ? "[" + typeString + "]" : "") + " : " + (log != null ? log.replace("\n", "\n" + extraSpaces) : "");
        System.out.println(logSaveTxt);
        if (e != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            System.err.println(extraSpaces + "--- STACK TRACE ---\n" + extraSpaces + sw.toString().replace("\n", "\n" + extraSpaces) + "\n" + extraSpaces + "--- END STACK TRACE ---\n");
        }
    }
}
