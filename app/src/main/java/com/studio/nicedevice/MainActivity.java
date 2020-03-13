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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import static android.content.ContentValues.TAG;
import static java.lang.Thread.sleep;

public class MainActivity extends Activity {

    private Button propertiesButton;
    private Button logsButton;
    private Button googleConfig;
    private Switch switchdev;

    Button closePopupButton;
    PopupWindow popupWindow;
    LinearLayout linearLayout;
    ConstraintLayout constraintLayout;


    // Admin Ownwer

    public static final String NICEDEVICE_PACKAGE = "com.studio.nicedevice";
    public static final String[] APP_PACKAGES = {NICEDEVICE_PACKAGE};
    public DevicePolicyManager mDevicePolicyManager;
    public PackageManager mPackageManager;
    public ComponentName mAdminComponentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Popup Window
        closePopupButton = (Button) findViewById(R.id.closebuttonpopup);
        constraintLayout = (ConstraintLayout) findViewById(R.id.constrainlayout);

        SetAdminOwner();

        propertiesButton =findViewById(R.id.buttonfullproperties);

        propertiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(getApplicationContext(), PropertiesActivity.class);

                startActivity(intent1);
                finish();
            }
        });

        logsButton =findViewById(R.id.buttonlogcat);
        logsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(getApplicationContext(), logsActivity.class);

                startActivity(intent2);
                finish();
            }
        });

        googleConfig =findViewById(R.id.buttongoogleconfig);
        googleConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent3 = new Intent(getApplicationContext(), GoogleActivity.class);

                startActivity(intent3);
                finish();


            }
        });



        switchdev = findViewById(R.id.switch1);
        switchdev.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Popupwindow();


                    //startActivity(new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS));
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

    public void Popupwindow() {


        //instantiate the popup.xml layout file
        LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.popup, null);

        closePopupButton = (Button) customView.findViewById(R.id.closebuttonpopup);

        //instantiate popup window
        popupWindow = new PopupWindow(customView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //display the popup window
        popupWindow.showAtLocation(constraintLayout, Gravity.CENTER, 0, 0);

        //close the popup window on button click
        closePopupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

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







}
