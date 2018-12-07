package com.example.ashleydsouza.takeyourmeds.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.ashleydsouza.takeyourmeds.models.MedicineInformation;
import com.example.ashleydsouza.takeyourmeds.models.Users;

import java.util.List;

public class GenericAdaptor extends RecyclerView.Adapter {

    private List<MedicineInformation> medicineList;
    private List<Users> userList;
    private Context context;

    public GenericAdaptor(Context context, List<Users> userList) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return userList != null ? userList.size() : 0;
    }

    static class GenericViewHolder extends RecyclerView.ViewHolder {

        public GenericViewHolder(View v) {
            super(v);
        }
    }
}
