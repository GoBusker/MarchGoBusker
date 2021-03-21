package com.sample.apps.is4447.gobusker.FanFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sample.apps.is4447.gobusker.Model.Busker;
import com.sample.apps.is4447.gobusker.R;

public class FanPaymentFragment extends Fragment implements AdapterView.OnItemSelectedListener{


    FirebaseUser firebaseBusker;
    String profileid;

    TextView send_payment, send_payment2, send_payment5, send_payment20;

    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment = null;

    private RadioGroup radioGroup;

    RadioButton radiobutton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fan_payment,container, false);

        firebaseBusker = FirebaseAuth.getInstance().getCurrentUser();

        send_payment = view.findViewById(R.id.send_payments);
        send_payment.setMovementMethod(LinkMovementMethod.getInstance());




        SharedPreferences prefs = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        profileid = prefs.getString("profileid", "none");

        //buskerInfo();
        //tenInfo();


//        Spinner spinner = view.findViewById(R.id.fan_payment_spinner);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
//                R.array.fandonate, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(this);

        bottomNavigationView = view.findViewById(R.id.payment_fan_bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

                 getFragmentManager().beginTransaction().replace(R.id.fragment_container_fan,
                new FanPaymentFragment()).commit();

        return view;
    }
    // I referenced this Youtube video for the bottom navigation
//https://www.youtube.com/watch?v=Kovj7Xyy6_g&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=3&ab_channel=KODDev
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new FanHomeFragment();
                            break;
                        case R.id.nav_search_fan:

                            selectedFragment = new FanSearchFragment();
                            break;
                        case R.id.nav_favourite:
                            selectedFragment = new FanNotificationFragment();
                            break;
                        case R.id.nav_profile:
                            SharedPreferences.Editor editor = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
                            editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            editor.apply();
                            selectedFragment = new FanOwnProfileFragment();
                            break;
                    }

                    return true;
                }
            };

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