package com.example.finallabh071241050.network;

import com.example.finallabh071241050.model.CategoryResponse;
import com.example.finallabh071241050.model.MealResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api/json/v1/1/search.php?s=")
    Call<MealResponse> getMeals();

    @GET("api/json/v1/1/lookup.php")
    Call<MealResponse> getMealDetail(@Query("i") String id);

    @GET("api/json/v1/1/list.php?c=list")
    Call<CategoryResponse> getCategories();

    @GET("api/json/v1/1/filter.php")
    Call<MealResponse> getMealsByCategory(@Query("c") String category);

    // Pencarian berdasarkan nama (paling umum digunakan untuk search bar)
    @GET("api/json/v1/1/search.php")
    Call<MealResponse> searchMeals(@Query("s") String query);

    // Pencarian berdasarkan bahan
    @GET("api/json/v1/1/filter.php")
    Call<MealResponse> searchMealsByIngredient(@Query("i") String ingredient);
}