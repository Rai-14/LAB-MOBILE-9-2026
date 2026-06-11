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

    // Tambahkan field ini
    public boolean isFavorite = false;
}