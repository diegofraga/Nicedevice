package com.studio.nicedevice;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.studio.nicedevice.R.id.textviewproperties;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Properties extends Activity {




    TextView propertiestextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_properties);

        
        propertiestextview = findViewById(textviewproperties);
        propertiestextview.setText(executeCommandLine("getprop | grep cpu.abi"));




    }

    public String executeCommandLine(String commandLine) {
        try {

            String[] cmd = {
                    "/bin/sh",
                    "-c",
                    commandLine
            };
            Process process;
            process = Runtime.getRuntime().exec(cmd);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String read;
            StringBuilder output=new StringBuilder();


            while ((read = reader.readLine())!=null){
                output.append(read);
                output.append("\n");
                Log.d("executed command ", output.toString());
            }

            //propertiestextview.setText(output.toString());
            reader.close();
            process.waitFor();
            return output.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
