package com.example.finallabh071241050.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.finallabh071241050.R;
import com.example.finallabh071241050.data.AppDatabase;
import com.example.finallabh071241050.data.MealEntity;
import com.example.finallabh071241050.model.Meal;
import com.example.finallabh071241050.model.MealResponse;
import com.example.finallabh071241050.network.ApiClient;

import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailFragment extends Fragment {

    private ImageView imgMeal, ivFavorite, ivBack;
    private TextView tvName, tvInstructions, tvIngredients;
    private Meal currentMeal;
    private boolean isFavStatus = false;
    private String mealId; // Simpan ID sebagai variabel global

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgMeal = view.findViewById(R.id.img_detail_meal);
        tvName = view.findViewById(R.id.tv_detail_name);
        tvInstructions = view.findViewById(R.id.tv_detail_instructions);
        tvIngredients = view.findViewById(R.id.tv_detail_ingredients);
        ivFavorite = view.findViewById(R.id.iv_favorite);
        ivBack = view.findViewById(R.id.iv_back);

        ivBack.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());

        if (getArguments() != null) {
            mealId = getArguments().getString("EXTRA_ID");
            if (mealId != null) {
                fetchMealDetail(mealId);
            }
        }

        ivFavorite.setOnClickListener(v -> toggleFavorite());
    }

    private void fetchMealDetail(String id) {
        ApiClient.getInstance().getMealDetail(id).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(@NonNull Call<MealResponse> call, @NonNull Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().meals != null) {
                    currentMeal = response.body().meals.get(0);
                    displayData(currentMeal);
                    saveDetailToDatabase(currentMeal); // Simpan ke DB saat Online
                    checkFavoriteStatus(id); // Cek status fav setelah data dimuat
                }
            }

            @Override
            public void onFailure(@NonNull Call<MealResponse> call, @NonNull Throwable t) {
                // JIKA GAGAL/OFFLINE: Load dari Database
                loadDetailFromDatabase(id);
            }
        });
    }

    private void displayData(Meal meal) {
        tvName.setText(meal.strMeal);
        tvInstructions.setText(meal.strInstructions);
        tvIngredients.setText(meal.getIngredients());
        Glide.with(this).load(meal.strMealThumb).into(imgMeal);
    }

    // --- FUNGSI OFFLINE/DATABASE ---

    private void saveDetailToDatabase(Meal meal) {
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase db = AppDatabase.getDatabase(getContext());
            MealEntity existing = db.mealDao().getMealById(meal.idMeal);

            MealEntity entity = new MealEntity();
            entity.idMeal = meal.idMeal;
            entity.strMeal = meal.strMeal;
            entity.strMealThumb = meal.strMealThumb;
            entity.strInstructions = meal.strInstructions;
            entity.strIngredients = meal.getIngredients();

            // Jaga status favorit tetap ada
            entity.isFavorite = (existing != null && existing.isFavorite);

            db.mealDao().insert(entity);
        });
    }

    private void loadDetailFromDatabase(String id) {
        Executors.newSingleThreadExecutor().execute(() -> {
            MealEntity entity = AppDatabase.getDatabase(getContext()).mealDao().getMealById(id);
            if (entity != null && getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    tvName.setText(entity.strMeal);
                    tvInstructions.setText(entity.strInstructions);
                    tvIngredients.setText(entity.strIngredients);
                    Glide.with(this).load(entity.strMealThumb).into(imgMeal);
                    isFavStatus = entity.isFavorite;
                    updateFavoriteIcon();
                    Toast.makeText(getContext(), "Mode Offline: Menampilkan data tersimpan", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void checkFavoriteStatus(String id) {
        Executors.newSingleThreadExecutor().execute(() -> {
            MealEntity entity = AppDatabase.getDatabase(getContext()).mealDao().getMealById(id);
            if (entity != null) {
                isFavStatus = entity.isFavorite;
                if (getActivity() != null) getActivity().runOnUiThread(this::updateFavoriteIcon);
            }
        });
    }

    private void toggleFavorite() {
        if (currentMeal == null) return;
        isFavStatus = !isFavStatus;
        updateFavoriteIcon();

        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase db = AppDatabase.getDatabase(getContext());
            MealEntity entity = db.mealDao().getMealById(currentMeal.idMeal);

            if (entity == null) {
                entity = new MealEntity();
                entity.idMeal = currentMeal.idMeal;
                entity.strMeal = currentMeal.strMeal;
                entity.strMealThumb = currentMeal.strMealThumb;
                entity.strInstructions = currentMeal.strInstructions;
                entity.strIngredients = currentMeal.getIngredients();
            }
            entity.isFavorite = isFavStatus;
            db.mealDao().insert(entity);
        });
    }

    private void updateFavoriteIcon() {
        if (isFavStatus) {
            ivFavorite.setImageResource(R.drawable.ic_favorite_filled);
        } else {
            ivFavorite.setImageResource(R.drawable.ic_favorite_border);
        }
    }
}