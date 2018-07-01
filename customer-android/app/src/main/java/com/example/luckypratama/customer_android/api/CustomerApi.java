package com.example.luckypratama.customer_android.api;

import com.example.luckypratama.customer_android.models.CustomerItem;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.ArrayList;

public interface CustomerApi {

//  Change the ip to yours
    String BASE_PATH = "http://192.168.0.8:8090/";

//    Get customer list
    @GET("customers")
    Call<ArrayList<CustomerItem>> getCustomers();

//    Get single object of cutomer
    @GET("customers/{id}")
    Call<CustomerItem> getCustomer(@Path("id") long customerID);

//    Insert customer
    @POST("customers")
    Call<CustomerItem> insertCustomer(@Body CustomerItem customer);

//    Update customer
    @PUT("customers/{id}")
    Call<CustomerItem> updateCustomer(@Path("id")long customerID, @Body CustomerItem customer);

//    Delete customer
    @DELETE("customers/{id}")
    Call<CustomerItem> deleteCustomer(@Path("id")long customerID);
}
