package com.example.test.tempThings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.test.HubActivity;
import com.example.test.R;
import com.example.test.Server.Server;
import com.example.test.model.Comment;
import com.example.test.model.Event;

import java.util.List;

public class EventShowCaseActivity extends AppCompatActivity {

    private long id;
    private long user;
    private int size =0;
    private Event shEvent;
    private ShowcaseInfo showcaseInfo;
    private ShowcaseComments showcaseComments;
    private int current = 1;

    public void update(Event event){
        if (event==null||event.getName().isEmpty()){
            back();
        }
        shEvent=event;
        showcaseComments.update(event);
        showcaseInfo.update(event);
        Server.findByEvent(event,EventShowCaseActivity.this);
    }

    public void update() {
        Server.findByEvent(shEvent,EventShowCaseActivity.this);
    }

    public void update(List<Comment> comments){
        showcaseComments.update(comments);
    }


    public void back() {
        Toast.makeText(getApplicationContext(), "Похоже, данное мероприятие было удалено", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(EventShowCaseActivity.this, HubActivity.class);
        i.putExtra("id", user);
        startActivity(i);
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_showcase);
        id=getIntent().getExtras().getLong("id");
        user= getIntent().getExtras().getLong("user");
        showcaseInfo = ShowcaseInfo.newInstance(id,user);
        showcaseComments = ShowcaseComments.newInstance(id,user);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_showcase,showcaseInfo).commit();
        AppCompatButton back = findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EventShowCaseActivity.this, HubActivity.class);
                i.putExtra("id", user);
                startActivity(i);
            }
        });
        Server.getEventById((int) id, EventShowCaseActivity.this);
        TextView info = findViewById(R.id.info_showcase);
        TextView comments = findViewById(R.id.comments_showcase);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                info.setBackground(getDrawable(R.drawable.ic_1));
                comments.setBackground(getDrawable(R.drawable.ic_2));
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_showcase,showcaseInfo).commit();
            }
        });
        comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                info.setBackground(getDrawable(R.drawable.ic_2));
                comments.setBackground(getDrawable(R.drawable.ic_1));
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_showcase,showcaseComments).commit();
            }
        });

    }
}
