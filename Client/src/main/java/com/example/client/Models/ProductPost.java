package com.example.client.Models;

import com.google.gson.annotations.SerializedName;

public class ProductPost {
    @SerializedName("name")
    public String name;
    @SerializedName("amount")
    public int amount;
    @SerializedName("date_of_create")
    public String date_of_create;
    @SerializedName("expiration_date")
    public String expiration_date;
    @SerializedName("description")
    public String description;

    public ProductPost(String name, int amount, String date_of_create, String expiration_date, String description) {
        this.name = name;
        this.amount = amount;
        this.date_of_create = date_of_create;
        this.expiration_date = expiration_date;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate_of_create() {
        return date_of_create;
    }

    public void setDate_of_create(String date_of_create) {
        this.date_of_create = date_of_create;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
