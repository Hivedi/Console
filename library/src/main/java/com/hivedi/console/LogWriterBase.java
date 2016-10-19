package com.hivedi.console;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface LogWriterBase {
	//public abstract void saveHandler(String data, int type, Exception e);

    public void writeLogLine(@Nullable String tag, @Nullable String log, @Nullable Throwable e, @Console.LogLevel int lvl);
}
