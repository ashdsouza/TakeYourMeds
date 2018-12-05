package com.example.ashleydsouza.takeyourmeds.cruds;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.ashleydsouza.takeyourmeds.database.AppDatabase;
import com.example.ashleydsouza.takeyourmeds.models.MedicineInformation;

import java.util.List;

public class MedicineCrudImplementation {

    private AppDatabase appDb;
    public MedicineCrudImplementation(Context context) {
        appDb = AppDatabase.getAppDatabase(context);
    }

    public void insertMeds(final MedicineInformation meds) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                appDb.medicineInfoDao().insertMedicineInformation(meds);
            }
        }).start();
    }

    public void updateMeds(final MedicineInformation meds) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                appDb.medicineInfoDao().updateMedicineInformation(meds);
            }
        }).start();
    }

    public void deleteMeds(final MedicineInformation meds) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                appDb.medicineInfoDao().deleteMedicineInformation(meds);
            }
        }).start();
    }

    public LiveData<List<MedicineInformation>> getMedsForUser(int userId) {
        return appDb.medicineInfoDao().getAllMedicineEntriesForUser(userId);
    }

    public LiveData<MedicineInformation> getMedsById(int medId) {
        return appDb.medicineInfoDao().getMedicineInformationById(medId);
    }
}
