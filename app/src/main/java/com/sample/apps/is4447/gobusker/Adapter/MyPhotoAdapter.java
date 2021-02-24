package com.sample.apps.is4447.gobusker.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sample.apps.is4447.gobusker.Fragment.BuskerPostDetailsFragment;
import com.sample.apps.is4447.gobusker.Model.Post;
import com.sample.apps.is4447.gobusker.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class MyPhotoAdapter extends RecyclerView.Adapter<MyPhotoAdapter.ViewHolder>{

    //I adapted this Youtube video to have all busker posts visible on their profile
    // https://www.youtube.com/watch?v=4HKEApz-XOM&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=14&ab_channel=KODDev

    private Context context;
    private List<Post> mPosts;

    public MyPhotoAdapter(Context context, List<Post> mPosts) {
        this.context = context;
        this.mPosts = mPosts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.photos_item, viewGroup, false);
        return new MyPhotoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Post post = mPosts.get(i);

        Glide.with(context).load(post.getPostimage()).into(viewHolder.post_image);

        viewHolder.post_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
                editor.putString("postid", post.getPostid());
                editor.apply();

                ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new BuskerPostDetailsFragment()).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView post_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            post_image = itemView.findViewById(R.id.post_image);
        }
    }
}
