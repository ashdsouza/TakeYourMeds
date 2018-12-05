package com.example.ashleydsouza.takeyourmeds.dao;

import android.arch.lifecycle.LiveData;

import com.example.ashleydsouza.takeyourmeds.models.Users;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UsersDao {

    @Insert
    public void insertUser(Users user);

    @Update
    public void updateUser(Users user);

    @Delete
    public void deleteUser(Users user);

    @Query("SELECT * FROM Users WHERE email = :email AND password = :password")
    LiveData<List<Users>> getUsersWithCredentials(String email, String password);

    @Query("SELECT * FROM Users WHERE userId = :userId")
    LiveData<Users> getUserWithId(int userId);
}
