package com.sample.apps.is4447.gobusker.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.sample.apps.is4447.gobusker.BuskerFragments.BuskerOtherProfileFragment;
import com.sample.apps.is4447.gobusker.BuskerFragments.BuskerProfileFragment;
import com.sample.apps.is4447.gobusker.Model.Busker;
import com.sample.apps.is4447.gobusker.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;


public class BuskerAdapter extends RecyclerView.Adapter<BuskerAdapter.ViewHolder> {

    private Context nContext;
    private List<Busker> mBuskers;

    private String image;


    private FirebaseUser firebaseBusker;

    public BuskerAdapter(Context nContext, List<Busker> mBuskers) {
        this.nContext = nContext;
        this.mBuskers = mBuskers;
    }
 // I referenced this Youtube video for busker search and follow
   // https://www.youtube.com/watch?v=59ibixMg4ck&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=4&ab_channel=KODDev
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(nContext).inflate(R.layout.busker_item, viewGroup, false);
        return new BuskerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        firebaseBusker = FirebaseAuth.getInstance().getCurrentUser();

        final Busker busker = mBuskers.get(i);

        viewHolder.btn_follow.setVisibility(View.VISIBLE);

        viewHolder.username.setText(busker.getUsername());
        viewHolder.fullname.setText(busker.getFirstname());
       Glide.with(nContext).load(busker.getImageUrl()).into(viewHolder.image_profile);



        isFollowing(busker.getId(), viewHolder.btn_follow);




        if(busker.getId().equals(firebaseBusker.getUid())){
            viewHolder.btn_follow.setVisibility(View.GONE);
        }



        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = nContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
                editor.putString("profileid", busker.getId());
                editor.apply();

                ((FragmentActivity)nContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new BuskerOtherProfileFragment()).commit();
            }
        });
        // <!-- I referenced this Youtube video for busker search and follow
        //    https://www.youtube.com/watch?v=59ibixMg4ck&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=4&ab_channel=KODDev -->
viewHolder.btn_follow.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if (viewHolder.btn_follow.getText().toString().equals("follow")){
            FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseBusker.getUid())
                    .child("following").child(busker.getId()).setValue(true);
            FirebaseDatabase.getInstance().getReference().child("Follow").child(busker.getId())
                    .child("followers").child(firebaseBusker.getUid()).setValue(true);

            addNotifications(busker.getId());
        } else {
            FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseBusker.getUid())
                    .child("following").child(busker.getId()).removeValue();
            FirebaseDatabase.getInstance().getReference().child("Follow").child(busker.getId())
                    .child("followers").child(firebaseBusker.getUid()).removeValue();
        }
    }
});


    }

    private void addNotifications(String userid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child(userid);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userid", firebaseBusker.getUid());
        hashMap.put("text", " started following you");
        hashMap.put("postid", "");
        hashMap.put("ispost", true);

        reference.push().setValue(hashMap);
    }
    // <!-- I referenced this Youtube video for busker search and follow
    //    https://www.youtube.com/watch?v=59ibixMg4ck&lifnoptst=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=4&ab_channel=KODDev -->
    @Override
    public int getItemCount() {
        return mBuskers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public TextView fullname;
        public ImageView image_profile;
        public Button btn_follow;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            fullname = itemView.findViewById(R.id.fullname);
            image_profile = itemView.findViewById(R.id.image_profile);
            btn_follow = itemView.findViewById(R.id.btn_follow);
        }
    }
    // <!-- I referenced this Youtube video for busker search and follow
    //    https://www.youtube.com/watch?v=59ibixMg4ck&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=4&ab_channel=KODDev -->
    private void isFollowing(String userId, Button button){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Follow").child(firebaseBusker.getUid()).child("following");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(userId).exists()){
                    button.setText("following");
                } else {
                    button.setText("follow");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
