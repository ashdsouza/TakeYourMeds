package com.example.ashleydsouza.takeyourmeds.dao;

import android.arch.lifecycle.LiveData;

import com.example.ashleydsouza.takeyourmeds.models.MedicineInformation;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface MedicineInformationDao {

    @Insert
    public void insertMedicineInformation(MedicineInformation meds);

    @Update
    public void updateMedicineInformation(MedicineInformation meds);

    @Delete
    public void deleteMedicineInformation(MedicineInformation meds);

    @Query("SELECT * FROM MedicineInformation WHERE userId = :userId")
    LiveData<List<MedicineInformation>> getAllMedicineEntriesForUser(int userId);

    @Query("SELECT * FROM MedicineInformation WHERE medId = :medId")
    LiveData<MedicineInformation> getMedicineInformationById(int medId);
}
