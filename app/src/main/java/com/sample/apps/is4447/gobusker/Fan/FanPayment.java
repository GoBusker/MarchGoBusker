package com.sample.apps.is4447.gobusker.Fan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sample.apps.is4447.gobusker.FanFragments.FanHomeFragment;
import com.sample.apps.is4447.gobusker.FanFragments.FanNotificationFragment;
import com.sample.apps.is4447.gobusker.FanFragments.FanOwnProfileFragment;
import com.sample.apps.is4447.gobusker.FanFragments.FanSearchFragment;
import com.sample.apps.is4447.gobusker.Model.Busker;
import com.sample.apps.is4447.gobusker.R;

public class FanPayment extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {

    FirebaseUser firebaseBusker;
    String profileid;

    TextView send_payment, send_payment2, send_payment5, send_payment20, buskerdonationname;

    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment = null;

    private RadioGroup radioGroup;

    RadioButton radiobutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fan_payment);

        firebaseBusker = FirebaseAuth.getInstance().getCurrentUser();

        send_payment = findViewById(R.id.send_payments);
        send_payment.setMovementMethod(LinkMovementMethod.getInstance());

        buskerdonationname = findViewById(R.id.buskerdonationname);

        radioGroup = findViewById(R.id.radio);


        SharedPreferences prefs = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        profileid = prefs.getString("profileid", "none");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Buskers").child(profileid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Busker busker = snapshot.getValue(Busker.class);
               buskerdonationname.setText(busker.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //buskerInfo();
        //tenInfo();

//
//        Spinner spinner = findViewById(R.id.fan_payment_spinner);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.fandonate, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(this);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.two:
                        twoInfo();
                        break;
                    case R.id.five:
                        fiveInfo();
                        break;
                    case R.id.ten:
                        tenInfo();
                        break;
                    case R.id.twenty:
                        twentyInfo();
                        break;
                }
            }
        });


    }

    // I referenced this Youtube video for the bottom navigation
//https://www.youtube.com/watch?v=Kovj7Xyy6_g&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=3&ab_channel=KODDev


//    private void buskerInfo() {
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Buskers").child(profileid);
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Busker busker = dataSnapshot.getValue(Busker.class);
//                send_payment.setText(busker.getPayment10());
//                send_payment.setMovementMethod(LinkMovementMethod.getInstance());
//                send_payment2.setText(busker.getPayment2());
//                send_payment2.setMovementMethod(LinkMovementMethod.getInstance());
//                send_payment5.setText(busker.getPayment5());
//                send_payment5.setMovementMethod(LinkMovementMethod.getInstance());
//                send_payment20.setText(busker.getPayment20());
//                send_payment20.setMovementMethod(LinkMovementMethod.getInstance());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 1) {
            twoInfo();
        } else if(position == 2){
            fiveInfo();
        } else if (position == 3){
            tenInfo();
        } else if (position == 4){
            twentyInfo();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void tenInfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Buskers").child(profileid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Busker busker = dataSnapshot.getValue(Busker.class);
                send_payment.setText(busker.getPayment10());
                send_payment.setMovementMethod(LinkMovementMethod.getInstance());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void twoInfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Buskers").child(profileid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Busker busker = dataSnapshot.getValue(Busker.class);
                send_payment.setText(busker.getPayment2());
                send_payment.setMovementMethod(LinkMovementMethod.getInstance());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void fiveInfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Buskers").child(profileid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Busker busker = dataSnapshot.getValue(Busker.class);
                send_payment.setText(busker.getPayment5());
                send_payment.setMovementMethod(LinkMovementMethod.getInstance());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void twentyInfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Buskers").child(profileid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Busker busker = dataSnapshot.getValue(Busker.class);
                send_payment.setText(busker.getPayment20());
                send_payment.setMovementMethod(LinkMovementMethod.getInstance());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}