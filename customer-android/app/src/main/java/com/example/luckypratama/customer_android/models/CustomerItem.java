package com.example.luckypratama.customer_android.models;

import java.io.Serializable;

public class CustomerItem implements Serializable{
    private Long id;
    private String name;
    private String address;
    private String phone_number;
    private String email;

    public CustomerItem(){}

    public CustomerItem(Long id, String name, String address, String phone_number, String email) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone_number = phone_number;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getEmail() {
        return email;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
