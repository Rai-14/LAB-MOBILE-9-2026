package com.example.tp_1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class editProfile extends AppCompatActivity {

    EditText etName, etUsername, etPronouns, etBio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ImageView back = findViewById(R.id.backBtn);
        back.setOnClickListener(v -> finish());

        etName = findViewById(R.id.etName);
        etUsername = findViewById(R.id.etUsername);
        etPronouns = findViewById(R.id.etPronouns);
        etBio = findViewById(R.id.etBio);

        Intent intent = getIntent();

        etName.setText(intent.getStringExtra("name"));
        etUsername.setText(intent.getStringExtra("username"));
        etPronouns.setText(intent.getStringExtra("pronouns"));
        etBio.setText(intent.getStringExtra("bio"));
    }

    public void saveData (View view) {
        Intent intent = new Intent();

        intent.putExtra("name",etName.getText().toString());
        intent.putExtra("username",etUsername.getText().toString());
        intent.putExtra("pronouns",etPronouns.getText().toString());
        intent.putExtra("bio",etBio.getText().toString());

        if(selectedImageUri != null) {
            intent.putExtra("image",selectedImageUri.toString());
        }

        setResult(RESULT_OK, intent);
        finish();
    }
    Uri selectedImageUri;
    ActivityResultLauncher<String> pickImage =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    selectedImageUri = uri;
                    ImageView img = findViewById(R.id.imgProfile);
                    img.setImageURI(uri);
                }
            });

    public void changePhoto(View view) {
        pickImage.launch("image/*");
    }







}