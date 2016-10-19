package com.hivedi.console;

import android.support.annotation.Nullable;
import android.util.Log;

public class LogWriterLogCat implements LogWriterBase {

    @Override
    public void writeLogLine(@Nullable String tag, @Nullable String log, @Nullable Throwable e, @Console.LogLevel int lvl) {
        switch(lvl) {
            default:
            case Console.TYPE_VERBOSE : Log.v(tag, log); break;
            case Console.TYPE_ERROR   : Log.e(tag, log); break;
            case Console.TYPE_DEBUG   : Log.d(tag, log); break;
            case Console.TYPE_WARNING : Log.w(tag, log); break;
            case Console.TYPE_INFO    : Log.i(tag, log); break;
        }
        if (e != null) {
            e.printStackTrace();
        }
    }

}
