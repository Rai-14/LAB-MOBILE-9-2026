package com.example.finallabh071241050.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CategoryResponse {
    @SerializedName("meals")
    public List<Category> categories;
}