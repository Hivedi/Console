package com.hivedi.console_example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hivedi.console.Console;

public class MainActivity extends AppCompatActivity {

    private final Console.Log LOG1 = new Console.Log("test1", "test2");
    private final Console.Log LOG2 = new Console.Log("LOG2");
    private final Console.Log LOG3 = new Console.Log();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void handleLog(View v) {
        if (BuildConfig.DEBUG) {
            Console.logi("Test log info");
            LOG1.d("LOG1");
            LOG2.d("LOG2");
            LOG3.d("LOG3");
        }
    }

}
