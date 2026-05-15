package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {
    private EditText etUser, etName, etBio;
    private ImageView ivEditProfile;
    private Button btnChangeImage, btnSave;
    private Uri selectedImageUri;

    private final ActivityResultLauncher<String> getContent = registerForActivityResult(
        new ActivityResultContracts.GetContent(),
        uri -> {
            if (uri != null) {
                selectedImageUri = uri;
                ivEditProfile.setImageURI(uri);
            }
        }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etUser = findViewById(R.id.et_new_username);
        etName = findViewById(R.id.et_new_name);
        etBio = findViewById(R.id.et_new_bio);
        ivEditProfile = findViewById(R.id.iv_edit_profile);
        btnChangeImage = findViewById(R.id.btn_change_image);
        btnSave = findViewById(R.id.btn_save_profile);

        if (getIntent() != null) {
            etUser.setText(getIntent().getStringExtra("CURR_USERNAME"));
            etName.setText(getIntent().getStringExtra("CURR_NAME"));
            etBio.setText(getIntent().getStringExtra("CURR_BIO"));

            String uriString = getIntent().getStringExtra("CURR_IMAGE");

            if (uriString != null) {
                selectedImageUri = Uri.parse(uriString);
                ivEditProfile.setImageURI(selectedImageUri);
            }
        }

        btnChangeImage.setOnClickListener(v -> getContent.launch("image/*"));

        btnSave.setOnClickListener(v -> {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("RES_USERNAME", etUser.getText().toString());
            returnIntent.putExtra("RES_NAME", etName.getText().toString());
            returnIntent.putExtra("RES_BIO", etBio.getText().toString());
            if (selectedImageUri != null) {
                returnIntent.putExtra("RES_IMAGE_URI", selectedImageUri.toString());
            }
            setResult(RESULT_OK, returnIntent);
            finish();
        });
    }
}
