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

public class buskerHomePage extends AppCompatActivity {
    //declaring variables
    private FirebaseUser busker;
    private DatabaseReference reference;
    private String BuskerID;

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
        //Code acquired and modified from this youtube video for firebase User information display functionality
        //https://www.youtube.com/watch?v=-plgl1EQ21Q

        busker = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Buskers");
        BuskerID = busker.getUid();

        //set variables that are connected to elements on the pages interface
        final TextView firstnameBusker = (TextView) findViewById(R.id.tvBuskerShowFirst);
        final TextView surnameBusker = (TextView) findViewById(R.id.tvBuskerShowSurname);
        final TextView emailBusker = (TextView) findViewById(R.id.tvBuskerShowEmail);

        //search busker ID in Busker table
        reference.child(BuskerID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //fill info into single instance of busker class
                Busker buskerProfile = snapshot.getValue(Busker.class);

                //if last part was successful, and busker class is filled
                if(buskerProfile != null){
                    //Fill new varibles with info from the busker class
                    String firstName = buskerProfile.firstname;
                    String surname = buskerProfile.secondname;
                    String email = buskerProfile.email;

                    //Use these new varibales to set the text of the elements on the interface
                    firstnameBusker.setText(firstName);
                    surnameBusker.setText(surname);
                    emailBusker.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(buskerHomePage.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        });

    }
}