package com.sample.apps.is4447.gobusker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class fanHomePage extends AppCompatActivity {
    //declaring variables
    Button logout;

    private FirebaseUser fan;
    private DatabaseReference reference;
    private String FanID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fan_home_page);

        logout = (Button) findViewById(R.id.btnfanLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            //Code acquired and modified from this youtube video for firebase logout functionality
            //https://www.youtube.com/watch?v=DRBLazxi6Eg&ab_channel=CodeWithMazn
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(fanHomePage.this,MainActivity.class);
                startActivity(i);
            }
        });
        //Code acquired and modified from this youtube video for firebase User information display functionality
        //https://www.youtube.com/watch?v=-plgl1EQ21Q
        fan = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Fans");
        FanID = fan.getUid();

        //set variables that are connected to elements on the pages interface
        final TextView firstnameFan = (TextView) findViewById(R.id.tvFanShowFirst);
        final TextView surnameFan = (TextView) findViewById(R.id.tvFanShowSurname);
        final TextView emailFan = (TextView) findViewById(R.id.tvFanShowEmail);

        //search fan ID in Fan table
        reference.child(FanID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //fill info into single instance of fan class
                Fan fanProfile = snapshot.getValue(Fan.class);

                //if last part was successful, and busker class is filled
                if (fanProfile != null) {
                    //Fill new varibles with info from the busker class
                    String firstname = fanProfile.firstname;
                    String surname = fanProfile.secondname;
                    String email = fanProfile.email;

                    //Use these new varibales to set the text of the elements on the interface
                    firstnameFan.setText(firstname);
                    surnameFan.setText(surname);
                    emailFan.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(fanHomePage.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        });

    }
}