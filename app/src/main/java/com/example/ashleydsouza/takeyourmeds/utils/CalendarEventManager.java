package com.example.ashleydsouza.takeyourmeds.utils;

import android.support.annotation.NonNull;

import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CalendarEventManager {

    private List<CalendarEvent> calendarEvents;
    private static DatabaseReference mDatabase;
    private static CalendarEventManager instance;
    private static final String EVENTS = "/events/";
    private static final String USER_EVENTS = "/user-events/";

    public static CalendarEventManager getInstance() {
        if(instance == null) {
            instance = new CalendarEventManager();
        }
        mDatabase = FirebaseDatabase.getInstance().getReference();
        return instance;
    }

    public List<CalendarEvent> getCalendarEvents(int userId) {
        //Add your implementation here to get events from local database if needed
        Query q = mDatabase.child("user-events").child(String.valueOf(userId));
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnap: dataSnapshot.getChildren()) {
                    CalendarEvent event = dataSnap.getValue(CalendarEvent.class);
                    calendarEvents.add(event);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if(calendarEvents == null) {
            calendarEvents = new ArrayList<>();
        }
        return calendarEvents;
    }

    public void saveEvents(CalendarEvent event) {
//        if(calendarEvents == null) {
//            calendarEvents = new ArrayList<>();
//        }
//        calendarEvents.add(event);

        //Add your implementation here to save an event into your local database events
        // Create new post at /user-events/$userid/$eventid and at
        // /events/$eventid simultaneously
        String eventKey = mDatabase.child("events").push().getKey();

        Map<String, Object> eventValues = event.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(EVENTS + eventKey, eventValues);
        childUpdates.put(USER_EVENTS + event.getUserId() + "/" + eventKey, eventValues);

        mDatabase.updateChildren(childUpdates).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }
}
