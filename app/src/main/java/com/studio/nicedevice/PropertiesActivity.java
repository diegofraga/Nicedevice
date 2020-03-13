package com.studio.nicedevice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import android.os.Build;

import static com.studio.nicedevice.R.id.textViewparameters;
import static com.studio.nicedevice.R.id.textViewgoogleparameters;
import static com.studio.nicedevice.R.id.textviewbrand;
import static com.studio.nicedevice.R.id.textviewname;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class PropertiesActivity extends Activity {



    // Creating the textviews

    TextView nametextview;
    TextView brandtextview;
    TextView parametersTextview;
    TextView googleTextview;


    // Create  vector of string
    String[] splited;
    // Create SpannableStringBuilder to put color in the results
    SpannableStringBuilder builder = new SpannableStringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_properties);

        //Set orientation to landscape
        //setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        // Project Name

        //Get the properties
        String stringoutput;
        stringoutput = String.valueOf(executeCommandLine(
                                        "getprop | grep ro.mid_board;" +
                                        "getprop | grep ro.build.product;" +
                                        "getprop | grep ro.product.name;" +
                                        "getprop | grep ro.product.board;" +
                                        "getprop | grep ro.product.model;" +
                                        "getprop | grep ro.product.device;" +
                                        "getprop | grep ro.product.vendor.model"));

        nametextview = findViewById(textviewname);
        nametextview.setText(OrganizeLine(stringoutput));

        // Brand Name

        stringoutput = String.valueOf(executeCommandLine(
                                        "getprop | grep ro.build.host;" +
                                        "getprop | grep ro.product.brand;" +
                                        "getprop | grep ro.product.mtp.name;" +
                                        "getprop | grep ro.product.usb.name;" +
                                        "getprop | grep ro.product.wifi.name;" +
                                        "getprop | grep ro.product.bluetooth.name;" +
                                        "getprop | grep ro.product.manufacturer;" +
                                        "getprop | grep ro.product.vendor.manufacturer;"));

        brandtextview = findViewById(textviewbrand);
        brandtextview.setText(OrganizeLine(stringoutput));

        // Parameters Project

        stringoutput = String.valueOf(executeCommandLine(
                                        "getprop | grep ro.vendor.mediatek.platform;" +
                                        "getprop | grep ro.product.vendor.device;" +
                                        "getprop | grep ro.build.fingerprint;"+
                                        "getprop | grep persist.sys.usb.config;"+
                                        "getprop | grep ro.sf.lcd_density;"+
                                        "getprop | grep ro.product.locale;"+
                                        "getprop | grep persist.sys.locale;"+
                                        "getprop | grep persist.sys.timezone;"));

        parametersTextview =findViewById(textViewparameters);
        parametersTextview.setText(OrganizeLine(stringoutput));


        // Google Parameters

        stringoutput = String.valueOf(executeCommandLine(
                                        "getprop | grep ro.build.type;" +
                                        "getprop | grep ro.build.version.security_patch;" +
                                        "getprop | grep ro.com.google.gmsversion;" +
                                        "getprop | grep ro.product.first_api_level;" +
                                        "getprop | grep ro.com.google.acsa;" +
                                        "getprop | grep ro.com.google.clientidbase;" +
                                        "getprop | grep ro.serialno;" +
                                        "getprop | grep ro.com.google.clientidbase.ms;"));

        googleTextview=findViewById(textViewgoogleparameters);
        //Get CPU device parameter
        builder = getCPUdevice().append(OrganizeLine(stringoutput));
        //Show all the parameters
        googleTextview.setText(builder);

    }



    //Creating a color function insert color after ":" checking in the int index

    public void colorStringvectorline(String[] string,SpannableStringBuilder builder){


        for (String s : string) {
            int index;
            index = s.indexOf(":") + 1;
            SpannableString str1 = new SpannableString(s);
            str1.setSpan(new ForegroundColorSpan(getColor(R.color.Greentext)), index, str1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.append(str1);
            builder.append("\n");


        }

    }



    // Organize the String beauty

    public SpannableStringBuilder OrganizeLine(String stringoutput){

        //Remove Some Especial caracteres
        stringoutput = stringoutput.replace("[", "").replace("]", "");

        //Split the string in a vector of lines "\n"
        splited = stringoutput.split("\\r?\\n");



        //Insert color on in the results (odd)
        builder = new SpannableStringBuilder();
        colorStringvectorline(splited,builder);
        return builder;

    }



    //Creating a color function insert 2 strings and color the second String:
    public void colorString2(String string1, String string2, SpannableStringBuilder builder){


        SpannableString str1 = new SpannableString(string1);
        builder.append(str1);
        SpannableString str2 = new SpannableString(string2);
        str2.setSpan(new ForegroundColorSpan(Color.parseColor("#5ec639")), 0, str2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );
        builder.append(" ");
        builder.append(str2);
        builder.append("\n");
    }

    //Creating a color function insert color in the second String(Odd index):

    public void colorStringvector(String[] string,SpannableStringBuilder builder){

        for (int i = 0; i < string.length; i++){
            SpannableString str1 = new SpannableString(string[i]);

            if((i%2)!=0){
                str1.setSpan(new ForegroundColorSpan(getColor(R.color.Greentext)), 0, str1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );
                builder.append(str1);
                builder.append("\n");
            }else {
                builder.append(str1);
                builder.append(" ");
            }


        }

    }

    public  SpannableStringBuilder getCPUdevice() {
        String CPU = Build.SUPPORTED_ABIS[0];
        SpannableStringBuilder builder = new SpannableStringBuilder();
        colorString2("cpu architecture:", CPU, builder);
        return builder;
    }

    //function execute command line (String) return output . This function its possible to use command "|"
    public static String executeCommandLine(String commandLine) {
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

            reader.close();
            process.waitFor();
            return output.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //Creating a color function insert 3 strings and color the second String:
    public void colorString2(String string1, String string2,String string3,  SpannableStringBuilder builder){


        SpannableString str1 = new SpannableString(string1);
        builder.append(str1);
        SpannableString str2 = new SpannableString(string2);
        builder.append(str2);
        SpannableString str3 = new SpannableString(string3);
        str2.setSpan(new ForegroundColorSpan(Color.parseColor("#5ec639")), 0, str2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );
        builder.append(" ");
        builder.append(str2);
        builder.append("\n");
    }

    // Organize the String beauty

    public SpannableStringBuilder Organize(String stringoutput){

        //Remove Some Especial caracteres
        stringoutput = stringoutput.replace("[", "").replace("]", "");

        //Split the string in a vector of strings (words)
        splited = stringoutput.split("\\s");

        //Insert color on in the results (odd)
        builder = new SpannableStringBuilder();
        colorStringvector(splited,builder);

        return builder;

    }


    // App functions
    // Add the back button to return the MainActivity

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PropertiesActivity.this,MainActivity.class);
        //intent2.putExtra("tipo", vc_TipoEmitente);
        //intent2.putExtra("mensagem", "");
        startActivity(intent);
        finish();
    }

    // Check if the device is a tablet
    public static boolean isTablet(Context ctx){
        return (ctx.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

}
