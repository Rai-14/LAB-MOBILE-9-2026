package com.example.finallabh071241050.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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

    private ImageView imgMeal, ivFavorite, ivBack; // Tambah ivBack
    private TextView tvName, tvInstructions, tvIngredients;
    private Meal currentMeal;
    private boolean isFavStatus = false;

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
        ivBack = view.findViewById(R.id.iv_back); // Inisialisasi ivBack

        // Logika tombol kembali
        ivBack.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());

        if (getArguments() != null) {
            String mealId = getArguments().getString("EXTRA_ID");
            if (mealId != null) {
                fetchMealDetail(mealId);
                checkFavoriteStatus(mealId);
            }
        }

        ivFavorite.setOnClickListener(v -> toggleFavorite());
    }

    // ... (fungsi lainnya seperti fetchMealDetail, checkFavoriteStatus, toggleFavorite, updateFavoriteIcon tetap sama)

    private void fetchMealDetail(String id) {
        ApiClient.getInstance().getMealDetail(id).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(@NonNull Call<MealResponse> call, @NonNull Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().meals != null) {
                    currentMeal = response.body().meals.get(0);
                    tvName.setText(currentMeal.strMeal);
                    Glide.with(DetailFragment.this).load(currentMeal.strMealThumb).into(imgMeal);
                    tvInstructions.setText(currentMeal.strInstructions);
                    tvIngredients.setText(currentMeal.getIngredients());
                }
            }
            @Override
            public void onFailure(@NonNull Call<MealResponse> call, @NonNull Throwable t) { }
        });
    }

    private void checkFavoriteStatus(String id) {
        Executors.newSingleThreadExecutor().execute(() -> {
            MealEntity entity = AppDatabase.getDatabase(getContext()).mealDao().getMealById(id);
            isFavStatus = (entity != null && entity.isFavorite);
            if (getActivity() != null) {
                getActivity().runOnUiThread(this::updateFavoriteIcon);
            }
        });
    }

    private void toggleFavorite() {
        if (currentMeal == null) return;
        isFavStatus = !isFavStatus;
        updateFavoriteIcon();

        Executors.newSingleThreadExecutor().execute(() -> {
            MealEntity entity = new MealEntity();
            entity.idMeal = currentMeal.idMeal;
            entity.strMeal = currentMeal.strMeal;
            entity.strMealThumb = currentMeal.strMealThumb;
            entity.isFavorite = isFavStatus;

            AppDatabase.getDatabase(getContext()).mealDao().insert(entity);
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