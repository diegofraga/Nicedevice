package com.studio.nicedevice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.security.KeyStore;

import static android.content.ContentValues.TAG;

public class GoogleActivity extends MainActivity {




    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google);



        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.VISIBLE);

        try {
            configGoogle();
        }catch (Exception e){
            Log.e("GOOGLE ACTIVITY", "Device owner not set");
            Log.e("GOOGLE ACTIVITY", e.toString());
            e.printStackTrace();
        }
        //spinner.setVisibility(View.GONE);
    }














    private void configGoogle(){



        enableADB(true);
        enableDevelopmentMode(true);
        enableStayOnWhilePluggedIn(true);
        enableWIFI(true);
        connectWifi("android_2.4","android7932");
        connectWifi("android_5","android7932");
        connectWifi("Google_Approval","android7932");
        enableTime(true);
        enableTimeZone(true);
        //startActivity(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS));

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










    @Override
    public void onBackPressed() {
        Intent intent = new Intent(GoogleActivity.this,MainActivity.class);
        //intent2.putExtra("tipo", vc_TipoEmitente);
        //intent2.putExtra("mensagem", "");
        startActivity(intent);
        finish();
    }
}
