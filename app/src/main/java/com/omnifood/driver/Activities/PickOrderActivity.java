package com.omnifood.driver.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import com.omnifood.driver.Models.OrderDelivered;
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
            restaurantNameTextView, restaurantPhoneTextView, restaurantAddressTextView, totalTextView,
    getDirectionTextView;
    Button takeOrderButton, orderDeliveredButton;
    OmnifoodApi omnifoodApi;
    ListReadyOrders listReadyOrders;
    Token token;
    String latitude, longitude;

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
        orderDeliveredButton = findViewById(R.id.order_delivered_button);
//        getDirectionTextView = findViewById(R.id.get_direction);


        orderDeliveredButton.setEnabled(false);
        orderDeliveredButton.setBackgroundColor(getResources().getColor(R.color.gray_btn_bg_color));


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

        latitude = listReadyOrders.getConsumer().getLatitude();
        longitude = listReadyOrders.getConsumer().getLongitude();
        Log.d(TAG, "onCreate: Latitude: " + latitude);
        Log.d(TAG, "onCreate: Longitude: " + longitude);

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
                        orderDeliveredButton.setEnabled(true);
                        orderDeliveredButton.setBackgroundColor(getResources().getColor(R.color.button_color));
                        takeOrderButton.setEnabled(false);
                        takeOrderButton.setBackgroundColor(getResources().getColor(R.color.gray_btn_bg_color));
                    }

                    @Override
                    public void onFailure(Call<Status> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error message: " + t.getStackTrace(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onFailure: Error message: " + t.getMessage() );
                    }
                });
            }
        });

        orderDeliveredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OrderDelivered orderDelivered = new OrderDelivered(token.getToken(), listReadyOrders.getId());

                Call<Status> statusCall = omnifoodApi.orderDelivered(orderDelivered.getToken(), orderDelivered.getOrderId());

                statusCall.enqueue(new Callback<Status>() {
                    @Override
                    public void onResponse(Call<Status> call, Response<Status> response) {
                        if(!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse: Code: " + response.code());
                            return;
                        }

                        Status status = response.body();
                        Toast.makeText(getApplicationContext(), "" + status.getStatus(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<Status> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error message: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onFailure: Error message: " + t.getMessage());
                    }
                });
            }
        });

//        getDirectionTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String uri = "https://www.google.com/maps?daddr="+latitude+","+longitude;
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                startActivity(intent);
//            }
//        });

    }
}
















