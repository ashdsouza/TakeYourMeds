package com.example.ashleydsouza.takeyourmeds.models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.ashleydsouza.takeyourmeds.cruds.UserCrudImplementation;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserCrudImplementation userCrudRepo;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userCrudRepo = new UserCrudImplementation(application);
    }

    public void insert(Users user) {
        userCrudRepo.insertUser(user);
    }

    public void updateUser(Users user) {
        userCrudRepo.updateUser(user);
    }

    public void delete(Users user) {
        userCrudRepo.deleteUser(user);
    }

    public LiveData<Users> getUser(int id) {
        return userCrudRepo.getUser(id);
    }

    public LiveData<List<Users>> getUserList(String email, String password) {
        return userCrudRepo.getUser(email, password);
    }
}
