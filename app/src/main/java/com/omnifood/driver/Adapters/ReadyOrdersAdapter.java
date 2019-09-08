package com.omnifood.driver.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.omnifood.driver.Activities.PickOrderActivity;
import com.omnifood.driver.Models.ListReadyOrders;
import com.omnifood.driver.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReadyOrdersAdapter extends RecyclerView.Adapter<ReadyOrdersAdapter.ReadyOrderViewHolder>{

    private static final String TAG = "ReadyOrdersAdapter";

    private ArrayList<ListReadyOrders> readyOrdersArrayList;
    private Context context;

    public ReadyOrdersAdapter(Context context, ArrayList<ListReadyOrders> readyOrdersArrayList) {
        this.readyOrdersArrayList = readyOrdersArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ReadyOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.get_ready_order_list_item, parent, false);
        return new ReadyOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ReadyOrderViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: ");

        Glide.with(context)
                .asBitmap()
                .load(readyOrdersArrayList.get(position).getRestaurant().getLogo())
                .into(holder.restaurantLogoImageView);
        holder.consumerAddressTextView.setText(readyOrdersArrayList.get(position).getConsumer().getAddress());
        holder.restaurantAddressTextView.setText(readyOrdersArrayList.get(position).getRestaurant().getAddress());
        holder.restaurantPhoneTextView.setText(readyOrdersArrayList.get(position).getRestaurant().getPhone());

        holder.listReadyOrderItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Clicked: " + holder.restaurantAddressTextView.getText().toString(), Toast.LENGTH_SHORT).show();

                Intent pickOrderIntent = new Intent(context, PickOrderActivity.class);
                pickOrderIntent.putExtra("orderDetails", readyOrdersArrayList.get(position));
                pickOrderIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(pickOrderIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return readyOrdersArrayList.size();
    }

    public class ReadyOrderViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView listReadyOrderItem;
        CircleImageView restaurantLogoImageView;
        TextView consumerAddressTextView, restaurantAddressTextView, restaurantPhoneTextView;

        public ReadyOrderViewHolder(@NonNull View itemView) {
            super(itemView);

            listReadyOrderItem = itemView.findViewById(R.id.ready_list_order_parent);
            restaurantLogoImageView = itemView.findViewById(R.id.circle_image_view);
            consumerAddressTextView = itemView.findViewById(R.id.consumer_address_text_view);
            restaurantAddressTextView = itemView.findViewById(R.id.restaurant_address_text_view);
            restaurantPhoneTextView = itemView.findViewById(R.id.restaurant_contact_text_view);
        }
    }
}
