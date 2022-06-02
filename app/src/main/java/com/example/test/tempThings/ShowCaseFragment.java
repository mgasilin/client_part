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


import com.example.test.R;
import com.example.test.Server.Server;
import com.example.test.model.Event;

public class ShowCaseFragment extends Fragment {

    private long id;
    private long userId;

    private TextView name;
    private TextView description;
    private TextView adress;


    public ShowCaseFragment() {
    }

    public void back(){
        destroy();
    }

    public void update(Event event) {
        if (event == null) {
            destroy();
        } else {
            name.setText(event.getName());
            description.setText(event.getDescription());
            adress.setText(event.getPlace());
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
        description = view.findViewById(R.id.show_description);
        adress = view.findViewById(R.id.show_adress);

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
        Server.getEventById((int) id, getActivity(),ShowCaseFragment.this);
        return view;
    }

    private void destroy() {
        getParentFragmentManager().beginTransaction().detach(this).commit();
    }

}