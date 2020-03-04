package com.studio.nicedevice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class logsActivity extends PropertiesActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);



        executeCommandLine("adb logcat -c");

        executeCommandLine("adb logcat ");


    }
}
