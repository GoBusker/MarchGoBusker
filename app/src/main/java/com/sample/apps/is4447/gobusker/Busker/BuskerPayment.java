package com.sample.apps.is4447.gobusker.Busker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.sample.apps.is4447.gobusker.Model.Busker;
import com.sample.apps.is4447.gobusker.R;

import java.util.HashMap;

public class BuskerPayment extends AppCompatActivity {

    EditText addPayment;
    Button btnPayment;

    FirebaseUser firebaseBusker;

    MaterialEditText payment10;

    MaterialEditText payment2;
    Button btnPayment2;

    MaterialEditText payment5;
    Button btnPayment5;

    MaterialEditText payment20;
    Button btnPayment20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busker_payment);

        payment10 = findViewById(R.id.addPayment);
        btnPayment = findViewById(R.id.btnPayment);

        payment2 = findViewById(R.id.addPayment2);
        btnPayment2 = findViewById(R.id.btnPayment2);

        payment5 = findViewById(R.id.addPayment5);
        btnPayment5 = findViewById(R.id.btnPayment5);

        payment20 = findViewById(R.id.addPayment20);
        btnPayment20 = findViewById(R.id.btnPayment20);

        firebaseBusker = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Buskers").child(firebaseBusker.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Busker busker = snapshot.getValue(Busker.class);
               payment10.setText(busker.getPayment10());
                payment2.setText(busker.getPayment2());
                payment5.setText(busker.getPayment5());
                payment20.setText(busker.getPayment20());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "10 euro donation set", Toast.LENGTH_SHORT).show();
                updateProfile(payment10.getText().toString());
            }
        });

        btnPayment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "2 euro donation set", Toast.LENGTH_SHORT).show();
                updateProfile2(payment2.getText().toString());
            }
        });

        btnPayment5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "5 euro donation set", Toast.LENGTH_SHORT).show();
                updateProfile5(payment5.getText().toString());
            }
        });

        btnPayment20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "20 euro donation set", Toast.LENGTH_SHORT).show();
                updateProfile20(payment20.getText().toString());
            }
        });



    }

    private void updateProfile(String payment10) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Buskers").child(firebaseBusker.getUid());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("payment10", payment10);
        reference.updateChildren(hashMap);
    }
    private void updateProfile2(String payment2) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Buskers").child(firebaseBusker.getUid());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("payment2", payment2);
        reference.updateChildren(hashMap);
    }
    private void updateProfile5(String payment5) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Buskers").child(firebaseBusker.getUid());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("payment5", payment5);
        reference.updateChildren(hashMap);
    }
    private void updateProfile20(String payment20) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Buskers").child(firebaseBusker.getUid());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("payment20", payment20);
        reference.updateChildren(hashMap);
    }
}