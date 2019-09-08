package com.omnifood.driver.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.omnifood.driver.Models.ListReadyOrders;
import com.omnifood.driver.Models.PickOrder;
import com.omnifood.driver.Models.Status;
import com.omnifood.driver.Models.Token;
import com.omnifood.driver.OmnifoodApi;
import com.omnifood.driver.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PickOrderActivity extends AppCompatActivity {

    private static final String TAG = "PickOrderActivity";

    ImageView restaurantImageView;
    TextView consumerNameTextView, consumerPhoneTextView, consumerAddressTextView,
            restaurantNameTextView, restaurantPhoneTextView, restaurantAddressTextView, totalTextView;
    Button takeOrderButton;
    OmnifoodApi omnifoodApi;
    ListReadyOrders listReadyOrders;
    Token token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_order);

        Intent pickOrderIntent = getIntent();
        listReadyOrders = (ListReadyOrders) pickOrderIntent.getSerializableExtra("orderDetails");

        SharedPreferences sharedPreferences = getSharedPreferences("token", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("token", "");
        token = gson.fromJson(json, Token.class);
        Log.d(TAG, "onCreate: Token: " + token.getToken());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RegisterActivity.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        omnifoodApi = retrofit.create(OmnifoodApi.class);

        restaurantImageView = findViewById(R.id.pick_order_image);
        consumerNameTextView = findViewById(R.id.consumer_name_value);
        consumerPhoneTextView = findViewById(R.id.consumer_phone_value);
        consumerAddressTextView = findViewById(R.id.consumer_address_value);
        restaurantNameTextView = findViewById(R.id.restaurant_name_value);
        restaurantPhoneTextView = findViewById(R.id.restaurant_phone_value);
        restaurantAddressTextView = findViewById(R.id.restaurant_address_value);
        totalTextView = findViewById(R.id.total_text_view_value);
        takeOrderButton = findViewById(R.id.take_order_button);


        Glide.with(getApplicationContext())
                .asBitmap()
                .load(listReadyOrders.getRestaurant().getLogo())
                .into(restaurantImageView);

        consumerNameTextView.setText(listReadyOrders.getConsumer().getName());
        consumerPhoneTextView.setText(listReadyOrders.getConsumer().getPhone());
        consumerAddressTextView.setText(listReadyOrders.getConsumer().getAddress());
        restaurantNameTextView.setText(listReadyOrders.getRestaurant().getName());
        restaurantPhoneTextView.setText(listReadyOrders.getRestaurant().getPhone());
        restaurantAddressTextView.setText(listReadyOrders.getRestaurant().getAddress());
        totalTextView.setText(listReadyOrders.getTotal());

        takeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickOrder pickOrder = new PickOrder(token.getToken(), listReadyOrders.getId());
                Call<Status> statusCall = omnifoodApi.pickUpOrder(token.getToken(), listReadyOrders.getId());

                statusCall.enqueue(new Callback<Status>() {
                    @Override
                    public void onResponse(Call<Status> call, Response<Status> response) {
                        if(!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Code: " + response.code() , Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse: Code: " + response.code());
                            return;
                        }

                        Status status = response.body();
                        Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onResponse: Order Picked ");
                    }

                    @Override
                    public void onFailure(Call<Status> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error message: " + t.getStackTrace(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onFailure: Error message: " + t.getMessage() );
                    }
                });
            }
        });

    }
}
















