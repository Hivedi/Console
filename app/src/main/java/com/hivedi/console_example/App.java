package com.hivedi.console_example;

import android.app.Application;

import com.hivedi.console.Console;

/**
 * Created by Kenumir on 2015-10-26.
 *
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Console.setEnabled(true);
        Console.setMainTag("console_tests");
        Console.addLogWriterLogCat();
        Console.addLogWriterSystem();
    }
}
