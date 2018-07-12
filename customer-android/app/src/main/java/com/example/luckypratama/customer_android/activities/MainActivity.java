package com.example.luckypratama.customer_android.activities;

import android.os.Bundle;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.content.Intent;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;
import android.support.design.widget.FloatingActionButton;

import com.example.luckypratama.customer_android.R;
import com.example.luckypratama.customer_android.adapters.CustomerAdapter;
import com.example.luckypratama.customer_android.api.CustomerApi;
import com.example.luckypratama.customer_android.models.CustomerItem;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CustomerAdapter adapter;
    private ArrayList<CustomerItem> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    @Override
    protected void onResume(){
        super.onResume();
        initViews();
    }

    private void initViews(){
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        getCustomers();
    }

    private Retrofit config(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CustomerApi.BASE_PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }


    // Get customer list
    private void getCustomers(){
        CustomerApi request = this.config().create(CustomerApi.class);
        Call<ArrayList<CustomerItem>> call = request.getCustomers();
        call.enqueue(new Callback<ArrayList<CustomerItem>>() {
            @Override
            public void onResponse(Call<ArrayList<CustomerItem>> call, Response<ArrayList<CustomerItem>> response) {
                ArrayList<CustomerItem> cusResponse = response.body();
                data = new ArrayList<>(cusResponse);
                adapter = new CustomerAdapter(data);
                recyclerView.setAdapter(adapter);
                Toast.makeText(getApplicationContext(), "Customer List", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ArrayList<CustomerItem>> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }

    // Insert customer
    public void goInsert(View view){
//        Button btnAdd = findViewById(R.id.btnAdd);
        FloatingActionButton fabAdd = findViewById(R.id.btnAdd);
        fabAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getBaseContext(), InsertActivity.class);
                startActivity(intent);
            }
        });
    }
}