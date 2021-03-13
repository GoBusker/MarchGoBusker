package com.sample.apps.is4447.gobusker.Adapter;

import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sample.apps.is4447.gobusker.BuskerFragments.BuskerPostDetailsFragment;
import com.sample.apps.is4447.gobusker.BuskerFragments.BuskerProfileFragment;
import com.sample.apps.is4447.gobusker.Model.Busker;
import com.sample.apps.is4447.gobusker.Model.BuskerNotifications;
import com.sample.apps.is4447.gobusker.Model.Fan;
import com.sample.apps.is4447.gobusker.Model.Post;
import com.sample.apps.is4447.gobusker.R;

import org.w3c.dom.Text;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class BuskerNotificationAdapter extends RecyclerView.Adapter<BuskerNotificationAdapter.viewHolder> {

    private Context mContext;
    private List<BuskerNotifications> mNotifications;

    public BuskerNotificationAdapter(Context mContext, List<BuskerNotifications> mNotifications) {
        this.mContext = mContext;
        this.mNotifications = mNotifications;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.buskernotifications_item, parent, false);
        return new BuskerNotificationAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int i) {
        BuskerNotifications notification = mNotifications.get(i);

        holder.text.setText(notification.getText());

        getBuskerInfo(holder.image_profile, holder.username, notification.getUserid());
        getFanInfo(holder.username, notification.getUserid());


        if (notification.isIspost()) {
            holder.post_image.setVisibility(View.VISIBLE);
            getPostImage(holder.post_image, notification.getPostid());
        } else {
            holder.post_image.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notification.isIspost()) {
                    SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
                    editor.putString("postid", notification.getPostid());
                    editor.apply();

                    ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new BuskerPostDetailsFragment()).commit();
                }   else {
                    SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
                    editor.putString("postid", notification.getUserid());
                    editor.apply();

                    ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new BuskerProfileFragment()).commit();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mNotifications.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        public ImageView image_profile, post_image;
        public TextView username, text;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            image_profile = itemView.findViewById(R.id.image_profile);
            post_image = itemView.findViewById(R.id.post_image);
            username = itemView.findViewById(R.id.username);
            text = itemView.findViewById(R.id.text);
        }
    }

        private void getBuskerInfo(ImageView imageView, TextView username, String publisherid) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Buskers").child(publisherid);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Busker busker = snapshot.getValue(Busker.class);
                    if (busker != null) {
                        Glide.with(mContext).load(busker.getImageUrl()).into(imageView);
                        username.setText(busker.getUsername());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    private void getFanInfo(TextView username, String publisherid) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Fans").child(publisherid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Fan fan = snapshot.getValue(Fan.class);
                if (fan != null) {
                    username.setText(fan.getUsername());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
        private void getPostImage(ImageView imageView, final String postid){
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts").child(postid);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Post post = snapshot.getValue(Post.class);
                    Glide.with(mContext).load(post.getPostimage()).into(imageView);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

