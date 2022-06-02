package com.example.test.hubActivityFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.test.R;
import com.example.test.Server.Server;
import com.example.test.model.Event;
import com.example.test.model.adapters.UsualEventRvAdapter;
import com.example.test.model.adapters.listeners.CustomItemClickListener;
import com.example.test.tempThings.NewEventActivity;
import com.example.test.tempThings.ShowCaseFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    public static final String ID = "id";
    private ShowCaseFragment showCaseFragment;
    private RecyclerView recycler;
    private List<Event> eventList=new ArrayList<>();
    private long userId;
    private UsualEventRvAdapter adapter;

    public HomeFragment() {
    }

    public void update(List<Event> events){
        eventList=events;
        adapter.setEvents(events);
        adapter.notifyDataSetChanged();
    }

    public static HomeFragment newInstance(long id) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putLong(ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getLong(ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Server.getByUserId((int)userId,getActivity(),HomeFragment.this);
        List<Event> myEvents=new ArrayList<>();
        eventList=myEvents;
        recycler = view.findViewById(R.id.home_recycler);
        adapter =new UsualEventRvAdapter(eventList, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Event event =eventList.get(position);
                showCaseFragment = ShowCaseFragment.newInstance(event.getId(),userId);
                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.event_showcase_home, showCaseFragment)
                        .commit();
            }
        });
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(llm);
        recycler.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                adapter.removeEvent(viewHolder.getLayoutPosition(),getActivity());
                adapter.notifyItemRemoved(viewHolder.getLayoutPosition());
                try {
                    getChildFragmentManager().beginTransaction().detach(showCaseFragment).commit();
                }catch (Exception e){
                    Log.wtf("deleting fragment","nothing to delete");
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recycler);

        AppCompatButton new_event = view.findViewById(R.id.new_event);
        new_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), NewEventActivity.class);
                i.putExtra("id", userId);
                startActivity(i);
            }
        });


        return view;
    }


}