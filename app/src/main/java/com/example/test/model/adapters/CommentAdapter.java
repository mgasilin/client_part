package com.example.test.model.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.model.Comment;
import com.example.test.model.Event;
import com.example.test.model.adapters.listeners.CustomItemClickListener;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{
    private Context context;
    public static class CommentViewHolder extends RecyclerView.ViewHolder {

        TextView commentAuthor;
        TextView commentText;

        CommentViewHolder(View itemView) {
            super(itemView);
            commentAuthor = (TextView) itemView.findViewById(R.id.author);
            commentText = (TextView) itemView.findViewById(R.id.comment_text);
        }
    }

    List<Comment> comments;

    public CommentAdapter(List<Comment> comments, Context context) {
        this.context = context;
        this.comments=comments;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_comment, viewGroup, false);
        final CommentAdapter.CommentViewHolder commentViewHolder = new CommentAdapter.CommentViewHolder(v);
        return commentViewHolder;
    }

    @Override
    public void onBindViewHolder(CommentAdapter.CommentViewHolder commentViewHolder, int i) {
        commentViewHolder.commentAuthor.setText(comments.get(i).getAuthorName());
        commentViewHolder.commentText.setText(comments.get(i).getMessage());
    }

    public Comment getComment(int pos){return  comments.get(pos);}

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
