package com.example.ashleydsouza.takeyourmeds.utils;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class CalendarEventManager {

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

    public void saveEvents(CalendarEvent event) {
        //Firebase Implementation here to save an event into Database
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
