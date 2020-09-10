package com.example.formula1.api;

import com.example.formula1.imageobject.Item;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageApiResponse {

    @SerializedName("items")
    @Expose
    private List<Item> itemsList;

    public List<Item> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<Item> itemsList) {
        this.itemsList = itemsList;
    }
}
