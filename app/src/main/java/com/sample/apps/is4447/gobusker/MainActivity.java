package com.sample.apps.is4447.gobusker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sample.apps.is4447.gobusker.Busker.buskerLogin;
import com.sample.apps.is4447.gobusker.Fan.fanLogin;

public class MainActivity extends AppCompatActivity {
    Button fan, busker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fan = (Button) findViewById(R.id.btnFan);
        busker = (Button) findViewById(R.id.btnBusker);
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

    }
}