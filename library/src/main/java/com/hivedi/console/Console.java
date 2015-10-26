package com.hivedi.console;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;


public class Console {
	
	private static final int VERSION = 2;

	private static String TAG = "console";
	private static boolean consoleEnabled = false; //BuildConfig.DEBUG;
	
	private static ArrayList<LogWriterBase> logWrites = new ArrayList<LogWriterBase>();
	
	public static int getVersion() {
		return VERSION;
	}
	
	public static boolean isEnabled() {
		return consoleEnabled;
	}
	
	public static void setEnabled(boolean e) {
		consoleEnabled = e;
	}
	
	public static void setTag(String t) {
		TAG = t;
	}
	
	public static String getTag() {
		return TAG;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getLogWriter(Class<?> c) {
		if (logWrites.size() > 0) {
			for(LogWriterBase lw : logWrites) {
				if (lw.getClass().equals(c)) {
                    return (T) lw;
                }
			}
			return null;
		} else {
            return null;
        }
	}
	
	/**
	 * @param logString string to show on log
	 * @param e exception to print on log
	 * @param type - 0 normal, 1 - error, 2 - debug, 3 - warning, 4 - info
	 */
	private static void logBase(String logString, Exception e, int type) {
		if (isEnabled() && logWrites.size() > 0) {
			for(LogWriterBase lb : logWrites) {
				lb.saveHandler(logString, type, e);
			}
		}
	}
	
	public static void loge(String log, Exception e) {
		logBase(log, e, 1);
	}
	public static void loge(String log) {
		logBase(log, null, 1);
	}
	
	public static void log(String log, Exception e) {
		logBase(log, e, 0);
	}
	public static void log(String log) {
		logBase(log, null, 0);
	}
	
	public static void logi(String log, Exception e) {
		logBase(log, e, 4);
	}
	public static void logi(String log) {
		logBase(log, null, 4);
	}
	
	public static void logd(String log, Exception e) {
		logBase(log, e, 2);
	}
	public static void logd(String log) {
		logBase(log, null, 2);
	}
	
	public static void logw(String log, Exception e) {
		logBase(log, e, 3);
	}
	public static void logw(String log) {
		logBase(log, null, 3);
	}

	public static void addLogWriter(LogWriterBase lw) {
		if (!Console.logWrites.contains(lw)) {
            Console.logWrites.add(lw);
        }
	}

    public static void addLogWriterLogCat() {
        addLogWriter(new LogWriterLogCat());
    }

    public static void addLogWriterDb(Context ctx) {
        addLogWriter(new LogWriterDb(ctx));
    }

    public static void addLogWriterFile(File f) {
        addLogWriter(new LogWriterFile(f));
    }

    public static void addLogWriterSystem() {
        addLogWriter(new LogWriterSystem());
    }
}
