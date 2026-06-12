package com.example.finallabh071241050.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.google.android.material.snackbar.Snackbar;
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
    private Button btnSearch;
    private LinearLayout layoutError;
    private Button btnRetry;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvMeals = view.findViewById(R.id.rv_meals);
        rvMeals.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MealAdapter(new ArrayList<>());
        rvMeals.setAdapter(adapter);

        swipeRefresh = view.findViewById(R.id.swipe_refresh);
        etSearch = view.findViewById(R.id.et_search);
        btnSearch = view.findViewById(R.id.btn_search);
        layoutError = view.findViewById(R.id.layout_error);
        btnRetry = view.findViewById(R.id.btn_retry);

        swipeRefresh.setOnRefreshListener(this::fetchMeals);
        btnRetry.setOnClickListener(v -> fetchMeals());
        btnSearch.setOnClickListener(v -> {
            String query = etSearch.getText().toString().trim();
            if (!query.isEmpty()) performRemoteSearch(query);
            else fetchMeals();
        });
        fetchMeals();
    }

    private void performRemoteSearch(String query) {
        swipeRefresh.setRefreshing(true);
        ApiClient.getInstance().searchMealsByIngredient(query).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(@NonNull Call<MealResponse> call, @NonNull Response<MealResponse> response) {
                swipeRefresh.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null && response.body().meals != null) {
                    layoutError.setVisibility(View.GONE);
                    rvMeals.setVisibility(View.VISIBLE);
                    adapter.updateData(response.body().meals);
                } else {
                    layoutError.setVisibility(View.VISIBLE);
                    rvMeals.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<MealResponse> call, @NonNull Throwable t) {
                swipeRefresh.setRefreshing(false);
                layoutError.setVisibility(View.VISIBLE);
                rvMeals.setVisibility(View.GONE);
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
                    layoutError.setVisibility(View.GONE);
                    rvMeals.setVisibility(View.VISIBLE);
                    adapter.updateData(response.body().meals);
                    saveToDatabase(response.body().meals);
                }
            }
            @Override
            public void onFailure(@NonNull Call<MealResponse> call, @NonNull Throwable t) {
                swipeRefresh.setRefreshing(false);
                layoutError.setVisibility(View.VISIBLE);
                rvMeals.setVisibility(View.GONE);
                Snackbar.make(requireView(), "Mode Offline", Snackbar.LENGTH_LONG).show();
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
            if (getContext() != null) AppDatabase.getInstance(getContext()).mealDao().insertAll(entities);
        });
    }

    private void loadFromDatabase() {
        Executors.newSingleThreadExecutor().execute(() -> {
            if (getContext() == null) return;
            List<MealEntity> localData = AppDatabase.getInstance(getContext()).mealDao().getAllMeals();
            List<Meal> mealList = new ArrayList<>();
            for (MealEntity e : localData) {
                Meal m = new Meal();
                m.idMeal = e.idMeal;
                m.strMeal = e.strMeal;
                m.strMealThumb = e.strMealThumb;
                mealList.add(m);
            }
            if (getActivity() != null) getActivity().runOnUiThread(() -> adapter.updateData(mealList));
        });
    }
}