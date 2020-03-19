package com.studio.nicedevice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.security.KeyStore;

import static android.content.ContentValues.TAG;

public class GoogleActivity extends MainActivity {




    private ProgressBar spinner;


    private ImageView imagedevmode;
    private ImageView imageadbmode;
    private ImageView imagestayawake;
    private ImageView imagewifi;
    private ImageView imagetime;


    private TextView time;
    private TextView displaysettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google);

        imagedevmode = findViewById(R.id.Checkdevmode);
        imageadbmode = findViewById(R.id.Checkgreenadb);
        imagestayawake = findViewById(R.id.Checkstayawake);
        imagewifi = findViewById(R.id.Checkwifi);
        imagetime = findViewById(R.id.Checktime);
        time = findViewById(R.id.enabletime);
        displaysettings = findViewById(R.id.enabledisplaysettings);

        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.VISIBLE);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                spinner.setVisibility(View.INVISIBLE);
            }
        }, 3000);

        try {
            configGoogle();
        }catch (Exception e){
            Log.e("GOOGLE ACTIVITY", "Device owner not set");
            Log.e("GOOGLE ACTIVITY", e.toString());
            e.printStackTrace();
        }

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS));


            }
        });

        displaysettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(android.provider.Settings.ACTION_DISPLAY_SETTINGS));

            }
        });

    }

    private void configGoogle(){

        enableDevelopmentMode(true);
        enableADB(true);
        enableStayOnWhilePluggedIn(true);
        enableWIFI(true);
        setNetworks();
        enableTime(true);
        enableTimeZone(true);


    }




    private void connectWifi(String SSID , String PASSWORD) {


        WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.SSID =  String.format("\"%s\"", SSID);
        wifiConfig.preSharedKey = String.format("\"%s\"", PASSWORD);
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        int netId = wifiManager.addNetwork(wifiConfig);
        wifiManager.disconnect();
        wifiManager.enableNetwork(netId, true);
        wifiManager.reconnect();
    }

    private void setNetworks(){
        try {

            connectWifi("android_2.4", "android7932");
            connectWifi("android_5", "android7932");
            connectWifi("Fraga", "16011991");
            connectWifi("Google_Approval", "android7932");
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    imagewifi.setVisibility(View.VISIBLE);
                }
            }, 2000);

        }catch (Exception e){
            imagewifi.setVisibility(View.INVISIBLE);

        }

    }



    private void enableDevelopmentMode(boolean enabled){
        if (enabled) {
            try {
                mDevicePolicyManager.setGlobalSetting(
                        mAdminComponentName,
                        Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, "1");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imagedevmode.setVisibility(View.VISIBLE);
                    }
                }, 2000);

            }catch (Exception e){

            imagedevmode.setVisibility(View.INVISIBLE);
            }

        }else{
            mDevicePolicyManager.setGlobalSetting(
                    mAdminComponentName,
                    Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, "0");
        }
    }


    private void enableADB(boolean enabled){
        if (enabled) {
            try {
            mDevicePolicyManager.setGlobalSetting(
                    mAdminComponentName,
                    Settings.Global.ADB_ENABLED, "1");

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    imageadbmode.setVisibility(View.VISIBLE);
                }
            }, 2000);

        }catch (Exception e){
                imageadbmode.setVisibility(View.INVISIBLE);
        }


        }else{
            mDevicePolicyManager.setGlobalSetting(
                    mAdminComponentName,
                    Settings.Global.ADB_ENABLED, "0");
        }
    }



    private void enableStayOnWhilePluggedIn(boolean enabled){
        if (enabled) {
            try {
            mDevicePolicyManager.setGlobalSetting(
                    mAdminComponentName,
                    Settings.Global.STAY_ON_WHILE_PLUGGED_IN,
                    Integer.toString(BatteryManager.BATTERY_PLUGGED_AC
                            | BatteryManager.BATTERY_PLUGGED_USB
                            | BatteryManager.BATTERY_PLUGGED_WIRELESS));


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    imagestayawake.setVisibility(View.VISIBLE);
                }
            }, 2000);

            }catch (Exception e){
                imagestayawake.setVisibility(View.INVISIBLE);
            }

        } else {
            mDevicePolicyManager.setGlobalSetting(
                    mAdminComponentName,
                    Settings.Global.STAY_ON_WHILE_PLUGGED_IN,
                    "0"
            );
        }
    }




    private void enableWIFI(boolean enabled){
        if (enabled) {
            mDevicePolicyManager.setGlobalSetting(
                    mAdminComponentName,
                    Settings.Global.WIFI_ON, "1");

        }else{
            mDevicePolicyManager.setGlobalSetting(
                    mAdminComponentName,
                    Settings.Global.WIFI_ON, "0");
        }
    }
    private void enableTime(boolean enabled){
        if (enabled) {
            mDevicePolicyManager.setGlobalSetting(
                    mAdminComponentName,
                    Settings.Global.AUTO_TIME, "1");

        }else{
            mDevicePolicyManager.setGlobalSetting(
                    mAdminComponentName,
                    Settings.Global.AUTO_TIME, "0");
        }
    }
    private void enableTimeZone(boolean enabled){
        if (enabled) {
            mDevicePolicyManager.setGlobalSetting(
                    mAdminComponentName,
                    Settings.Global.AUTO_TIME_ZONE, "1");

        }else{
            mDevicePolicyManager.setGlobalSetting(
                    mAdminComponentName,
                    Settings.Global.AUTO_TIME_ZONE, "0");
        }
    }



    public void SetAdminOwner() {

        // Retrieve Device Policy Manager so that we can check whether we can lock to screen later
        mDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);

        // Retrieve DeviceAdminReceiver ComponentName so we can make device management api calls later
        mAdminComponentName = DeviceAdminReceiver.getComponentName(this);
        Log.d(TAG, "mAdminComponentName:" + mAdminComponentName);

        // Retrieve Package Manager so that we can enable and disable LockedActivity
        mPackageManager = this.getPackageManager();

        try {
            mDevicePolicyManager.setLockTaskPackages(mAdminComponentName, APP_PACKAGES);
        } catch (Exception e) {


            //startActivity(intent_popUp);
            Toast.makeText(this, "Device Owner not set", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Device owner not set");
            Log.e(TAG, e.toString());
            e.printStackTrace();


        }


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(GoogleActivity.this,MainActivity.class);
        //intent2.putExtra("tipo", vc_TipoEmitente);
        //intent2.putExtra("mensagem", "");
        startActivity(intent);
        finish();
    }
}
