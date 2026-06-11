package com.example.finallabh071241050.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finallabh071241050.R;
import com.example.finallabh071241050.adapter.MealAdapter;
import com.example.finallabh071241050.model.MealResponse;
import com.example.finallabh071241050.network.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryDetailFragment extends Fragment {

    private RecyclerView rvMeals;
    private String categoryName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            categoryName = getArguments().getString("EXTRA_CATEGORY");
        }

        TextView tvTitle = view.findViewById(R.id.tv_title_category);
        tvTitle.setText("Kategori: " + categoryName);

        rvMeals = view.findViewById(R.id.rv_meals_category);
        rvMeals.setLayoutManager(new GridLayoutManager(getContext(), 2));

        fetchMealsByCategory(categoryName);
    }

    private void fetchMealsByCategory(String category) {
        ApiClient.getInstance().getMealsByCategory(category).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(@NonNull Call<MealResponse> call, @NonNull Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MealAdapter adapter = new MealAdapter(response.body().meals);
                    rvMeals.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(@NonNull Call<MealResponse> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Gagal memuat data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}