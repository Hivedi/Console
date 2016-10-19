package com.hivedi.console;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;


public class Console {

    public static class Log {

        private String tag;

        public Log(String... tags) {
            tag = TAG;
            if (tags != null && tags.length > 0) {
                if (tag == null) {
                    tag = "";
                }
                for(String s : tags) {
                    tag += "/" + s;
                }
            }
        }

        public void v(String log, Throwable e) {
            logBase(tag, log, e, TYPE_VERBOSE);
        }
        public void v(String log) {
            v(log, null);
        }

        public void i(String log, Throwable e) {
            logBase(tag, log, e, TYPE_INFO);
        }
        public void i(String log) {
            i(log, null);
        }

        public void e(String log, Throwable e) {
            logBase(tag, log, e, TYPE_ERROR);
        }
        public void e(String log) {
            e(log, null);
        }

        public void d(String log, Throwable e) {
            logBase(tag, log, e, TYPE_DEBUG);
        }
        public void d(String log) {
            d(log, null);
        }

        public void w(String log, Throwable e) {
            logBase(tag, log, e, TYPE_WARNING);
        }
        public void w(String log) {
            w(log, null);
        }

    }

    public static final int TYPE_VERBOSE = 0;
    public static final int TYPE_ERROR = 1;
    public static final int TYPE_DEBUG = 2;
    public static final int TYPE_WARNING = 3;
    public static final int TYPE_INFO = 4;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TYPE_VERBOSE, TYPE_ERROR, TYPE_DEBUG, TYPE_WARNING, TYPE_INFO})
    public @interface LogLevel {}

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
	
	public static void setMainTag(String t) {
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
     *
     * @param tag tag
     * @param log string
     * @param e error
     * @param lvl level
     */
	private synchronized static void logBase(@Nullable String tag, @Nullable String log, @Nullable Throwable e, @Console.LogLevel int lvl) {
		if (isEnabled() && logWrites.size() > 0) {
			for(LogWriterBase lb : logWrites) {
                lb.writeLogLine(tag, log, e, lvl);
			}
		}
	}

	public static void loge(String log, Exception e) { logBase(TAG, log, e, TYPE_ERROR); }
	public static void loge(String log) { loge(log, null); }

    public static void logd(String log, Exception e) { logBase(TAG, log, e, TYPE_DEBUG); }
    public static void logd(String log) { logd(log, null); }
	
	public static void log(String log, Exception e) { logBase(TAG, log, e, TYPE_VERBOSE); }
	public static void log(String log) { logd(log, null); }

    public static void logv(String log, Exception e) { log(log, e); }
    public static void logv(String log) { log(log, null); }

    public static void logi(String log, Exception e) { logBase(TAG, log, e, TYPE_INFO); }
    public static void logi(String log) { logi(log, null); }

    public static void logw(String log, Exception e) { logBase(TAG, log, e, TYPE_WARNING); }
    public static void logw(String log) { logw(log, null); }

	public static synchronized void addLogWriter(LogWriterBase lw) {
		if (!Console.logWrites.contains(lw)) {
            Console.logWrites.add(lw);
        }
	}

    public static void addLogWriterLogCat() {
        addLogWriter(new LogWriterLogCat());
    }

    public static void addLogWriterDb(@NonNull Context ctx) {
        addLogWriter(new LogWriterDb(ctx));
    }

    public static void addLogWriterFile(@NonNull File f) {
        addLogWriter(new LogWriterFile(f));
    }

    public static void addLogWriterSystem() {
        addLogWriter(new LogWriterSystem());
    }

    public static void addLogWriterFileAsync(@NonNull String fileName) {
        addLogWriter(new LogWriterFileAsync(fileName));
    }
}
