package com.example.ashleydsouza.takeyourmeds.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ashleydsouza.takeyourmeds.R;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.ArrayList;
import java.util.List;

public class CalendarEventAdaptor extends RecyclerView.Adapter<CalendarEventAdaptor.CalendarEventAdaptorHolder> {

    private List<Event> events = new ArrayList<>();

    @NonNull
    @Override
    public CalendarEventAdaptorHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.calendar_events_view, viewGroup, false);
        return new CalendarEventAdaptorHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarEventAdaptorHolder calEventHolder, int i) {
        Event event = events.get(i);
        calEventHolder.eventDesc.setText((String) event.getData());
    }

    @Override
    public int getItemCount() {
        return events != null ? events.size() : 0;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
        notifyDataSetChanged();         //not to be used
    }

    //to get Event at a position in the View; Used for swiping to delete event
    public Event getMedsAt(int position) {
        return events.get(position);
    }

    //this will hold the views in our Recycler View
    class CalendarEventAdaptorHolder extends RecyclerView.ViewHolder {
        private TextView eventDesc;

        public CalendarEventAdaptorHolder(@NonNull View itemView) {
            super(itemView);
            eventDesc = itemView.findViewById(R.id.event_description);
        }
    }
}
