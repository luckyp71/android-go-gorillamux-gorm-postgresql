package com.example.luckypratama.customer_android.adapters;

import android.content.Intent;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luckypratama.customer_android.activities.InsertActivity;
import com.example.luckypratama.customer_android.activities.UpdateActivity;
import com.example.luckypratama.customer_android.api.CustomerApi;
import com.example.luckypratama.customer_android.models.CustomerItem;
import com.example.luckypratama.customer_android.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private ArrayList<CustomerItem> customerList;

    public CustomerAdapter(ArrayList<CustomerItem> customerList) {
        this.customerList = customerList;
    }

    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_item, parent, false);
        CustomerViewHolder cusViewHolder = new CustomerViewHolder(v);
        return cusViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomerAdapter.CustomerViewHolder holder, final int position) {
        holder.id.setText("ID: " + String.valueOf(customerList.get(position).getId()));
        holder.name.setText("Name: " + customerList.get(position).getName());
        final CustomerItem customer = customerList.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View viewItem = view;
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(CustomerApi.BASE_PATH)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                CustomerApi request = retrofit.create(CustomerApi.class);
                Call<CustomerItem> call = request.getCustomer(customer.getId());
                call.enqueue(new Callback<CustomerItem>() {
                    @Override
                    public void onResponse(Call<CustomerItem> call, final Response<CustomerItem> response) {
                        Context ctx = viewItem.getContext();
                        Intent intent = new Intent(ctx, UpdateActivity.class);
                        intent.putExtra("customer", response.body());
                        ctx.startActivity(intent);
                        Toast.makeText(viewItem.getContext(), "sss", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<CustomerItem> call, Throwable t) {
                        Log.d("Error", t.getMessage());
                    }
                });
            }

        });
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public static class CustomerViewHolder extends RecyclerView.ViewHolder {
        private TextView id;
        private TextView name;

        public CustomerViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.cusID);
            name = itemView.findViewById(R.id.name);
        }
    }
}