package com.sample.apps.is4447.gobusker.FanFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sample.apps.is4447.gobusker.Fan.fanLogin;
import com.sample.apps.is4447.gobusker.Model.Busker;
import com.sample.apps.is4447.gobusker.Model.Fan;
import com.sample.apps.is4447.gobusker.R;
public class FanOwnProfileFragment extends Fragment {

    TextView username, following, name, email;

    FirebaseUser firebaseBusker;

    String profileid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fan_own_profile, container, false);

        SharedPreferences prefs = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        profileid = prefs.getString("profileid", "none");

        firebaseBusker = FirebaseAuth.getInstance().getCurrentUser();

        username = view.findViewById(R.id.fan_username);
        following = view.findViewById(R.id.following);
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);


        getData();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Follow").child(profileid).child("following");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                following.setText(""+dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return view;
    }


    private void getData(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Fans").child(profileid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(getContext() == null){
                    return;
                }
                Fan fan = snapshot.getValue(Fan.class);

                //   Glide.with(getContext()).load(busker.getImageUrl()).into(image_profile);
                username.setText(fan.getUsername());
                name.setText(fan.getFirstname());
                email.setText(fan.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}