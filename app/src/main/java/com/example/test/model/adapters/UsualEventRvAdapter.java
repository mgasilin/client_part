package com.example.test.model.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.Server.Server;
import com.example.test.model.Event;
import com.example.test.model.adapters.listeners.CustomItemClickListener;

import java.util.List;

public class UsualEventRvAdapter extends RecyclerView.Adapter<UsualEventRvAdapter.EventViewHolder> {

    public static class EventViewHolder extends RecyclerView.ViewHolder {

        TextView eventName;
        TextView eventPlace;

        EventViewHolder(View itemView) {
            super(itemView);
            eventName = (TextView) itemView.findViewById(R.id.ev_name);
            eventPlace = (TextView) itemView.findViewById(R.id.ev_place);
        }
    }

    List<Event> events;
    CustomItemClickListener listener;


    public UsualEventRvAdapter(List<Event> events, CustomItemClickListener listener) {
        this.events = events;this.listener=listener;
    }

    public void removeEvent(int position, Context c) {
        Server.removeEvent(events.get(position),c);
        events.remove(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_usual_event, viewGroup, false);
        final EventViewHolder eventViewHolder = new EventViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(v, eventViewHolder.getPosition());
            }
        });
        return eventViewHolder;
    }

    @Override
    public void onBindViewHolder(EventViewHolder eventViewHolder, int i) {
        eventViewHolder.eventName.setText(events.get(i).getName());
        eventViewHolder.eventPlace.setText(events.get(i).getPlace());
    }

    public Event getEvent(int pos){return  events.get(pos);}

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}