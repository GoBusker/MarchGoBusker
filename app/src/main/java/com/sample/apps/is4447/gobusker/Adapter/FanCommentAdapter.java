package com.sample.apps.is4447.gobusker.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.sample.apps.is4447.gobusker.Busker.BuskerFeed;
import com.sample.apps.is4447.gobusker.BuskerFragments.BuskerProfileFragment;
import com.sample.apps.is4447.gobusker.Fan.FanFeed;
import com.sample.apps.is4447.gobusker.FanFragments.FanProfileFragment;
import com.sample.apps.is4447.gobusker.Model.Busker;
import com.sample.apps.is4447.gobusker.Model.Comment;
import com.sample.apps.is4447.gobusker.Model.Fan;
import com.sample.apps.is4447.gobusker.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class FanCommentAdapter extends RecyclerView.Adapter<FanCommentAdapter.ViewHolder> {

    // I used this Youtube video as a reference for displaying comments under posts
    //    https://www.youtube.com/watch?v=V2lai8cJIkk&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=10&ab_channel=KODDev

    private Context mContext;
    private List<Comment> mComment;

    private FirebaseUser firebaseBusker;

    public void Busker(){

    }

    public FanCommentAdapter(Context mContext, List<Comment> mComment) {
        this.mContext = mContext;
        this.mComment = mComment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.comment_item_fan, viewGroup, false);
        return new FanCommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        firebaseBusker = FirebaseAuth.getInstance().getCurrentUser();
        Comment comment = mComment.get(i);

        viewHolder.comment.setText(comment.getComment());
      getBuskerInfo(viewHolder.image_profile, viewHolder.username, comment.getPublisher());
       getFanInfo(viewHolder.username, comment.getPublisher());

        viewHolder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
//                editor.putString("profileid", comment.getPublisher());
//                editor.apply();
//
//                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.layout.,
//                        new FanProfileFragment()).commit();


            }
        });
        viewHolder.image_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(mContext, FanFeed.class);
//                intent.putExtra("publisherid", comment.getPublisher());
//                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mComment.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView image_profile;
        public TextView username, comment;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            image_profile = itemView.findViewById(R.id.image_profile);
            username = itemView.findViewById(R.id.username);
            comment = itemView.findViewById(R.id.comment);

        }
    }
    private void getBuskerInfo(ImageView imageView, TextView username, String publisherid) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Buskers").child(publisherid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Busker busker = dataSnapshot.getValue(Busker.class);
                if (busker != null) {
                    username.setText(busker.getUsername());
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
        private void getFanInfo(TextView username, String publisherid){
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Fans").child(publisherid);

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Fan fan = dataSnapshot.getValue(Fan.class);
                    if (fan != null) {
                        username.setText(fan.getFirstname());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }

}

