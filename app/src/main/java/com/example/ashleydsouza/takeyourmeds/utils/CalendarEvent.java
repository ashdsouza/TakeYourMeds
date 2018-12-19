package com.example.ashleydsouza.takeyourmeds.utils;


import java.util.HashMap;
import java.util.Map;

public class CalendarEvent {
    private Integer userId;
    private Integer color;
    private Long timeInMillis;
    private Object data;

    public CalendarEvent(Integer userId, Integer color, Long timeInMillis, Object data) {
        this.userId = userId;
        this.color = color;
        this.timeInMillis = timeInMillis;
        this.data = data;
    }

    public Integer getColor() {
        return color;
    }

    public Long getTimeInMillis() {
        return timeInMillis;
    }

    public Object getData() {
        return data;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public void setTimeInMillis(Long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("eventColor", color);
        result.put("timeEventAdded", timeInMillis);
        result.put("description", data);

        return result;
    }
}
