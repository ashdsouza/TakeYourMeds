package com.example.ashleydsouza.takeyourmeds.models;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Users.class, parentColumns = "userId", childColumns = "userId", onDelete = CASCADE))
public class MedicineInformation {
    @PrimaryKey(autoGenerate = true)
    private int medId;

    private String name;
    private String dosage;
    private Integer amount;
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
