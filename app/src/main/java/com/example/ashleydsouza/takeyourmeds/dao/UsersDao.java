package com.example.ashleydsouza.takeyourmeds.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.ashleydsouza.takeyourmeds.models.Users;

import java.util.List;


@Dao
public interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertUser(Users user);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public void updateUser(Users user);

    @Delete
    public void deleteUser(Users user);

    @Query("SELECT * FROM Users WHERE email = :email AND password = :password")
    public LiveData<List<Users>> getUsersWithCredentials(String email, String password);

    @Query("SELECT * FROM Users WHERE userId = :userId")
    public LiveData<Users> getUserWithId(int userId);
}
