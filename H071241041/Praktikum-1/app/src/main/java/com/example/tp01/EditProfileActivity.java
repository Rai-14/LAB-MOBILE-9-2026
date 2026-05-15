package com.example.tp01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    private EditText etName, etUsername, etBio;
    private ImageView ivEditProfile;
    private Button btnSave, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etName = findViewById(R.id.etName);
        etUsername = findViewById(R.id.etUsername);
        etBio = findViewById(R.id.etBio);
        ivEditProfile = findViewById(R.id.ivEditProfile);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        Intent intent = getIntent();
        String oldName = intent.getStringExtra("EXTRA_NAME");
        String oldUsername = intent.getStringExtra("EXTRA_USERNAME");
        String oldBio = intent.getStringExtra("EXTRA_BIO");

        etName.setText(oldName);
        etUsername.setText(oldUsername);
        etBio.setText(oldBio);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = etName.getText().toString();
                String newUsername = etUsername.getText().toString();
                String newBio = etBio.getText().toString();

                Intent resultIntent = new Intent();
                resultIntent.putExtra("EXTRA_NAME", newName);
                resultIntent.putExtra("EXTRA_USERNAME", newUsername);
                resultIntent.putExtra("EXTRA_BIO", newBio);

                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}