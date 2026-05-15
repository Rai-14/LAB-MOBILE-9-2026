package com.example.tugas1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditProfileActivity extends AppCompatActivity {

    private EditText etName, etUsername, etBio, etInterests, etLink, etPodcast;
    private ImageView profileImage;
    private Uri selectedImageUri;

    private final ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                if (uri != null) {
                    selectedImageUri = uri;
                    profileImage.setImageURI(uri);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etName = findViewById(R.id.etName);
        etUsername = findViewById(R.id.etUsername);
        etBio = findViewById(R.id.etBio);
        etInterests = findViewById(R.id.etInterests);
        etLink = findViewById(R.id.etLink);
        etPodcast = findViewById(R.id.etPodcast);
        profileImage = findViewById(R.id.profileImage);

        // Get data from Intent
        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("name");
            String username = intent.getStringExtra("username");
            String bio = intent.getStringExtra("bio");
            String imageUriString = intent.getStringExtra("imageUri");
            
            if (name != null) etName.setText(name);
            if (username != null) etUsername.setText(username);
            if (bio != null) etBio.setText(bio);
            if (imageUriString != null) {
                selectedImageUri = Uri.parse(imageUriString);
                profileImage.setImageURI(selectedImageUri);
            }
        }

        profileImage.setOnClickListener(v -> {
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build());
        });

        findViewById(R.id.btnClose).setOnClickListener(v -> finish());
        
        findViewById(R.id.btnDone).setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("name", etName.getText().toString());
            resultIntent.putExtra("username", etUsername.getText().toString());
            resultIntent.putExtra("bio", etBio.getText().toString());
            if (selectedImageUri != null) {
                resultIntent.putExtra("imageUri", selectedImageUri.toString());
            }
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}
