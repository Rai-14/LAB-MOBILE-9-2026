package com.example.finallabh071241050.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finallabh071241050.R;
import com.example.finallabh071241050.adapter.CategoryAdapter;
import com.example.finallabh071241050.model.CategoryResponse;
import com.example.finallabh071241050.network.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {

    private RecyclerView rvCategories;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvCategories = view.findViewById(R.id.rv_categories);

        // Menggunakan GridLayoutManager dengan 2 kolom
        rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 2));

        fetchCategories();
    }

    private void fetchCategories() {
        ApiClient.getInstance().getCategories().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(@NonNull Call<CategoryResponse> call, @NonNull Response<CategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CategoryAdapter adapter = new CategoryAdapter(response.body().categories);
                    rvCategories.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CategoryResponse> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Gagal memuat kategori: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}