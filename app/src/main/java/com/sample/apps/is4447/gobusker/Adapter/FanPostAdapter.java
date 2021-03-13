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
import com.sample.apps.is4447.gobusker.Busker.BuskerComments;
import com.sample.apps.is4447.gobusker.BuskerFragments.BuskerPostDetailsFragment;
import com.sample.apps.is4447.gobusker.BuskerFragments.BuskerProfileFragment;
import com.sample.apps.is4447.gobusker.Fan.FanComments;
import com.sample.apps.is4447.gobusker.FanFragments.FanPostDetailsFragment;
import com.sample.apps.is4447.gobusker.FanFragments.FanProfileFragment;
import com.sample.apps.is4447.gobusker.Model.Busker;
import com.sample.apps.is4447.gobusker.Model.Post;
import com.sample.apps.is4447.gobusker.R;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

//I used this video for reference for sending comments onto posts
//    https://www.youtube.com/watch?v=wemyU3GdS8A&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=9&ab_channel=KODDev


//https://www.youtube.com/watch?v=mk2CrU-awkM&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=7&ab_channel=KODDev
//I used this Youtube video for reference for displaying posts
public class FanPostAdapter extends RecyclerView.Adapter<FanPostAdapter.ViewHolder> {

    public Context mContext;
    public List<Post> mPost;

    private FirebaseUser firebaseBusker;

    public FanPostAdapter(Context mContext, List<Post> mPost) {
        this.mContext = mContext;
        this.mPost = mPost;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fan_post_item, viewGroup, false);
        return new FanPostAdapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        firebaseBusker = FirebaseAuth.getInstance().getCurrentUser();
        Post post = mPost.get(i);

        Glide.with(mContext).load(post.getPostimage()).into(viewHolder.post_image);

        if (post.getDescription().equals("")){
            viewHolder.description.setVisibility(View.GONE);
        } else {
            viewHolder.description.setVisibility(View.VISIBLE);
            viewHolder.description.setText(post.getDescription());
        }
        publisherInfo(viewHolder.image_profile, viewHolder.username, viewHolder.publisher,post.getPublisher());
        isLikes(post.getPostid(), viewHolder.like);
        nrLikes(viewHolder.likes, post.getPostid());
        getComments(post.getPostid(), viewHolder.comments);
        //isSaved(post.getPostid(), viewHolder.save);

        viewHolder.image_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
                editor.putString("profileid", post.getPublisher());
                editor.apply();

                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_fan,
                        new FanProfileFragment()).commit();
            }
        });

        viewHolder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
                editor.putString("profileid", post.getPublisher());
                editor.apply();

                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_fan,
                        new FanProfileFragment()).commit();
            }
        });
        viewHolder.publisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
                editor.putString("profileid", post.getPublisher());
                editor.apply();

                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new BuskerProfileFragment()).commit();
            }
        });

//    I adapted this youtube video to add post details
//     https://www.youtube.com/watch?v=CGIgC3l4Bz0&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=16&ab_channel=KODDev
        viewHolder.post_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
                editor.putString("postid", post.getPostid());
                editor.apply();

                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_fan,
                        new FanPostDetailsFragment()).commit();
            }
        });




//        viewHolder.save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (viewHolder.save.getTag().equals("save")) {
//                    FirebaseDatabase.getInstance().getReference().child("Saves").child(firebaseBusker.getUid())
//                            .child(post.getPostid()).setValue(true);
//                } else {
//                    FirebaseDatabase.getInstance().getReference().child("Saves").child(firebaseBusker.getUid())
//                            .child(post.getPostid()).removeValue();
//                }
//            }
//        });

        //  https://www.youtube.com/watch?v=B1NiPvfMbDM&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=8&ab_channel=KODDev
        //I used this video for reference to adding likes to posts
        viewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHolder.like.getTag().equals("like")){
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(post.getPostid())
                            .child(firebaseBusker.getUid()).setValue(true);
                    addNotifications(post.getPublisher(), post.getPostid());
                } else{
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(post.getPostid())
                            .child(firebaseBusker.getUid()).removeValue();
                }
            }
        });
//I used this video for reference for sending comments onto posts
//    https://www.youtube.com/watch?v=wemyU3GdS8A&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=9&ab_channel=KODDev
        viewHolder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, FanComments.class);
                intent.putExtra("postid", post.getPostid());
                intent.putExtra("publisherid", post.getPublisher());
                mContext.startActivity(intent);
            }
        });
        viewHolder.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, FanComments.class);
                intent.putExtra("postid", post.getPostid());
                intent.putExtra("publisherid", post.getPublisher());
                mContext.startActivity(intent);
            }
        });
    }

    //https://www.youtube.com/watch?v=mk2CrU-awkM&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=7&ab_channel=KODDev
//I used this Youtube video for reference for displaying posts
    @Override
    public int getItemCount() {
        return mPost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image_profile, post_image, like, comment, save;
        public TextView username, likes, publisher, description, comments;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image_profile = itemView.findViewById(R.id.image_profile);
            post_image = itemView.findViewById(R.id.post_image);
            comments = itemView.findViewById(R.id.comments);
            like = itemView.findViewById(R.id.like);
            comment = itemView.findViewById(R.id.comment);
            save = itemView.findViewById(R.id.save);
            username = itemView.findViewById(R.id.username);
            likes = itemView.findViewById(R.id.likes);
            publisher = itemView.findViewById(R.id.publisher);
            description = itemView.findViewById(R.id.description);
        }

    }
    //I used this video for reference for sending comments onto posts
//    https://www.youtube.com/watch?v=wemyU3GdS8A&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=9&ab_channel=KODDev
    private void getComments(String postid, TextView comments){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comments").child(postid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                comments.setText("View All " + dataSnapshot.getChildrenCount() + "  Comments");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void addNotifications(String userid, String postid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child(userid);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userid", firebaseBusker.getUid());
        hashMap.put("text", " will be at your busk");
        hashMap.put("postid", postid);
        hashMap.put("ispost", true);

        reference.push().setValue(hashMap);
    }

    //  https://www.youtube.com/watch?v=B1NiPvfMbDM&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=8&ab_channel=KODDev
    //I used this video for reference to adding likes to posts
    private void isLikes(String postid, ImageView imageView){

        final FirebaseUser firebaseBusker = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Likes")
                .child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(firebaseBusker.getUid()).exists()){
                    imageView.setImageResource(R.drawable.ic_people_filled);
                    imageView.setTag("liked");
                } else{
                    imageView.setImageResource(R.drawable.ic_people);
                    imageView.setTag("like");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //  https://www.youtube.com/watch?v=B1NiPvfMbDM&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=8&ab_channel=KODDev
    //I used this video for reference to adding likes to posts
    private void nrLikes(final TextView likes, String postid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Likes")
                .child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                likes.setText(dataSnapshot.getChildrenCount()+" attendees");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //https://www.youtube.com/watch?v=mk2CrU-awkM&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=7&ab_channel=KODDev
//I used this Youtube video for reference for displaying posts
    private void publisherInfo(final ImageView image_profile,final TextView username, final TextView publisher, String userId) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Buskers").child(userId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Busker busker = dataSnapshot.getValue(Busker.class);
                Glide.with(mContext).load(busker.getImageUrl()).into(image_profile);
                username.setText(busker.getUsername());
                publisher.setText(busker.getUsername());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //I adapted this Youtube video to add saving post functionality
    //https://www.youtube.com/watch?v=uloDNWsM__g&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=15&ab_channel=KODDev
//    private void isSaved(String postid, ImageView imageView){
//        FirebaseUser firebaseBusker = FirebaseAuth.getInstance().getCurrentUser();
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Saves")
//                .child(firebaseBusker.getUid());
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.child(postid).exists()){
//                    imageView.setImageResource(R.drawable.ic_save_black);
//                    imageView.setTag("saved");
//                } else {
//                    imageView.setImageResource(R.drawable.ic_save_white);
//                    imageView.setTag("save");
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

}
