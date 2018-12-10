package com.example.ashleydsouza.takeyourmeds.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ashleydsouza.takeyourmeds.R;
import com.example.ashleydsouza.takeyourmeds.models.MedicineInformation;
import com.example.ashleydsouza.takeyourmeds.models.Users;

import java.util.ArrayList;
import java.util.List;

public class GenericAdaptor extends RecyclerView.Adapter<GenericAdaptor.GenericHolder> {

    private List<MedicineInformation> meds = new ArrayList<>();

    @NonNull
    @Override
    public GenericHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.prescription_view, viewGroup, false);
        return new GenericHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GenericHolder genericHolder, int i) {
        MedicineInformation med = meds.get(i);
        genericHolder.medName.setText(med.getName());
        genericHolder.medDosage.setText(med.getDosage());
        genericHolder.medAmount.setText(String.valueOf(med.getAmount()));
        genericHolder.medTime.setText(med.getTime());
        genericHolder.medNotes.setText(med.getAdditionalNotes());
    }

    @Override
    public int getItemCount() {
        return meds != null ? meds.size() : 0;
    }

    public void setMedicines(List<MedicineInformation> meds) {
        this.meds = meds;
        notifyDataSetChanged();         //not to be used
    }

    //to get MedicineInformation at a position in the View; Used for swiping to delete medicine
    public MedicineInformation getMedsAt(int position) {
        return meds.get(position);
    }

    //this will hold the views in our Recycler View
    class GenericHolder extends RecyclerView.ViewHolder {
        private TextView medName;
        private TextView medDosage;
        private TextView medAmount;
        private TextView medTime;
        private TextView medNotes;

        public GenericHolder(@NonNull View itemView) {
            super(itemView);
            medName = itemView.findViewById(R.id.med_name);
            medDosage = itemView.findViewById(R.id.med_dosage);
            medAmount = itemView.findViewById(R.id.med_dosage_amount);
            medTime = itemView.findViewById(R.id.med_time);
            medNotes = itemView.findViewById(R.id.med_notes);
        }
    }
}
