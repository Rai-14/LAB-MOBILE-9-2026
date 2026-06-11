package com.example.finallabh071241050.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.finallabh071241050.R;
import com.example.finallabh071241050.adapter.MealAdapter;
import com.example.finallabh071241050.data.AppDatabase;
import com.example.finallabh071241050.data.MealEntity;
import com.example.finallabh071241050.model.Meal;
import com.example.finallabh071241050.model.MealResponse;
import com.example.finallabh071241050.network.ApiClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private RecyclerView rvMeals;
    private MealAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;
    private EditText etSearch;

    private final Handler searchHandler = new Handler(Looper.getMainLooper());
    private Runnable searchRunnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvMeals = view.findViewById(R.id.rv_meals);

        // PERUBAHAN UTAMA DI SINI: Menggunakan VERTICAL agar scroll ke bawah
        rvMeals.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        adapter = new MealAdapter(new ArrayList<>());
        rvMeals.setAdapter(adapter);

        swipeRefresh = view.findViewById(R.id.swipe_refresh);
        etSearch = view.findViewById(R.id.et_search);

        swipeRefresh.setOnRefreshListener(this::fetchMeals);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchHandler.removeCallbacks(searchRunnable);
            }

            @Override
            public void afterTextChanged(Editable s) {
                String query = s.toString().trim();
                if (query.isEmpty()) {
                    fetchMeals();
                } else {
                    searchRunnable = () -> performRemoteSearch(query);
                    searchHandler.postDelayed(searchRunnable, 500);
                }
            }
        });

        fetchMeals();
    }

    private void performRemoteSearch(String query) {
        swipeRefresh.setRefreshing(true);
        ApiClient.getInstance().searchMealsByIngredient(query).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(@NonNull Call<MealResponse> call, @NonNull Response<MealResponse> response) {
                swipeRefresh.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    List<Meal> meals = response.body().meals;
                    if (meals != null) {
                        adapter.updateData(meals);
                    } else {
                        adapter.updateData(new ArrayList<>());
                        Toast.makeText(getContext(), "Tidak ditemukan hasil", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MealResponse> call, @NonNull Throwable t) {
                swipeRefresh.setRefreshing(false);
                Toast.makeText(getContext(), "Gagal mencari: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchMeals() {
        swipeRefresh.setRefreshing(true);
        ApiClient.getInstance().getMeals().enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(@NonNull Call<MealResponse> call, @NonNull Response<MealResponse> response) {
                swipeRefresh.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null && response.body().meals != null) {
                    adapter.updateData(response.body().meals);
                    saveToDatabase(response.body().meals);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MealResponse> call, @NonNull Throwable t) {
                swipeRefresh.setRefreshing(false);
                loadFromDatabase();
            }
        });
    }

    private void saveToDatabase(List<Meal> meals) {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<MealEntity> entities = new ArrayList<>();
            for (Meal m : meals) {
                MealEntity entity = new MealEntity();
                entity.idMeal = m.idMeal;
                entity.strMeal = m.strMeal;
                entity.strMealThumb = m.strMealThumb;
                entities.add(entity);
            }
            if (getContext() != null) {
                AppDatabase.getDatabase(getContext()).mealDao().insertAll(entities);
            }
        });
    }

    private void loadFromDatabase() {
        Executors.newSingleThreadExecutor().execute(() -> {
            if (getContext() == null) return;
            List<MealEntity> localData = AppDatabase.getDatabase(getContext()).mealDao().getAllMeals();
            List<Meal> mealList = new ArrayList<>();
            for (MealEntity e : localData) {
                Meal m = new Meal();
                m.idMeal = e.idMeal;
                m.strMeal = e.strMeal;
                m.strMealThumb = e.strMealThumb;
                mealList.add(m);
            }
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> adapter.updateData(mealList));
            }
        });
    }
}