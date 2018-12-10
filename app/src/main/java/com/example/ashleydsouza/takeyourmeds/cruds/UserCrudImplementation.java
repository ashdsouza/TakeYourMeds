package com.example.ashleydsouza.takeyourmeds.cruds;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;

import com.example.ashleydsouza.takeyourmeds.database.AppDatabase;
import com.example.ashleydsouza.takeyourmeds.models.Users;

import java.util.List;

public class UserCrudImplementation {

    private AppDatabase appDb;
    private MediatorLiveData<List<Users>> mUsersLive = new MediatorLiveData<>();
    public UserCrudImplementation(Context context) {
        appDb = AppDatabase.getAppDatabase(context);
    }

    public void insertUser(Users user) {
        insertUser(user, false, false);
    }

    public void insertUser(Users user, boolean encrypt, boolean sendNotification) {
        if(encrypt) {
            //TODO: Implement this for security later
        }
        user.setSendNotification(sendNotification);
        insertUserInDb(user);
    }

    public void insertUserInDb(final Users user) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                appDb.userDao().insertUser(user);
            }
        }).start();
    }

    public void updateUser(final Users user) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                appDb.userDao().updateUser(user);
            }
        }).start();
    }

    public void deleteUser(final Users user) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                appDb.userDao().deleteUser(user);
            }
        }).start();
    }

    public LiveData<Users> getUser(int id) {
        return appDb.userDao().getUserWithId(id);
    }

    public LiveData<List<Users>> getUser(String email, String password) {
        return appDb.userDao().getUsersWithCredentials(email, password);
//        final LiveData<List<Users>> users = appDb.userDao().getUsersWithCredentials(email, password);
//
//        mUsersLive.addSource(users, new Observer<List<Users>>() {
//            @Override
//            public void onChanged(@Nullable List<Users> userList) {
//                if(userList != null && !userList.isEmpty()) {
//                    mUsersLive.removeSource(users);
//                    mUsersLive.setValue(userList);
//                }
//            }
//        });

//        return mUsersLive;
    }
}
