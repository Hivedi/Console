package com.hivedi.console;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by Hivedi2 on 2016-10-13.
 *
 */

public class LogWriterFileAsync implements LogWriterBase {

    private static final String TASK_NAME = "Console File Log";
	private final ExecutorService exec = Executors.newSingleThreadExecutor(new ThreadFactory(){
        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, TASK_NAME);
        }
    });
	private File f = null;
    private String nameOfFile;

    public LogWriterFileAsync(@NonNull String fileName) {
        nameOfFile = fileName;
    }

	private void saveLog(@Nullable String tag, @Nullable String log, @Nullable Throwable e, @Console.LogLevel int lvl) {
        if (f == null) {
            f = new File(Environment.getExternalStorageDirectory(), nameOfFile);
        }

        String logSaveTxt = log;
        StringWriter logType;
        if(e != null) {
            logType = new StringWriter();
            PrintWriter pw = new PrintWriter(logType);
            e.printStackTrace(pw);
            logSaveTxt = log + "                                   \n--- STACK TRACE ---\n" + logType.toString().replace("\n", "\n                                   ");
        }

        String typeString;
        switch(lvl) {
            default:
            case Console.TYPE_VERBOSE : typeString = "VERBOSE"; break;
            case Console.TYPE_ERROR   : typeString = "ERROR  "; break;
            case Console.TYPE_DEBUG   : typeString = "DEBUG  "; break;
            case Console.TYPE_WARNING : typeString = "WARNING"; break;
            case Console.TYPE_INFO    : typeString = "INFO   "; break;
        }

		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(this.f, true));
			out.write("[" + now() + "][" + typeString + "]" + (tag != null ? "[" + tag + "]" : "") + " : ");
			out.write(logSaveTxt != null ? logSaveTxt.replace("\n", "\n                                   ") : "");
			out.newLine();
			out.flush();
		} catch (IOException var13) {
			// ignore
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch (IOException var12) {
					// ignore
				}
			}
		}
	}
	private String now() {
		return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())).format(System.currentTimeMillis());
	}

    @Override
    public void writeLogLine(final @Nullable String tag, final @Nullable String log, final @Nullable Throwable e, final @Console.LogLevel int lvl) {
        if (Build.VERSION.SDK_INT >= 11) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    saveLog(tag, log, e, lvl);
                    return null;
                }
            }.executeOnExecutor(exec);
        } else {
            new Thread(){
                @Override
                public void run() {
                    Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                    Thread.currentThread().setName(TASK_NAME);
                    saveLog(tag, log, e, lvl);
                }
            }.start();
        }
    }
}
