package com.studio.nicedevice;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.concurrent.ExecutionException;

import static android.content.ContentValues.TAG;
import static java.lang.Thread.sleep;


public class MainActivity extends Activity {

    private Button propertiesButton;
    private Button logsButton;
    private Button googleConfig;
    private Switch switchdev;




    public static final String NICEDEVICE_PACKAGE = "com.studio.nicedevice";
    public static final String[] APP_PACKAGES = {NICEDEVICE_PACKAGE};
    public DevicePolicyManager mDevicePolicyManager;
    public PackageManager mPackageManager;
    public ComponentName mAdminComponentName;

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

                 Intent intent = new Intent(getApplicationContext(), logsActivity.class);

                 startActivity(intent);
                 finish();
            }
        });

        googleConfig =findViewById(R.id.buttongoogleconfig);
        googleConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), GoogleActivity.class);
                startActivity(intent);
                finish();


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


            Intent intent = new Intent(getApplicationContext(), PopupActivity.class);

            startActivity(intent);
            //Toast.makeText(this, "Device Owner not set", Toast.LENGTH_LONG).show();
            //Log.e(TAG, "Device owner not set");
            //Log.e(TAG, e.toString());
            //e.printStackTrace();


        }


    }







}
