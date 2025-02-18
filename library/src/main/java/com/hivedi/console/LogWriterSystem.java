package com.hivedi.console;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class LogWriterSystem implements LogWriterBase {
	
	private String extraSpaces = "                                            ";
	private boolean showTag = true, showType = true;
	
	public LogWriterSystem() {}
	public LogWriterSystem(boolean showTag, boolean showType) {
		this.showTag = showTag;
		this.showType = showType;

		extraSpaces = "                        ";

		if (this.showTag) {
			if (Console.getTag() != null && !Console.getTag().isEmpty()) {
				for (int i = 0; i < Console.getTag().length(); i++) {
					extraSpaces += " ";
				}
			}
			extraSpaces += "  ";
		}
		if (this.showType) {
			extraSpaces += "         ";
		}
	}

	@Override
	public void saveHandler(String arg0, int arg1, Exception e) {
		final String TAG = Console.getTag();
		String typeString = null;
		if (showType) {
			switch(arg1) {
				case 1: typeString = "ERROR  "; break;
				case 2: typeString = "DEBUG  "; break;
				case 3: typeString = "WARNING"; break;
				case 4: typeString = "INFO   "; break;
				case 0:
				default:
					typeString = "VERBOSE"; break;
			}
		}
		
		String tagStr = showTag ? "[" + TAG + "]"  :"";
		
		String logSaveTxt = "[" + now() + "]" + tagStr + (typeString != null ? "[" + typeString + "]" : "") + " : " + arg0.replace("\n", "\n" + extraSpaces);
		System.out.println(logSaveTxt);
		if (e != null) {
			StringWriter sw = new StringWriter(); 
			PrintWriter pw = new PrintWriter(sw); 
			e.printStackTrace(pw);
			System.err.println(extraSpaces + "--- STACK TRACE ---\n" + extraSpaces + sw.toString().replace("\n", "\n" + extraSpaces) + "\n" + extraSpaces + "--- END STACK TRACE ---\n");
		}
	}

	public static String now() { 
		return new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss", 
			Locale.getDefault()
		).format(Calendar.getInstance().getTime()); 
	}

}
