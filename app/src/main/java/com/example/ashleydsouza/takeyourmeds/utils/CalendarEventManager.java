package com.example.ashleydsouza.takeyourmeds.utils;

import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.ArrayList;
import java.util.List;


public class CalendarEventManager {

    private List<Event> calendarEvents;

    private static CalendarEventManager instance;

    public static CalendarEventManager getInstance() {
        if(instance == null) {
            instance = new CalendarEventManager();
        }
        return instance;
    }

    public List<Event> getCalendarEvents() {
        //Add your implementation here to get events from local database if needed
        if(calendarEvents == null) {
            calendarEvents = new ArrayList<>();
        }
        return calendarEvents;
    }

    public void saveEvents(Event meds) {
        if(calendarEvents == null) {
            calendarEvents = new ArrayList<>();
        }
        calendarEvents.add(meds);
        //Add your implementation here to save an event into your local database events
    }
}
