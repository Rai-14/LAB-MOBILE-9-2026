package com.example.finallabh071241050.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finallabh071241050.R;
import com.example.finallabh071241050.adapter.MealAdapter;
import com.example.finallabh071241050.data.AppDatabase;
import com.example.finallabh071241050.data.MealEntity;
import com.example.finallabh071241050.model.Meal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class FavoriteFragment extends Fragment {

    private RecyclerView rvFavorite;
    private MealAdapter adapter;
    private TextView tvEmpty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvFavorite = view.findViewById(R.id.rv_favorite);
        tvEmpty = view.findViewById(R.id.tv_empty);

        rvFavorite.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inisialisasi Adapter dengan list kosong
        adapter = new MealAdapter(new ArrayList<>());
        rvFavorite.setAdapter(adapter);
    }

    // Gunakan onResume agar data diperbarui setiap kali fragment dibuka kembali
    @Override
    public void onResume() {
        super.onResume();
        loadFavorites();
    }

    private void loadFavorites() {
        Executors.newSingleThreadExecutor().execute(() -> {
            if (getContext() == null) return;

            // MENGGUNAKAN getFavoriteMeals() agar hanya yang favorit yang muncul
            List<MealEntity> localData = AppDatabase.getDatabase(getContext()).mealDao().getFavoriteMeals();
            List<Meal> mealList = new ArrayList<>();

            // Mapping MealEntity ke Meal agar bisa dibaca Adapter
            for (MealEntity e : localData) {
                Meal m = new Meal();
                m.idMeal = e.idMeal;
                m.strMeal = e.strMeal;
                m.strMealThumb = e.strMealThumb;
                mealList.add(m);
            }

            // Update UI di Main Thread
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    if (mealList.isEmpty()) {
                        tvEmpty.setVisibility(View.VISIBLE);
                        rvFavorite.setVisibility(View.GONE);
                    } else {
                        tvEmpty.setVisibility(View.GONE);
                        rvFavorite.setVisibility(View.VISIBLE);
                        adapter.updateData(mealList);
                    }
                });
            }
        });
    }
}