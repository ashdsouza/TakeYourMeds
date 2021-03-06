package com.example.ashleydsouza.takeyourmeds.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ashleydsouza.takeyourmeds.models.Users;
import com.google.gson.Gson;

public class Session {
    private SharedPreferences perfs;
    private SharedPreferences.Editor editor;

    private String EMAIL = "email";
    private String NAME = "name";
    private String USERID = "userId";
    private String LOGIN = "login";
    private String USER_OBJ = "UserObj";
    private String PREFERENCE = "tym";

    public Session(Context context) {
        perfs = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        editor = perfs.edit();
    }

    public void storeUser(Users user) {
        editor.putString(EMAIL, user.getEmail());
        editor.putString(NAME, user.getName());
        editor.putInt(USERID, user.getUserId());
        editor.putBoolean(LOGIN, true);

        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(USER_OBJ, json);

        editor.commit();
    }

    public void setName(String name) {
        editor.putString(NAME, name).apply();
    }

    public void setEmail(String email) {
        editor.putString(EMAIL, email).apply();
    }

    public void setUserId(int userId) {
        editor.putInt(USERID, userId).apply();
    }

    public String getName() {
        return perfs.getString(NAME,"");
    }

    public String getEmail() {
        return perfs.getString(EMAIL,"");
    }

    public int getUserId() {
        return perfs.getInt(USERID,-1);
    }

    public String getUserObj() {
        return perfs.getString(USER_OBJ,"");
    }

    public boolean isUserLoggedIn() {
        return perfs.getBoolean(LOGIN, false);
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();
    }
}
