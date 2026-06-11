package com.example.finallabh071241050.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface MealDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<MealEntity> meals);

    // Tetap gunakan REPLACE untuk favorit agar saat diklik bisa toggle true/false
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MealEntity meal);

    // Tambahkan method ini jika sewaktu-waktu ingin update data spesifik
    @Update
    void update(MealEntity meal);

    @Query("SELECT * FROM meal_table")
    List<MealEntity> getAllMeals();

    @Query("SELECT * FROM meal_table WHERE isFavorite = 1")
    List<MealEntity> getFavoriteMeals();

    @Query("SELECT * FROM meal_table WHERE idMeal = :id LIMIT 1")
    MealEntity getMealById(String id);
}