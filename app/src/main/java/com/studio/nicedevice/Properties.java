package com.studio.nicedevice;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.studio.nicedevice.R.id.textViewparameters;
import static com.studio.nicedevice.R.id.textviewbrand;
import static com.studio.nicedevice.R.id.textviewname;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Properties extends Activity {




    TextView nametextview;
    TextView brandtextview;
    TextView parametersTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_properties);


        //Get getprop from device
        executeCommandLine("getprop");

        // Insert StringBuilder output to the String
        //Need to update work only with the string stringoutput
       // String stringoutput = String.valueOf(executeCommandLine("getprop"));



        // Project Name

        nametextview = findViewById(textviewname);;
        nametextview.setMovementMethod(new ScrollingMovementMethod());
        String stringoutput = String.valueOf(executeCommandLine("getprop | grep ro.build.product;" +
                "getprop | grep ro.mid_board;" +
                "getprop | grep ro.product.board;" +
                "getprop | grep ro.product.device;" +
                "getprop | grep ro.product.model;" +
                "getprop | grep ro.product.name;" +
                "getprop | grep ro.product.vendor.model"));

        stringoutput = stringoutput.replace("[", "");
        stringoutput = stringoutput.replace("]", "");
        nametextview.setText(stringoutput);

        // Brand Name

        brandtextview = findViewById(textviewbrand);
        brandtextview.setMovementMethod(new ScrollingMovementMethod());
        stringoutput = String.valueOf(executeCommandLine("getprop | grep ro.product.brand;" +
                "getprop | grep ro.product.manufacturer;" +
                "getprop | grep ro.build.host;" +
                "getprop | grep ro.product.vendor.manufacturer;"));

        stringoutput = stringoutput.replace("[", "");
        stringoutput = stringoutput.replace("]", "");
        brandtextview.setText(stringoutput);

        // Parameters Project

        parametersTextview =findViewById(textViewparameters);
        parametersTextview.setMovementMethod(new ScrollingMovementMethod());
        stringoutput = String.valueOf(executeCommandLine("getprop | grep ro.build.version.security_patch;" +
                "getprop | grep ro.com.google.acsa;" +
                "getprop | grep ro.com.google.gmsversion;" +
                "getprop | grep ro.product.first_api_level;" +
                "getprop | grep persist.sys.usb.config;"+
                "getprop | grep persist.sys.timezone;" +
                "getprop | grep ro.product.locale;"+
                "getprop | grep persist.sys.locale;" +
                "getprop | grep ro.sf.lcd_density"));

        stringoutput = stringoutput.replace("[", "");
        stringoutput = stringoutput.replace("]", "");
        parametersTextview.setText(stringoutput);


    }

    //function execute command line (String) return output
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
