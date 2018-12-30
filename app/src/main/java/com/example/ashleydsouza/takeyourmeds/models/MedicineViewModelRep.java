package com.example.ashleydsouza.takeyourmeds.models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.ashleydsouza.takeyourmeds.cruds.MedicineRepository;

import java.util.List;

public class MedicineViewModelRep extends AndroidViewModel {

    private MedicineRepository medRepo;

    public MedicineViewModelRep(@NonNull Application application) {
        super(application);
        medRepo = new MedicineRepository(application);
    }

    public void insert(List<MedicineInformation> meds) {
        medRepo.insertMeds(meds);
    }

    public void updateMed(MedicineInformation med) {
        medRepo.updateMed(med);
    }

    public void delete(MedicineInformation med) {
        medRepo.deleteMed(med);
    }

    public LiveData<List<MedicineInformation>> getMedsForUser(int userId) {
        return medRepo.getMedsForUser(userId);
    }

    public LiveData<MedicineInformation> getMedById(int medId) {
        return medRepo.getMedsForId(medId);
    }
}
