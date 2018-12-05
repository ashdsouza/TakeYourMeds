package com.example.ashleydsouza.takeyourmeds.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.ashleydsouza.takeyourmeds.dao.MedicineInformationDao;
import com.example.ashleydsouza.takeyourmeds.dao.UsersDao;
import com.example.ashleydsouza.takeyourmeds.models.MedicineInformation;
import com.example.ashleydsouza.takeyourmeds.models.Users;


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
