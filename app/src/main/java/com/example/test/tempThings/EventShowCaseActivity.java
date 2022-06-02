package com.example.test.tempThings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.HubActivity;
import com.example.test.R;
import com.example.test.Server.Server;
import com.example.test.model.Comment;
import com.example.test.model.adapters.CommentAdapter;
import com.example.test.model.Event;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class EventShowCaseActivity extends AppCompatActivity {

    private long id;
    private long user;
    private Event shEvent;
    private CommentAdapter adapter;
    private EditText comment;
    private AppCompatButton send;
    private TextView name;
    private TextView description;
    private int size=0;
    private TextView place;
    private TextView cats;
    private TextView date;
    private RecyclerView commentRecycler;
    private AppCompatButton update;

    public void back(){
        Intent i = new Intent(EventShowCaseActivity.this, HubActivity.class);
        i.putExtra("id",user);
        startActivity(i);
    }

    public void update(Event event) {
        shEvent=event;
        if (event.getName()==null||event.getName().isEmpty()){
            back();
        }
        ArrayList<Boolean> categories = event.getCategories();
        String categoriesForShow = "";
        if (categories.get(0)) {
            categoriesForShow += "Уличное мероприятие. ";
        }
        if (categories.get(1)) {
            categoriesForShow += "Групповое мероприятие. ";
        }
        if (categories.get(2)) {
            categoriesForShow += "Мероприятие для всей семьи. ";
        }
        if (categories.get(3)) {
            categoriesForShow += "Бесплатное мероприятие.";
        }
        if (categories.get(4)) {
            categoriesForShow += "Мероприятие с ковидными ограничениями. ";
        }
        if (categories.get(5)) {
            categoriesForShow += "Мероприятие с предварительной регистрацией. ";
        }
        if (categories.get(6)) {
            categoriesForShow += "Спротивное мероприятие. ";
        }
        if (categories.get(7)) {
            categoriesForShow += "Мероприятие с возрастными ограничениями. ";
        }
        name.setText(event.getName());
        description.setText(event.getDescription());
        place.setText(event.getPlace());
        if (categoriesForShow.isEmpty()){
            categoriesForShow="Данное мероприятие не принадлежит ни одной из категорий.";
        }
        cats.setText(categoriesForShow);
        date.setText(event.getDate());
        Server.findByEvent(event, EventShowCaseActivity.this);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = comment.getText().toString();
                comment.setText("");
                if (text.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Не введет текст комментария", Toast.LENGTH_LONG).show();
                } else {
                    Comment comment1 = new Comment(event.getId(), user, text);
                    size++;
                    Server.insertComment(comment1, EventShowCaseActivity.this);
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Server.findByEvent(event, EventShowCaseActivity.this);
            }
        });
    }

    public void update(List<Comment> comments){
        if (comments.size()<size){
            Server.getEventById((int) id, EventShowCaseActivity.this);
        }
        size=comments.size();
        adapter.setComments(comments);
        adapter.notifyDataSetChanged();
    }

    public void update() {
        Server.findByEvent(shEvent,EventShowCaseActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        comment = findViewById(R.id.comment);
        setContentView(R.layout.activity_event_show_case);
        id=getIntent().getExtras().getLong("id");
        user= getIntent().getExtras().getLong("user");
        send = findViewById(R.id.send_comment);
        name = findViewById(R.id.event_name);
        description = findViewById(R.id.event_description);
        place = findViewById(R.id.event_place);
        cats = findViewById(R.id.event_categories);
        date = findViewById(R.id.event_date);
        comment = findViewById(R.id.comment);
        send = findViewById(R.id.send_comment);
        ShowCaseMap map = ShowCaseMap.newInstance(id);
        getSupportFragmentManager().beginTransaction().replace(R.id.map_place, map).commit();
        List<Comment> comments = new ArrayList<>();
        commentRecycler = findViewById(R.id.comment_recycler);
        adapter = new CommentAdapter(comments, getApplicationContext());
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        commentRecycler.setLayoutManager(llm);
        commentRecycler.setAdapter(adapter);
        update = findViewById(R.id.update_comment);
        AppCompatButton back = findViewById(R.id.backShBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EventShowCaseActivity.this, HubActivity.class);
                i.putExtra("id", user);
                startActivity(i);
            }
        });
        Server.getEventById((int) id, EventShowCaseActivity.this);

    }
}