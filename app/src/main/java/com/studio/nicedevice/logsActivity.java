package com.studio.nicedevice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.PrivateKey;
import java.text.SimpleDateFormat;
import java.util.Date;

public class logsActivity extends Activity {



    private Button clearlog;
    private Button startlog;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);

        clearlog = findViewById(R.id.buttonClearlogs);
        clearlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                PropertiesActivity.executeCommandLine("adb logcat -c");
            }
        });

        startlog = findViewById(R.id.buttonlogcat);
        startlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                collectLogCat("llogcat" );
                Log.e("TESTE", "START LOG");

            }
        });



    }



    public void collectLogCat(String commandLine) {


        File storageDir =  logsActivity.this.getExternalFilesDir(Environment.DIRECTORY_DCIM);
        File file = null;
        try {
            file = File.createTempFile(logname(), ".txt", storageDir);
            //BufferedWriter buf = new BufferedWriter(new FileWriter(file, true));
            //buf.append("hi this will write in to file");
           // buf.newLine();  // pointer will be nextline
           // buf.close();

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
            StringBuilder output= new StringBuilder();
            BufferedWriter buf =  new BufferedWriter(new FileWriter(file, true));

         //   while ((read = reader.readLine())!=null){
                output.append("TESTE FRAGA");

                Log.d("executed command ", output.toString());
                buf.append("teste bosta");
                buf.newLine();
        //    }
            buf.close();
            reader.close();
            process.waitFor();

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

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


    public String logname(){
        //Get Product Name
        String device ;
        device = String.valueOf(PropertiesActivity.executeCommandLine("getprop | grep ro.build.product"));
        device = device.replace("[", "").replace("]", "").replace("\n","");
        device = device.substring(18);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        device = device + "-" + timeStamp;
        return device;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(logsActivity.this,MainActivity.class);
        //intent2.putExtra("tipo", vc_TipoEmitente);
        //intent2.putExtra("mensagem", "");
        startActivity(intent);
        finish();
    }

}
