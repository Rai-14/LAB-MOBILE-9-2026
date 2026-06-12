package com.example.finallabh071241050.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "meal_table")
public class MealEntity {
    @PrimaryKey
    @NonNull
    public String idMeal;
    public String strMeal;
    public String strMealThumb;

    // Field baru untuk menyimpan detail agar bisa diakses offline
    public String strInstructions;
    public String strIngredients;

    public boolean isFavorite = false;
}