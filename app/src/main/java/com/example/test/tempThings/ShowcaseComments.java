package com.example.test.tempThings;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.R;
import com.example.test.Server.Server;
import com.example.test.model.Comment;
import com.example.test.model.Event;
import com.example.test.model.adapters.CommentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowcaseComments#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowcaseComments extends Fragment {
    private long id;
    private long user;
    private int size = 0;
    private Event shEvent;
    private CommentAdapter adapter;
    private EditText comment;
    private AppCompatButton send;
    private RecyclerView commentRecycler;
    private AppCompatButton update;


    public void update(List<Comment> comments) {
        if (size > comments.size()) {
            if (shEvent != null)
                Server.findByEvent(shEvent, getActivity());
        }
        if (adapter != null) {
            size = comments.size();
            adapter.setComments(comments);
            adapter.notifyDataSetChanged();
        }
    }

    public void update(Event event) {
        shEvent = event;
    }

    public ShowcaseComments() {
    }

    public static ShowcaseComments newInstance(long id, long user) {
        ShowcaseComments fragment = new ShowcaseComments();
        Bundle args = new Bundle();
        args.putLong("id", id);
        args.putLong("user", user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong("id");
            user = getArguments().getLong("user");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_showcase_comments, container, false);
        comment = view.findViewById(R.id.new_comment);
        send = view.findViewById(R.id.send_new_comment);
        List<Comment> comments = new ArrayList<>();
        commentRecycler = view.findViewById(R.id.showcase_comments);
        adapter = new CommentAdapter(comments, getActivity());
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        commentRecycler.setLayoutManager(llm);
        commentRecycler.setAdapter(adapter);
        update = view.findViewById(R.id.update_recycler);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = comment.getText().toString();
                comment.setText("");
                if (text.isEmpty()) {
                    Toast.makeText(getActivity(), "Не введет текст комментария", Toast.LENGTH_LONG).show();
                } else {
                    Comment comment1 = new Comment(shEvent.getId(), user, text);
                    size++;
                    Server.insertComment(comment1, getActivity());
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Server.findByEvent(shEvent, getActivity());
            }
        });
        Server.findByEvent(shEvent,getActivity());
        return view;
    }
}