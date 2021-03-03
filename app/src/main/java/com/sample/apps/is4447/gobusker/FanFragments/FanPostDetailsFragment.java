package com.sample.apps.is4447.gobusker.FanFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sample.apps.is4447.gobusker.Adapter.FanPostAdapter;
import com.sample.apps.is4447.gobusker.Adapter.PostAdapter;
import com.sample.apps.is4447.gobusker.Model.Post;
import com.sample.apps.is4447.gobusker.R;

import java.util.ArrayList;
import java.util.List;


public class FanPostDetailsFragment extends Fragment {
    //    I adapted this youtube video to add post details
    //     https://www.youtube.com/watch?v=CGIgC3l4Bz0&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=16&ab_channel=KODDev

    String postid;
    private RecyclerView recyclerView;
    private FanPostAdapter postAdapter;
    private List<Post> postList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fan_post_details, container, false);


        SharedPreferences preferences = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        postid = preferences.getString("postid", "none");

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        postList = new ArrayList<>();
        postAdapter = new FanPostAdapter(getContext(), postList);
        recyclerView.setAdapter(postAdapter);

        readPost();

        return view;
    }

    private void readPost(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts").child(postid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                Post post = dataSnapshot.getValue(Post.class);
                postList.add(post);

                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    }
