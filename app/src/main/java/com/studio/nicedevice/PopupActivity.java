package com.studio.nicedevice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class PopupActivity extends Activity {



    Button closePopupButton;
    TextView commandSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);


        closePopupButton = findViewById(R.id.ClosePopupButton);
        commandSet = findViewById(R.id.setdeviceowner1);
        closePopupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();


            }
        });

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(600),(int)(300) );
        //getWindow().setLayout((int)(width*.9),(int)(height*.35) );


        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);


    }
}
