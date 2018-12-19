package com.example.ashleydsouza.takeyourmeds.utils;


import java.util.HashMap;
import java.util.Map;

public class CalendarEvent {
    private Integer userId;
    private Integer eventColor;
    private Long timeEventAdded;
    private Object description;

    public CalendarEvent() {}

    public CalendarEvent(Integer userId, Integer eventColor, Long timeEventAdded, Object description) {
        this.userId = userId;
        this.eventColor = eventColor;
        this.timeEventAdded = timeEventAdded;
        this.description = description;
    }

    public Integer getEventColor() {
        return eventColor;
    }

    public Long getTimeEventAdded() {
        return timeEventAdded;
    }

    public Object getDescription() {
        return description;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setEventColor(Integer eventColor) {
        this.eventColor = eventColor;
    }

    public void setTimeEventAdded(Long timeEventAdded) {
        this.timeEventAdded = timeEventAdded;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("eventColor", eventColor);
        result.put("timeEventAdded", timeEventAdded);
        result.put("description", description);

        return result;
    }
}
