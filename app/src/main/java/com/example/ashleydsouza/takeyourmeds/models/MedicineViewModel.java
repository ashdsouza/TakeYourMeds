package com.example.ashleydsouza.takeyourmeds.models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.ashleydsouza.takeyourmeds.cruds.MedicineCrudImplementation;

import java.util.List;

public class MedicineViewModel extends AndroidViewModel {

    private MedicineCrudImplementation medCrud;

    public MedicineViewModel(@NonNull Application application) {
        super(application);
        medCrud = new MedicineCrudImplementation(application);
    }

    public void insert(List<MedicineInformation> meds) {
        medCrud.insertMeds(meds);
    }

    public void updateMed(MedicineInformation med) {
        medCrud.updateMeds(med);
    }

    public void delete(MedicineInformation med) {
        medCrud.deleteMeds(med);
    }

    public LiveData<List<MedicineInformation>> getMedsForUser(int userId) {
        return medCrud.getMedsForUser(userId);
    }

    public LiveData<MedicineInformation> getMedById(int medId) {
        return medCrud.getMedsById(medId);
    }
}
