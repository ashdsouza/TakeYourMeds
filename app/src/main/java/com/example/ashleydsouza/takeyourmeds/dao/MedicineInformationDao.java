package com.example.ashleydsouza.takeyourmeds.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.ashleydsouza.takeyourmeds.models.MedicineInformation;

import java.util.List;



@Dao
public interface MedicineInformationDao {

    @Insert
    public void insertMedicineInformation(List<MedicineInformation> meds);

    @Insert
    public void insertMedicineInformation(MedicineInformation... meds);

    @Update
    public void updateMedicineInformation(MedicineInformation meds);

    @Delete
    public void deleteMedicineInformation(MedicineInformation meds);

    @Query("SELECT * FROM MedicineInformation WHERE userId = :userId")
    public LiveData<List<MedicineInformation>> getAllMedicineEntriesForUser(int userId);

    @Query("SELECT * FROM MedicineInformation WHERE medId = :medId")
    public LiveData<MedicineInformation> getMedicineInformationById(int medId);
}
