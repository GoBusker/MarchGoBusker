package com.sample.apps.is4447.gobusker.Fan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sample.apps.is4447.gobusker.Model.Busker;
import com.sample.apps.is4447.gobusker.R;

public class FanPayment extends AppCompatActivity {

    FirebaseUser firebaseBusker;
    String profileid;

    TextView send_payment, send_payment2, send_payment5, send_payment20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fan_payment);

        firebaseBusker = FirebaseAuth.getInstance().getCurrentUser();

        send_payment = findViewById(R.id.send_payments);
        send_payment.setMovementMethod(LinkMovementMethod.getInstance());

        send_payment2 = findViewById(R.id.send_payments2);
        send_payment2.setMovementMethod(LinkMovementMethod.getInstance());

        send_payment5 = findViewById(R.id.send_payments5);
        send_payment5.setMovementMethod(LinkMovementMethod.getInstance());

        send_payment20 = findViewById(R.id.send_payments20);
        send_payment20.setMovementMethod(LinkMovementMethod.getInstance());



        SharedPreferences prefs = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        profileid = prefs.getString("profileid", "none");

        buskerInfo();


    }

    private void buskerInfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Buskers").child(profileid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Busker busker = dataSnapshot.getValue(Busker.class);
                send_payment.setText(busker.getPayment10());
                send_payment.setMovementMethod(LinkMovementMethod.getInstance());
                send_payment2.setText(busker.getPayment2());
                send_payment2.setMovementMethod(LinkMovementMethod.getInstance());
                send_payment5.setText(busker.getPayment5());
                send_payment5.setMovementMethod(LinkMovementMethod.getInstance());
                send_payment20.setText(busker.getPayment20());
                send_payment20.setMovementMethod(LinkMovementMethod.getInstance());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}