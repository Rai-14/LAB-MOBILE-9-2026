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
    public String strInstructions;
    public String strIngredients;

    public String strCategory;

    public boolean isFavorite = false;
    public boolean isTried = false;
    public long lastViewed = 0;
}