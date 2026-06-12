package com.example.finallabh071241050.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.finallabh071241050.R;
import com.example.finallabh071241050.adapter.MealAdapter;
import com.example.finallabh071241050.data.AppDatabase;
import com.example.finallabh071241050.data.MealEntity;
import com.example.finallabh071241050.model.Meal;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class ProfileFragment extends Fragment {
    private ImageView imgProfile;
    private EditText etUsername, etEmail;
    private RecyclerView rvRecent;
    private String selectedImageUri = "";
    private SharedPreferences sharedPreferences;

    private final ActivityResultLauncher<String> pickImage = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    selectedImageUri = uri.toString();
                    Glide.with(this).load(uri).into(imgProfile);
                }
            }
    );

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgProfile = view.findViewById(R.id.img_profile);
        etUsername = view.findViewById(R.id.et_username);
        etEmail = view.findViewById(R.id.et_email);
        rvRecent = view.findViewById(R.id.rv_recent_meals);
        MaterialButton btnSave = view.findViewById(R.id.btn_save);
        MaterialButton btnSettings = view.findViewById(R.id.btn_go_to_settings);
        sharedPreferences = requireActivity().getSharedPreferences("UserProfile", Context.MODE_PRIVATE);
        loadProfile();
        loadRecentMeals();
        imgProfile.setOnClickListener(v -> pickImage.launch("image/*"));
        btnSave.setOnClickListener(v -> {
            saveProfile(etUsername.getText().toString(), etEmail.getText().toString(), selectedImageUri);
            Toast.makeText(getContext(), "Profil diperbarui", Toast.LENGTH_SHORT).show();
        });
        btnSettings.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_profile_to_settings));
    }

    private void loadRecentMeals() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<MealEntity> entities = AppDatabase.getInstance(getContext()).mealDao().getRecentMeals();
            List<Meal> meals = new ArrayList<>();
            for (MealEntity entity : entities) {
                Meal meal = new Meal();
                meal.idMeal = entity.idMeal;
                meal.strMeal = entity.strMeal;
                meal.strMealThumb = entity.strMealThumb;
                meal.strCategory = entity.strCategory;
                meals.add(meal);
            }
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    MealAdapter adapter = new MealAdapter(meals);
                    rvRecent.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                    rvRecent.setAdapter(adapter);
                });
            }
        });
    }

    private void loadProfile() {
        String username = sharedPreferences.getString("username", "Pengguna Baru");
        String email = sharedPreferences.getString("email", "");
        selectedImageUri = sharedPreferences.getString("imageUri", "");
        etUsername.setText(username);
        etEmail.setText(email);
        if (!selectedImageUri.isEmpty()) Glide.with(this).load(Uri.parse(selectedImageUri)).into(imgProfile);
    }

    private void saveProfile(String username, String email, String imageUri) {
        sharedPreferences.edit()
                .putString("username", username)
                .putString("email", email)
                .putString("imageUri", imageUri)
                .apply();
    }
}