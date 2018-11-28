package com.example.ashleydsouza.takeyourmeds;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

//        Intent intent = getIntent();
//        String email = intent.getStringExtra("email");

        TextView welcome = findViewById(R.id.welcome_view);
        String welcomeString = "Hi Ashley!";//getString(R.string.welcome_msg) + " " + email + "!";


        Calendar nowCal = Calendar.getInstance();
        String prescriptionForToday = getPrescriptionsForToday(nowCal.getTime(), null);

        welcomeString = welcomeString + "\n" + prescriptionForToday;

        welcome.setText(welcomeString);
    }

    public String getPrescriptionsForToday(Date date, String userEmail) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
        String loginDate = dateFormat.format(date);

        /**
         Get list of prescription based on date and userEmail
         set this to true when call is made to DB and there are results
         **/
        boolean hasPrescription = false;
        String listOfPrescription = "For " + loginDate + "\n";
        if(hasPrescription) {
            listOfPrescription += "";
        } else {
            listOfPrescription += getString(R.string.no_user_prescription);
        }
        return listOfPrescription;
    }
}
