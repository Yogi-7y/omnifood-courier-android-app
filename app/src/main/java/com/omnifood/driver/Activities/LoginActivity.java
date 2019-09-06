package com.omnifood.driver.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.omnifood.driver.R;

public class LoginActivity extends AppCompatActivity {

    RelativeLayout relativeLayout;
    TextInputLayout loginUsername, loginPassword;
    Button loginButton;
    TextView signUpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        relativeLayout = findViewById(R.id.relative_layout);
        loginUsername = findViewById(R.id.login_username_text_layout);
        loginPassword = findViewById(R.id.login_password_text_layout);
        loginButton = findViewById(R.id.login_button);
        signUpTextView = findViewById(R.id.login_sign_up);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = loginUsername.getEditText().getText().toString().trim();
                String password = loginPassword.getEditText().getText().toString().trim();
            }
        });

//        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
//        animationDrawable.setEnterFadeDuration(2000);
//        animationDrawable.setExitFadeDuration(4000);
//        animationDrawable.start();
    }
}
