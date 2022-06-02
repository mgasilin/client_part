package com.example.test.hubActivityFragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import com.example.test.R;
import com.example.test.Server.Server;
import com.example.test.model.Event;
import com.example.test.model.adapters.UsualEventRvAdapter;
import com.example.test.model.adapters.listeners.CustomItemClickListener;
import com.example.test.tempThings.ShowCaseFragment;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {
    private long id;
private List<Event> events=new ArrayList<>();
    public SearchFragment() {
    }

    private UsualEventRvAdapter adapter;
    private SharedPreferences sharedPreferences;
    private ShowCaseFragment showCaseFragment;

    public void update(List<Event> events) {
        Log.d("SEARCH",events.toString());
        adapter.setEvents(events);
        this.events=events;
        adapter.notifyDataSetChanged();
    }

    public ArrayList<Boolean> load() {
        ArrayList<Boolean> res = new ArrayList<Boolean>();
        sharedPreferences = this.getActivity().getPreferences(0);
        res.add(sharedPreferences.getBoolean(id + " cr1", false));
        res.add(sharedPreferences.getBoolean(id + " cr2", false));
        res.add(sharedPreferences.getBoolean(id + " cr3", false));
        res.add(sharedPreferences.getBoolean(id + " cr4", false));
        res.add(sharedPreferences.getBoolean(id + " cr5", false));
        res.add(sharedPreferences.getBoolean(id + " cr6", false));
        res.add(sharedPreferences.getBoolean(id + " cr7", false));
        res.add(sharedPreferences.getBoolean(id + " cr8", false));
        return res;
    }

    public static SearchFragment newInstance(long id) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putLong("id", id);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sharedPreferences = this.getActivity().getPreferences(0);
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        AppCompatButton sBtn = view.findViewById(R.id.search_by_name);
        EditText name = view.findViewById(R.id.search_name);
        List<Event> myEvents = new ArrayList<>();
        RecyclerView recycler = view.findViewById(R.id.search_recycler);
        adapter = new UsualEventRvAdapter(myEvents, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Event event = events.get(position);
                showCaseFragment = ShowCaseFragment.newInstance(event.getId(), id);
                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.event_showcase_search, showCaseFragment)
                        .commit();
            }
        });
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(llm);
        recycler.setAdapter(adapter);
        Server.getByCategories(getActivity(), sharedPreferences, id, SearchFragment.this);
        sBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Server.searchByName(name.getText().toString(), getActivity(), SearchFragment.this);
            }
        });
        AppCompatButton backBtn = view.findViewById(R.id.search_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences = getActivity().getPreferences(0);
                Server.getByCategories(getActivity(), sharedPreferences, id, SearchFragment.this);
                name.setText("");
                //return to categories search
            }
        });
        return view;
    }
}