package com.example.ashleydsouza.takeyourmeds.database;

import android.content.Context;

import com.example.ashleydsouza.takeyourmeds.dao.MedicineInformationDao;
import com.example.ashleydsouza.takeyourmeds.dao.UsersDao;
import com.example.ashleydsouza.takeyourmeds.models.MedicineInformation;
import com.example.ashleydsouza.takeyourmeds.models.Users;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Users.class, MedicineInformation.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UsersDao userDao();
    public abstract MedicineInformationDao medicineInfoDao();

    private static AppDatabase INSTANCE;
    private static String DB_NAME = "tym_db";

    public static AppDatabase getAppDatabase(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME).build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
