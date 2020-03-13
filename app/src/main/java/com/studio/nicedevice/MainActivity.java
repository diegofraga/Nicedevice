package com.studio.nicedevice;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.wifi.WifiConfiguration;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import static android.content.ContentValues.TAG;
import static android.provider.Settings.Global.DEVICE_NAME;
import static java.lang.Thread.sleep;

public class MainActivity extends Activity {

    private Button propertiesButton;
    private Button logsButton;
    private Button googleConfig;
    private Switch switchdev;



    public static final String NICEDEVICE_PACKAGE = "com.studio.nicedevice";
    public static final String[] APP_PACKAGES = {NICEDEVICE_PACKAGE};


    public DevicePolicyManager mDevicePolicyManager;
    private PackageManager mPackageManager;
    private ComponentName mAdminComponentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SetAdminOwner();


        propertiesButton =findViewById(R.id.buttonfullproperties);

        propertiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), PropertiesActivity.class);

                startActivity(intent);
                finish();
            }
        });

        logsButton =findViewById(R.id.buttonlogcat);
        logsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent3 = new Intent(getApplicationContext(), logsActivity.class);

                startActivity(intent3);
                finish();
            }
        });

        googleConfig =findViewById(R.id.buttongoogleconfig);
        googleConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                configGoogle();


            }
        });





        switchdev = findViewById(R.id.switch1);
        switchdev.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    startActivity(new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS));
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            switchdev.setChecked(false);
                        }
                    }, 2000);
                }

            }
        });


    }


    public static boolean isTablet(Context ctx){
        return (ctx.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
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
            Toast.makeText(this, "Device Owner not set", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Device owner not set");
            Log.e(TAG, e.toString());
            e.printStackTrace();
        }


    }



    private void configGoogle(){

        enableADB(true);
        enableDevelopmentMode(true);
        enableStayOnWhilePluggedIn(true);
        enableWIFI(true);
        connectWifi("android_2.4","android7932");
        connectWifi("android_5","android7932");
        connectWifi("Google_Approval","android7932");
        //enableTime(true);
        enableTimeZone(true);
        startActivity(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS));



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
    private void enableDevelopmentMode(boolean enabled){
        if (enabled) {
            mDevicePolicyManager.setGlobalSetting(
                    mAdminComponentName,
                    Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, "1");

        }else{
            mDevicePolicyManager.setGlobalSetting(
                    mAdminComponentName,
                    Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, "0");
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


    private void enableADB(boolean enabled){
        if (enabled) {
            mDevicePolicyManager.setGlobalSetting(
                    mAdminComponentName,
                    Settings.Global.ADB_ENABLED, "1");

        }else{
            mDevicePolicyManager.setGlobalSetting(
                    mAdminComponentName,
                    Settings.Global.ADB_ENABLED, "0");
        }
    }

    private void enableStayOnWhilePluggedIn(boolean enabled){
        if (enabled) {
            mDevicePolicyManager.setGlobalSetting(
                    mAdminComponentName,
                    Settings.Global.STAY_ON_WHILE_PLUGGED_IN,
                    Integer.toString(BatteryManager.BATTERY_PLUGGED_AC
                            | BatteryManager.BATTERY_PLUGGED_USB
                            | BatteryManager.BATTERY_PLUGGED_WIRELESS));
        } else {
            mDevicePolicyManager.setGlobalSetting(
                    mAdminComponentName,
                    Settings.Global.STAY_ON_WHILE_PLUGGED_IN,
                    "0"
            );
        }
    }

}
