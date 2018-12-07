package com.example.ashleydsouza.takeyourmeds.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ashleydsouza.takeyourmeds.R;
import com.example.ashleydsouza.takeyourmeds.cruds.UserCrudImplementation;
import com.example.ashleydsouza.takeyourmeds.models.Users;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private TextInputLayout inputLayoutEmail, inputLayoutPassword;
    private TextView userCredsErr;
    private Button btnLogin, btnSignup;
    private String realName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputLayoutEmail = findViewById(R.id.input_layout_email);
        inputLayoutPassword = findViewById(R.id.input_layout_password);
        userCredsErr = findViewById(R.id.user_creds_err);
        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        btnLogin = findViewById(R.id.btn_login);
        btnSignup = findViewById(R.id.btn_signup);

        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginForm();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
    }

    private void loginForm() {

        if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

        if (!validateCredentials()) {
            return;
        }

        String email = inputEmail.getText().toString().trim();
        Intent intent = new Intent(this, HomePageActivity.class);
        intent.putExtra("email", email);
        intent.putExtra("name", realName);
        startActivity(intent);
    }

    private boolean validateEmail() {

        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_pass));
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private LiveData<List<Users>> getRegisteredUser(String email, String password) {
        UserCrudImplementation userCrud = new UserCrudImplementation(getApplicationContext());
        final MediatorLiveData<List<Users>> userList = new MediatorLiveData<>();

        //Check if user is registered
        userCrud.getUser(email, password).observe(this, new Observer<List<Users>>() {
            @Override
            public void onChanged(@Nullable List<Users> users) {
                if (users != null && !users.isEmpty()) {
                    System.out.println("Registered users = " + users.size());
                    System.out.println("Name = " + users.get(0).getName());
                    userList.setValue(users);
                }
            }
        });
        return userList;
    }

    private boolean validateCredentials() {
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        LiveData<List<Users>> list = getRegisteredUser(email, password);
        if(list.getValue() != null && list.getValue().isEmpty()) {
            userCredsErr.setText(getString(R.string.err_user_not_registered));
            userCredsErr.setVisibility(View.VISIBLE);
            requestFocus(inputEmail);
            return false;
        } else {
            if(list.getValue() != null)
                realName = list.getValue().get(0).getName();
                System.out.println("Real name = " + realName);
            userCredsErr.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
            }
        }
    }
}
