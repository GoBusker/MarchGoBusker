package com.sample.apps.is4447.gobusker.BuskerFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sample.apps.is4447.gobusker.Adapter.MyPhotoAdapter;
import com.sample.apps.is4447.gobusker.Busker.BuskerComments;
import com.sample.apps.is4447.gobusker.Busker.BuskerEditProfile;
import com.sample.apps.is4447.gobusker.Busker.BuskerPayment;
import com.sample.apps.is4447.gobusker.Busker.buskerForgot;
import com.sample.apps.is4447.gobusker.Busker.buskerLogin;
import com.sample.apps.is4447.gobusker.Model.Busker;
import com.sample.apps.is4447.gobusker.Model.Post;
import com.sample.apps.is4447.gobusker.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BuskerProfileFragment extends Fragment {

    ImageView options;
    CircleImageView image_profiled;
    TextView posts, followers, following, fullname, bio, username, facebook, instagram;
    Button edit_profile;

    TextView musician, dancer, rock, jazz, professional;

    private List<String> mySaves;



    RecyclerView recyclerView_saves;
    MyPhotoAdapter myPhotoAdapter_saves;
    List<Post> postList_saves;

    RecyclerView recyclerView;
    MyPhotoAdapter myPhotoAdapter;
    List<Post> postList;

    FirebaseUser firebaseBusker;
    String profileid;

    ImageButton my_photos, saved_photos, add_payment;

    private String image;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_busker_profile, container, false);

        firebaseBusker = FirebaseAuth.getInstance().getCurrentUser();

        SharedPreferences prefs = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        profileid = prefs.getString("profileid", "none");

        image_profiled = view.findViewById(R.id.image_profile);
        options = view.findViewById(R.id.options);
        posts = view.findViewById(R.id.posts);
        followers = view.findViewById(R.id.followers);
        following = view.findViewById(R.id.following);
        fullname = view.findViewById(R.id.fullname);
        bio = view.findViewById(R.id.bio);
        username = view.findViewById(R.id.username);
        edit_profile = view.findViewById(R.id.edit_profile);
        my_photos = view.findViewById(R.id.my_photos);
        saved_photos = view.findViewById(R.id.saved_photos);

        facebook = view.findViewById(R.id.facebook);
        instagram = view.findViewById(R.id.instagram);

        musician = view.findViewById(R.id.musician);
        rock = view.findViewById(R.id.rock);
        jazz = view.findViewById(R.id.jazz);
        dancer = view.findViewById(R.id.dancer);
        professional = view.findViewById(R.id.professional);

        add_payment = view.findViewById(R.id.add_payment);

        //I adapted this Youtube video to have all busker posts visible on their profile
        // https://www.youtube.com/watch?v=4HKEApz-XOM&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=14&ab_channel=KODDev
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(linearLayoutManager);
        postList = new ArrayList<>();
        myPhotoAdapter = new MyPhotoAdapter(getContext(), postList);
        recyclerView.setAdapter(myPhotoAdapter);

        //I adapted this Youtube video to add saving post functionality
        //https://www.youtube.com/watch?v=uloDNWsM__g&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=15&ab_channel=KODDev
        recyclerView_saves = view.findViewById(R.id.recycler_view_save);
        recyclerView_saves.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager_saves = new GridLayoutManager(getContext(), 3);
        recyclerView_saves.setLayoutManager(linearLayoutManager_saves);
        postList_saves = new ArrayList<>();
        myPhotoAdapter_saves = new MyPhotoAdapter(getContext(), postList_saves);
        recyclerView_saves.setAdapter(myPhotoAdapter_saves);

        recyclerView.setVisibility(View.VISIBLE);
        recyclerView_saves.setVisibility(View.GONE);

        buskerInfo();
        getFollowers();
        getNrPosts();
        myPhotos();
        mysaves();

        if(profileid.equals(firebaseBusker.getUid())){
            edit_profile.setText("Edit Profile");
        } else {
            checkFollow();
            saved_photos.setVisibility(View.GONE);
        }




        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String btn = edit_profile.getText().toString();

                if(btn.equals("Edit Profile")){
                   startActivity(new Intent(getContext(), BuskerEditProfile.class));
                } else if (btn.equals("follow")){
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseBusker.getUid())
                            .child("following").child(profileid).setValue(true);
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(profileid)
                            .child("followers").child(firebaseBusker.getUid()).setValue(true);

                    addNotifications();

                } else if (btn.equals("following")){
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseBusker.getUid())
                            .child("following").child(profileid).removeValue();
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(profileid)
                            .child("followers").child(firebaseBusker.getUid()).removeValue();
                }

            }
        });
        my_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView_saves.setVisibility(View.GONE);
            }
        });
        saved_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.GONE);
                recyclerView_saves.setVisibility(View.VISIBLE);
            }
        });

        add_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), BuskerPayment.class));
            }
        });



        return view;
    }
    private void addNotifications(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child(profileid);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userid", firebaseBusker.getUid());
        hashMap.put("text", " started following you");
        hashMap.put("postid", "");
        hashMap.put("ispost", false);

        reference.push().setValue(hashMap);
    }

    private void buskerInfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Buskers").child(profileid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(getContext() == null){
                    return;
                }
                Busker busker = dataSnapshot.getValue(Busker.class);

                username.setText(busker.getUsername());
                fullname.setText(busker.getFirstname());
                bio.setText(busker.getBio());
                facebook.setText(busker.getFacebook());
                instagram.setText(busker.getInstagram());
                if (busker.isMusician() == true){
                    musician.setVisibility(View.VISIBLE);
                } else if (busker.isMusician() == false){
                    musician.setVisibility(View.GONE);
                }
                if (busker.isRock() == true){
                    rock.setVisibility(View.VISIBLE);
                } else if (busker.isRock() == false){
                    rock.setVisibility(View.GONE);
                }
                if (busker.isJazz() == true){
                    jazz.setVisibility(View.VISIBLE);
                } else if (busker.isJazz() == false){
                    jazz.setVisibility(View.GONE);
                }
                if (busker.isDancer() == true){
                    dancer.setVisibility(View.VISIBLE);
                } else if (busker.isDancer() == false){
                    dancer.setVisibility(View.GONE);
                }
                if (busker.isProfessional() == true){
                    professional.setVisibility(View.VISIBLE);
                } else if (busker.isProfessional() == false){
                    professional.setVisibility(View.GONE);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    Map<String, Object> map = (Map<String, Object>) snapshot.getValue();

                    Glide.clear(image_profiled);
                    if (map.get("imageurl") != null) {
                        image = map.get("imageurl").toString();
                        Glide.with(getActivity()).load(image).into(image_profiled);
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }



    private void checkFollow(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Follow").child(firebaseBusker.getUid()).child("following");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(profileid).exists()){
                    edit_profile.setText("following");
                } else {
                    edit_profile.setText("follow");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getFollowers(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Follow").child(profileid).child("followers");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                followers.setText(""+dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference()
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
    }
    private void getNrPosts(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Post post = snapshot.getValue(Post.class);
                    if (post.getPublisher().equals(profileid)){
                        i++;
                    }
                }
                posts.setText(""+i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //I adapted this Youtube video to have all busker posts visible on their profile
    // https://www.youtube.com/watch?v=4HKEApz-XOM&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=14&ab_channel=KODDev
    private void myPhotos(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Post post = snapshot.getValue(Post.class);
                    if (post.getPublisher().equals(profileid)){
                        postList.add(post);
                    }
                }
                Collections.reverse(postList);
                myPhotoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    //I adapted this Youtube video to add saving post functionality
    //https://www.youtube.com/watch?v=uloDNWsM__g&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=15&ab_channel=KODDev
    private void mysaves(){
        mySaves = new ArrayList<>();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Saves")
                    .child(firebaseBusker.getUid());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for ( DataSnapshot snapshot : dataSnapshot.getChildren()){
                        mySaves.add(snapshot.getKey());
                    }

                    readSaves();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        private void readSaves(){
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    postList_saves.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Post post = snapshot.getValue(Post.class);

                        for (String id : mySaves){
                            if (post.getPostid().equals(id)){
                                postList_saves.add(post);
                            }
                        }
                    }
                    myPhotoAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
