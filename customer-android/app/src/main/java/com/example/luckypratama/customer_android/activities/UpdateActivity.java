package com.example.luckypratama.customer_android.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.luckypratama.customer_android.R;
import com.example.luckypratama.customer_android.api.CustomerApi;
import com.example.luckypratama.customer_android.models.CustomerItem;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateActivity extends AppCompatActivity {

    private EditText customerId, customerName, customerAddress, customerPhoneNumber, customerEmail;
    private Button btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        customerId = findViewById(R.id.customerId);
        customerName = findViewById(R.id.customerName);
        customerAddress = findViewById(R.id.customerAddress);
        customerPhoneNumber = findViewById(R.id.customerPhoneNumber);
        customerEmail = findViewById(R.id.customerEmail);
        btnUpdate = findViewById(R.id.btnUpdate);
        initViews();
    }

    private Retrofit config() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CustomerApi.BASE_PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public void initViews() {
        Intent intent = getIntent();
        CustomerItem customer = (CustomerItem) intent.getExtras().getSerializable("customer");
        customerId.setText(String.valueOf(customer.getId()));
        customerName.setText(customer.getName());
        customerAddress.setText(customer.getAddress());
        customerPhoneNumber.setText(customer.getPhone_number());
        customerEmail.setText(customer.getEmail());
        Toast.makeText(getApplicationContext(), "Data customer by id = " + customer.getId(), Toast.LENGTH_SHORT).show();
    }

    public void clear(View view) {
        customerId.setText("");
        customerName.setText("");
        customerAddress.setText("");
        customerPhoneNumber.setText("");
        customerEmail.setText("");
    }

    // Insert customer
    public void insertCustomer(View view) {

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomerApi request = config().create(CustomerApi.class);
                Long id = Long.parseLong(String.valueOf(customerId.getText()));
                CustomerItem customer = new CustomerItem(Long.parseLong(String.valueOf(customerId.getText())), customerName.getText().toString(),
                        customerAddress.getText().toString(), customerPhoneNumber.getText().toString(),
                        customerEmail.getText().toString());
                Call<CustomerItem> call = request.updateCustomer(id, customer);
                call.enqueue(new Callback<CustomerItem>() {
                    @Override
                    public void onResponse(Call<CustomerItem> call, Response<CustomerItem> response) {
                        UpdateActivity.super.onBackPressed();
                    }

                    @Override
                    public void onFailure(Call<CustomerItem> call, Throwable t) {
                        Log.d("Error", t.getMessage());
                    }
                });

            }
        });

    }
}
