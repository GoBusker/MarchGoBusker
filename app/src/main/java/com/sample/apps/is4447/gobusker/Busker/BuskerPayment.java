package com.sample.apps.is4447.gobusker.Busker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

public class BuskerPayment extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {

    EditText addPayment;
    Button btnPayment;

    FirebaseUser firebaseBusker;

    MaterialEditText payment10;

    private RadioGroup radioGroup;

    RadioButton radiobutton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busker_payment);

//        Spinner spinner = findViewById(R.id.busker_payment_spinner);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.fandonate, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(this);


        payment10 = findViewById(R.id.addPayment);
        btnPayment = findViewById(R.id.btnPayment);


        radioGroup = findViewById(R.id.radio);

        RadioButton checkedRadioButton = (RadioButton)radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());



        firebaseBusker = FirebaseAuth.getInstance().getCurrentUser();

//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Buskers").child(firebaseBusker.getUid());
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Busker busker = snapshot.getValue(Busker.class);
//               payment10.setText(busker.getPayment10());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioid = radioGroup.getCheckedRadioButtonId();
                radiobutton = findViewById(radioid);
                if (radioid == -1) {
                    Toast.makeText(getApplicationContext(), "No donation set", Toast.LENGTH_SHORT).show();
                } else {
                    findradiobutton(radioid);
                }
            }


        });

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



    private void findradiobutton(int radioid) {

            switch (radioid){
                case R.id.two:
                    Toast.makeText(getApplicationContext(), "2 euro donation set", Toast.LENGTH_SHORT).show();
                    updateProfile2(payment10.getText().toString());
                    break;
                case R.id.five:
                    Toast.makeText(getApplicationContext(), "5 euro donation set", Toast.LENGTH_SHORT).show();
                    updateProfile5(payment10.getText().toString());
                    break;
                case R.id.ten:
                    Toast.makeText(getApplicationContext(), "10 euro donation set", Toast.LENGTH_SHORT).show();
                    updateProfile(payment10.getText().toString());
                    break;
                case R.id.twenty:
                    Toast.makeText(getApplicationContext(), "20 euro donation set", Toast.LENGTH_SHORT).show();
                    updateProfile20(payment10.getText().toString());
                    break;
            }
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {

        } else if(position == 1){
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
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Buskers").child(firebaseBusker.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Busker busker = dataSnapshot.getValue(Busker.class);
                payment10.setText(busker.getPayment10());
                payment10.setMovementMethod(LinkMovementMethod.getInstance());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void twoInfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Buskers").child(firebaseBusker.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Busker busker = dataSnapshot.getValue(Busker.class);
                payment10.setText(busker.getPayment2());
               payment10.setMovementMethod(LinkMovementMethod.getInstance());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void fiveInfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Buskers").child(firebaseBusker.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Busker busker = dataSnapshot.getValue(Busker.class);
                payment10.setText(busker.getPayment5());
                payment10.setMovementMethod(LinkMovementMethod.getInstance());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void twentyInfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Buskers").child(firebaseBusker.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Busker busker = dataSnapshot.getValue(Busker.class);
                payment10.setText(busker.getPayment20());
                payment10.setMovementMethod(LinkMovementMethod.getInstance());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}