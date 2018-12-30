package com.example.ashleydsouza.takeyourmeds.cruds;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.ashleydsouza.takeyourmeds.dao.MedicineInformationDao;
import com.example.ashleydsouza.takeyourmeds.database.AppDatabase;
import com.example.ashleydsouza.takeyourmeds.models.MedicineInformation;

import java.util.List;

public class MedicineRepository {
    private MedicineInformationDao medDao;

    public MedicineRepository(Application application) {
        AppDatabase db = AppDatabase.getAppDatabase(application);
        medDao = db.medicineInfoDao();
    }

    public void insertMeds(List<MedicineInformation> meds) {
        new InsertMedAsyncTask(medDao).execute(meds.toArray(new MedicineInformation[meds.size()]));
    }

    public void updateMed(MedicineInformation med) {
        new UpdateMedAsyncTask(medDao).execute(med);
    }

    public void deleteMed(MedicineInformation med) {
        new DeleteMedAsyncTask(medDao).execute(med);
    }

    public LiveData<List<MedicineInformation>> getMedsForUser(int userId) {
        return medDao.getAllMedicineEntriesForUser(userId);
    }

    public LiveData<MedicineInformation> getMedsForId(int medId) {
        return medDao.getMedicineInformationById(medId);
    }

    private static class InsertMedAsyncTask extends AsyncTask<MedicineInformation, Void, Void> {
        private MedicineInformationDao medDao;

        private InsertMedAsyncTask(MedicineInformationDao medDao) {
            this.medDao = medDao;
        }

        @Override
        protected Void doInBackground(MedicineInformation... lists) {
            medDao.insertMedicineInformation(lists);
            return null;
        }
    }

    private static class UpdateMedAsyncTask extends AsyncTask<MedicineInformation, Void, Void> {
        private MedicineInformationDao medDao;

        private UpdateMedAsyncTask(MedicineInformationDao medDao) {
            this.medDao = medDao;
        }

        @Override
        protected Void doInBackground(MedicineInformation... lists) {
            medDao.updateMedicineInformation(lists[0]);
            return null;
        }
    }

    private static class DeleteMedAsyncTask extends AsyncTask<MedicineInformation, Void, Void> {
        private MedicineInformationDao medDao;

        private DeleteMedAsyncTask(MedicineInformationDao medDao) {
            this.medDao = medDao;
        }

        @Override
        protected Void doInBackground(MedicineInformation... lists) {
            medDao.deleteMedicineInformation(lists[0]);
            return null;
        }
    }
}
