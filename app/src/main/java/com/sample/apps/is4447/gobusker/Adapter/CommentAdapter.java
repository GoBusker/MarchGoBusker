package com.sample.apps.is4447.gobusker.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.sample.apps.is4447.gobusker.Model.Comment;
import com.sample.apps.is4447.gobusker.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private Context mContext;
    private List<Comment> mComment;

    private FirebaseUser firebaseBusker;

    public CommentAdapter(Context mContext, List<Comment> mComment) {
        this.mContext = mContext;
        this.mComment = mComment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.comment_item, viewGroup, false);
        return new CommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
        private void getBuskerInfo(ImageView imageView, TextView username, String publisherid){

        }
    }

