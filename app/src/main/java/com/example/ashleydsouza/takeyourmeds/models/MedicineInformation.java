package com.example.ashleydsouza.takeyourmeds.models;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Users.class, parentColumns = "userId", childColumns = "userId", onDelete = CASCADE), indices = {@Index("userId")})
public class MedicineInformation {
    @PrimaryKey(autoGenerate = true)
    private int medId;

    @NonNull
    private String name;
    @NonNull
    private String dosage;
    @NonNull
    private Integer amount;
    @NonNull
    private String time;
    private String additionalNotes;

    private int userId;

    public MedicineInformation() {

    }

//    public MedicineInformation(String name, String dosage, Integer amount,
//                               String time, String additionalNotes) {
//        this.name = name;
//        this.dosage = dosage;
//        this.amount = amount;
//        this.time = time;
//        if(additionalNotes != null) {
//            this.additionalNotes = additionalNotes;
//        }
//    }

    public int getMedId() {
        return medId;
    }

    public void setMedId(int medId) {
        this.medId = medId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void printMedicine() {
        System.out.println("---Prescription---");
        System.out.println("Name = " + this.name);
        System.out.println("Dosage = " + this.dosage);
        System.out.println("Amount = " + this.amount);
        System.out.println("Time = " + this.time);
        if(this.additionalNotes != null) System.out.println("Notes = " + this.additionalNotes);
        System.out.println("------------------");
    }
}
