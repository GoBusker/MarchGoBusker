package com.sample.apps.is4447.gobusker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class buskerHomePage extends AppCompatActivity {

    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busker_home_page);

        logout = (Button) findViewById(R.id.btnbuskerLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            //Code acquired and modified from this youtube video for firebase logout functionality
            //https://www.youtube.com/watch?v=DRBLazxi6Eg&ab_channel=CodeWithMazn
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(buskerHomePage.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}