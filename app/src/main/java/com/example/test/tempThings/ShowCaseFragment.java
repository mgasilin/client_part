package com.example.test.tempThings;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.test.R;
import com.example.test.Server.Server;
import com.example.test.model.Event;

public class ShowCaseFragment extends Fragment {

    private long id;
    private long userId;

    private TextView date;
    private TextView name;
    private TextView adress;

    public static String beautifyEventDate(String date){
        String res = "";
        for (int i=0; i<8; i++){
            res=res+String.valueOf(date.charAt(i));
            if (i==1||i==3){
                res=res+".";
            }
        }
        return res;
    }

    public ShowCaseFragment() {
    }

    public void back() {
        Toast.makeText(getActivity(), "Похоже, данное мероприятие было удалено", Toast.LENGTH_SHORT).show();
        destroy();
    }

    public void update(Event event) {
        if (event == null||event.getName().isEmpty()) {
            destroy();
        } else {
            date.setText("Дата проведения: "+beautifyEventDate(event.getDate()));
            name.setText("Мероприятие: "+event.getName());
            adress.setText("Место проведения: "+event.getPlace());
        }
    }

    public static ShowCaseFragment newInstance(long id, long userId) {
        ShowCaseFragment fragment = new ShowCaseFragment();
        Bundle args = new Bundle();
        args.putLong("id", id);
        args.putLong("user", userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong("id");
            userId = getArguments().getLong("user");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_case, container, false);
        name = view.findViewById(R.id.show_name);
        adress = view.findViewById(R.id.show_adress);
        date=view.findViewById(R.id.show_date);

        AppCompatButton btn = view.findViewById(R.id.event_show);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), EventShowCaseActivity.class);
                i.putExtra("id", id);
                i.putExtra("user", userId);
                startActivity(i);
            }
        });

        AppCompatButton close = view.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                destroy();
            }
        });
        Server.getEventById((int) id, getActivity(), ShowCaseFragment.this);
        return view;
    }

    private void destroy() {
        getParentFragmentManager().beginTransaction().detach(this).commit();
    }

}