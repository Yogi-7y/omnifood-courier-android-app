package com.omnifood.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.omnifood.driver.Activities.RegisterActivity;
import com.omnifood.driver.Models.Courier;
import com.omnifood.driver.Models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompleteProfileActivity extends AppCompatActivity {

    private static final String TAG = "CompleteProfileActivity";

    TextInputLayout profilePhone, profileAddress;
    Button profileButton;
    OmnifoodApi omnifoodApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);

        Intent fromRegisterIntent = getIntent();
        final User user = (User) fromRegisterIntent.getSerializableExtra("user");

        profilePhone = findViewById(R.id.complete_phone_number);
        profileAddress = findViewById(R.id.complete_address);
        profileButton = findViewById(R.id.complete_submit_button);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RegisterActivity.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        omnifoodApi = retrofit.create(OmnifoodApi.class);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateAddress() && validatePhoneNUmber()) {

                    String phone = profilePhone.getEditText().getText().toString().trim();
                    String address = profileAddress.getEditText().getText().toString().trim();

                    Courier courier = new Courier(Integer.parseInt(user.getId()), phone, address);

                    Call<Courier> courierCall = omnifoodApi.completeProfile(courier);

                    courierCall.enqueue(new Callback<Courier>() {
                        @Override
                        public void onResponse(Call<Courier> call, Response<Courier> response) {
                            if(!response.isSuccessful()) {
                                Toast.makeText(CompleteProfileActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onResponse: Code: " + response.code());
                                return;
                            }

                            Courier courier = response.body();
                            Toast.makeText(getApplicationContext(), "" + courier.getPhone(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse: Courier Phone: " + courier.getPhone());
                        }

                        @Override
                        public void onFailure(Call<Courier> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Error message: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    private boolean validatePhoneNUmber() {

        String phoneNumberInput = profilePhone.getEditText().getText().toString().trim();

        if (phoneNumberInput.isEmpty()) {
            profilePhone.setError("Field can't be empty");
            return false;
        } else {
            profilePhone.setError(null);
            return true;
        }
    }

    private boolean validateAddress() {

        String addressInput = profileAddress.getEditText().getText().toString().trim();

        if (addressInput.isEmpty()) {
            profileAddress.setError("Field can't be empty");
            return false;
        } else {
            profileAddress.setError(null);
            return true;
        }
    }
}
