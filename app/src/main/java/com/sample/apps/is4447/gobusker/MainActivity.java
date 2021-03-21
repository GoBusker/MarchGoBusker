package com.sample.apps.is4447.gobusker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.sample.apps.is4447.gobusker.Busker.buskerLogin;
import com.sample.apps.is4447.gobusker.Fan.fanLogin;

public class MainActivity extends AppCompatActivity {
    Button fan, busker;
    ImageButton introback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fan = (Button) findViewById(R.id.btnFan);
        busker = (Button) findViewById(R.id.btnBusker);
        introback = (ImageButton) findViewById(R.id.back_intro);
        //intents to send user to busker or fan page
        busker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, buskerLogin.class);
                startActivity(i);
            }
        });
        fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, fanLogin.class);
                startActivity(i);
            }
        });
        findViewById(R.id.back_intro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // We normally won't show the welcome slider again in real app
                // but this is for testing
                PrefManager prefManager = new PrefManager(getApplicationContext());

                // make first time launch TRUE
                prefManager.setFirstTimeLaunch(true);

                startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
                finish();
            }
        });

    }
}