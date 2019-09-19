package com.omnifood.driver.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.omnifood.driver.Models.Login;
import com.omnifood.driver.Models.Token;
import com.omnifood.driver.OmnifoodApi;
import com.omnifood.driver.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    RelativeLayout relativeLayout;
    TextInputLayout loginUsername, loginPassword;
    Button loginButton;
    TextView signUpTextView;
    OmnifoodApi omnifoodApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        relativeLayout = findViewById(R.id.relative_layout);
        loginUsername = findViewById(R.id.login_username_text_layout);
        loginPassword = findViewById(R.id.login_password_text_layout);
        loginButton = findViewById(R.id.login_button);
        signUpTextView = findViewById(R.id.login_sign_up);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RegisterActivity.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        omnifoodApi = retrofit.create(OmnifoodApi.class);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateUsername() && validatePassword()) {

                    String username = loginUsername.getEditText().getText().toString().trim();
                    String password = loginPassword.getEditText().getText().toString().trim();

                    Login login = new Login(username, password);

                    Call<Token> loginCall = omnifoodApi.loginUser(login);

                    loginCall.enqueue(new Callback<Token>() {
                        @Override
                        public void onResponse(Call<Token> call, Response<Token> response) {
                            if (!response.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Make sure you entered correct username and password" , Toast.LENGTH_SHORT).show();                                Log.d(TAG, "onResponse: Code: " + response.code());
                                return;
                            }

                            Token token = response.body();
//                            Toast.makeText(getApplicationContext(), "You are successfully logged in.." + token.getFirstName(), Toast.LENGTH_SHORT).show();

                            SharedPreferences sharedPreferences = getSharedPreferences("token", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            Gson gson = new Gson();
                            String json = gson.toJson(token);
                            editor.putString("token", json);
                            editor.apply();

                            Intent listOrderIntent = new Intent(getApplicationContext(), ListOrderActivity.class);
                            startActivity(listOrderIntent);
                        }

                        @Override
                        public void onFailure(Call<Token> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Error message: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onFailure: Error message: " + t.getMessage());
                        }
                    });
                }
            }
        });

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
    }

    private boolean validateUsername() {

        String usernameInput = loginUsername.getEditText().getText().toString().trim();

        if (usernameInput.isEmpty()) {
            loginUsername.setError("Field can't be empty");
            return false;
        } else {
            loginUsername.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = loginPassword.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            loginPassword.setError("Field can't be empty");
            return false;
        } else {
            loginPassword.setError(null);
            return true;
        }
    }
}
