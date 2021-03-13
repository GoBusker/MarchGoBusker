package com.sample.apps.is4447.gobusker.FanFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sample.apps.is4447.gobusker.Fan.fanHomePage;
import com.sample.apps.is4447.gobusker.MainActivity;
import com.sample.apps.is4447.gobusker.Model.Fan;
import com.sample.apps.is4447.gobusker.R;
public class FanOwnProfileFragment extends Fragment {

    TextView username, following, name, email;

    FirebaseUser firebaseBusker;

    String profileid;

    Button logout, delete;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fan_own_profile, container, false);

        SharedPreferences prefs = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        profileid = prefs.getString("profileid", "none");

        firebaseBusker = FirebaseAuth.getInstance().getCurrentUser();

        username = view.findViewById(R.id.fan_usernam);
        following = view.findViewById(R.id.following);
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);

        logout = view.findViewById(R.id.btnfanLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            //Code acquired and modified from this youtube video for firebase logout functionality
            //https://www.youtube.com/watch?v=DRBLazxi6Eg&ab_channel=CodeWithMazn
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

//        delete = view.findViewById(R.id.btnDeleteFan);
//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                firebaseBusker.delete();
//            }
//        });


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