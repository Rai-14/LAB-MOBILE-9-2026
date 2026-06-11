package com.example.finallabh071241050.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.bumptech.glide.Glide;
import com.example.finallabh071241050.R;

public class ProfileFragment extends Fragment {

    private ImageView imgProfile;
    private EditText etUsername;
    private String selectedImageUri = "";
    private SharedPreferences sharedPreferences;

    // Launcher untuk memilih gambar
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
        Button btnSave = view.findViewById(R.id.btn_save);
        Button btnSettings = view.findViewById(R.id.btn_go_to_settings);

        sharedPreferences = requireActivity().getSharedPreferences("UserProfile", Context.MODE_PRIVATE);

        // Load data saat fragment dibuka
        loadProfile();

        // Aksi pilih gambar
        imgProfile.setOnClickListener(v -> pickImage.launch("image/*"));

        // Aksi simpan
        btnSave.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            saveProfile(username, selectedImageUri);
            Toast.makeText(getContext(), "Profil disimpan!", Toast.LENGTH_SHORT).show();
        });

        // Aksi ke Settings
        btnSettings.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_profile_to_settings);
        });
    }

    private void saveProfile(String username, String imageUri) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("imageUri", imageUri);
        editor.apply();
    }

    private void loadProfile() {
        String username = sharedPreferences.getString("username", "");
        selectedImageUri = sharedPreferences.getString("imageUri", "");

        etUsername.setText(username);
        if (!selectedImageUri.isEmpty()) {
            Glide.with(this).load(Uri.parse(selectedImageUri)).into(imgProfile);
        }
    }
}