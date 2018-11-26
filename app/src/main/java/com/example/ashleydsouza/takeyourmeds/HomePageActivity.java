package com.example.ashleydsouza.takeyourmeds;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        TextView welcome = findViewById(R.id.welcome_view);
        String welcomeString = getString(R.string.welcome_msg) + " " + email + "!";
        welcome.setText(welcomeString);
    }
}
