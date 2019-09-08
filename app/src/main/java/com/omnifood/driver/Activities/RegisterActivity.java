package com.omnifood.driver.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.omnifood.driver.Models.User;
import com.omnifood.driver.OmnifoodApi;
import com.omnifood.driver.R;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    public static final String BASE_URL = "http://192.168.0.4:8000";


    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 4 characters
                    "$");

    TextInputLayout registerUsername, registerFirstName, registerLastName, registerEmail, registerPassword, registerConfirmPassword;
    Button registerButton;
    OmnifoodApi omnifoodApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerUsername = findViewById(R.id.register_username);
        registerFirstName = findViewById(R.id.register_first_name);
        registerLastName = findViewById(R.id.register_last_name);
        registerEmail = findViewById(R.id.register_email);
        registerPassword = findViewById(R.id.register_password);
        registerConfirmPassword = findViewById(R.id.register_confirm_password);
        registerButton = findViewById(R.id.register_sign_up_button);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        omnifoodApi = retrofit.create(OmnifoodApi.class);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validateEmail() & validateFirstName() & validateLastName() & validatePassword() & validateUsername() & validateConfirmPassword()) {

                    String username = registerUsername.getEditText().getText().toString().trim();
                    String firstName = registerFirstName.getEditText().getText().toString().trim();
                    String lastName = registerLastName.getEditText().getText().toString().trim();
                    String email = registerEmail.getEditText().getText().toString().trim();
                    String password = registerPassword.getEditText().getText().toString().trim();

                    User user = new User(username, firstName, lastName, email, password);


                    Call<User> userCall = omnifoodApi.registerUser(user);

                    userCall.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (!response.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                                return;
                            }

                            User user1 = response.body();
                            Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();

                            Intent toProfileIntent = new Intent(getApplicationContext(), CompleteProfileActivity.class);
                            toProfileIntent.putExtra("user", user1);
                            startActivity(toProfileIntent);
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(RegisterActivity.this, "Error message: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

    }

    private boolean validateEmail() {

        String emailInput = registerEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            registerEmail.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            registerEmail.setError("Please enter a valid email address");
            return false;
        } else {
            registerEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = registerPassword.getEditText().getText().toString().trim();
        String confirmPasswordInput = registerPassword.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            registerPassword.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            registerPassword.setError("Password too weak");
            return false;
        } else {
            registerPassword.setError(null);
            return true;
        }

    }

    private boolean validateUsername() {

        String usernameInput = registerUsername.getEditText().getText().toString().trim();

        if (usernameInput.isEmpty()) {
            registerUsername.setError("Field can't be empty");
            return false;
        } else if (usernameInput.length() > 15) {
            registerUsername.setError("Username too long");
            return false;
        } else {
            registerUsername.setError(null);
            return true;
        }
    }

    private boolean validateFirstName() {

        String firstNameInput = registerFirstName.getEditText().getText().toString().trim();

        if (firstNameInput.isEmpty()) {
            registerFirstName.setError("Field can't be empty");
            return false;
        } else {
            registerFirstName.setError(null);
            return true;
        }
    }

    private boolean validateLastName() {

        String lastNameInput = registerLastName.getEditText().getText().toString().trim();

        if (lastNameInput.isEmpty()) {
            registerLastName.setError("Field can't be empty");
            return false;
        } else {
            registerLastName.setError(null);
            return true;
        }
    }

    private boolean validateConfirmPassword() {
        String passwordInput = registerConfirmPassword.getEditText().getText().toString().trim();
        String confirmPasswordInput = registerConfirmPassword.getEditText().getText().toString().trim();

        if (confirmPasswordInput.isEmpty()) {
            registerConfirmPassword.setError("Field can't be empty");
            return false;
        } else if (!passwordInput.equals(confirmPasswordInput)) {
            registerConfirmPassword.setError("Passwords do not match");
            return false;
        } else {
            registerConfirmPassword.setError(null);
            return true;
        }
    }

}
