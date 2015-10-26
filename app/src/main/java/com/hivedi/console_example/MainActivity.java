package com.hivedi.console_example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hivedi.console.Console;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void handleLog(View v) {
        if (BuildConfig.DEBUG) {
            Console.logi("Test log info");
        }
    }

}
