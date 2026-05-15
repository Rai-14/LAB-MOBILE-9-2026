package com.example.tugas1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView tvName, tvUsername, tvBio, postUser1, postUser2;
    private ImageView ivProfileMain, postAvatar1, postAvatar2;
    private Uri profileImageUri;

    private final ActivityResultLauncher<Intent> editProfileLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    String newName = result.getData().getStringExtra("name");
                    String newUsername = result.getData().getStringExtra("username");
                    String newBio = result.getData().getStringExtra("bio");
                    String newImageUri = result.getData().getStringExtra("imageUri");
                    
                    if (newName != null) {
                        tvName.setText(newName);
                    }
                    if (newUsername != null) {
                        tvUsername.setText(newUsername);
                        if (postUser1 != null) postUser1.setText(newUsername);
                        if (postUser2 != null) postUser2.setText(newUsername);
                    }
                    if (newBio != null) {
                        tvBio.setText(newBio);
                    }
                    if (newImageUri != null) {
                        profileImageUri = Uri.parse(newImageUri);
                        ivProfileMain.setImageURI(profileImageUri);
                        if (postAvatar1 != null) postAvatar1.setImageURI(profileImageUri);
                        if (postAvatar2 != null) postAvatar2.setImageURI(profileImageUri);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        tvName = findViewById(R.id.tvName);
        tvUsername = findViewById(R.id.tvUsername);
        tvBio = findViewById(R.id.tvBio);
        ivProfileMain = findViewById(R.id.ivProfileMain);
        postAvatar1 = findViewById(R.id.postAvatar1);
        postAvatar2 = findViewById(R.id.postAvatar2);
        postUser1 = findViewById(R.id.postUser1);
        postUser2 = findViewById(R.id.postUser2);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.btnEditProfile).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
            intent.putExtra("name", tvName.getText().toString());
            intent.putExtra("username", tvUsername.getText().toString());
            intent.putExtra("bio", tvBio.getText().toString());
            if (profileImageUri != null) {
                intent.putExtra("imageUri", profileImageUri.toString());
            }
            editProfileLauncher.launch(intent);
        });
    }
}