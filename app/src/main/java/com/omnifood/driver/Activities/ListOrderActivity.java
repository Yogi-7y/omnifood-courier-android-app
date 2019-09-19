package com.omnifood.driver.Activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.omnifood.driver.Adapters.ReadyOrdersAdapter;
import com.omnifood.driver.Models.ListReadyOrders;
import com.omnifood.driver.OmnifoodApi;
import com.omnifood.driver.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListOrderActivity extends AppCompatActivity {

    private static final String TAG = "ListOrderActivity";

    OmnifoodApi omnifoodApi;
    ReadyOrdersAdapter readyOrdersAdapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    ListReadyOrders readyOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order);

        swipeRefreshLayout = findViewById(R.id.swipeRefresh_layout);

        recyclerView = findViewById(R.id.list_ready_orders_recycler_view);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RegisterActivity.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        omnifoodApi = retrofit.create(OmnifoodApi.class);

        Call<List<ListReadyOrders>> listCall = omnifoodApi.getReadyOrders();
        listCall.enqueue(new Callback<List<ListReadyOrders>>() {
            @Override
            public void onResponse(Call<List<ListReadyOrders>> call, Response<List<ListReadyOrders>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: Code: " + response.code());
                    return;
                }

                List<ListReadyOrders> listReadyOrders = response.body();
                ArrayList<ListReadyOrders> listReadyOrdersArrayList = new ArrayList<>();

                for (ListReadyOrders readyOrders : listReadyOrders) {
                    listReadyOrdersArrayList.add(new ListReadyOrders(
                            readyOrders.getId(),
                            readyOrders.getConsumer(),
                            readyOrders.getRestaurant(),
                            readyOrders.getCourier(),
                            readyOrders.getTotal(),
                            readyOrders.getStatus(),
                            readyOrders.getMeal()
                    ));
                }
                Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                readyOrdersAdapter = new ReadyOrdersAdapter(getApplicationContext(), listReadyOrdersArrayList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(readyOrdersAdapter);
            }
            @Override
            public void onFailure(Call<List<ListReadyOrders>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error message: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: Error message: " + t.getMessage() + "\n" + t.getStackTrace());
            }
        });



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getApplicationContext(), "Refreshing...", Toast.LENGTH_SHORT).show();
                Call<List<ListReadyOrders>> listCall = omnifoodApi.getReadyOrders();
                listCall.enqueue(new Callback<List<ListReadyOrders>>() {
                    @Override
                    public void onResponse(Call<List<ListReadyOrders>> call, Response<List<ListReadyOrders>> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse: Code: " + response.code());
                            return;
                        }

                        List<ListReadyOrders> listReadyOrders = response.body();
                        ArrayList<ListReadyOrders> listReadyOrdersArrayList = new ArrayList<>();

                        for (ListReadyOrders readyOrders : listReadyOrders) {
                            listReadyOrdersArrayList.add(new ListReadyOrders(
                                    readyOrders.getId(),
                                    readyOrders.getConsumer(),
                                    readyOrders.getRestaurant(),
                                    readyOrders.getCourier(),
                                    readyOrders.getTotal(),
                                    readyOrders.getStatus(),
                                    readyOrders.getMeal()
                            ));
//                            Log.d(TAG, "onResponse: Ready Orders: " + readyOrders.getRestaurant().getName() + " \n" + readyOrders.getConsumer().getName());
//                            Log.d(TAG, "onResponse: RestaurantId: " + readyOrders.getMeal().getRestaurant() + "\n Meal: " + readyOrders.getMeal().getMeal());

                        }
                        Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                        readyOrdersAdapter = new ReadyOrdersAdapter(getApplicationContext(), listReadyOrdersArrayList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(readyOrdersAdapter);


                    }

                    @Override
                    public void onFailure(Call<List<ListReadyOrders>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error message: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onFailure: Error message: " + t.getMessage() + "\n" + t.getStackTrace());
                    }
                });


                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 1000);
            }
        });
    }

}
