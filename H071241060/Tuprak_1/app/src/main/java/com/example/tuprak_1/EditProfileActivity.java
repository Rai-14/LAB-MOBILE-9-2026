package com.example.tuprak_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EditProfileActivity extends AppCompatActivity {

    private EditText etName, etHeadline, etLocation;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        etName = findViewById(R.id.et_edit_name);
        etHeadline = findViewById(R.id.et_edit_headline);
        etLocation = findViewById(R.id.et_edit_location);
        btnSave = findViewById(R.id.btn_save);

        Intent intent = getIntent();
        etName.setText(intent.getStringExtra("name"));
        etHeadline.setText(intent.getStringExtra("headline"));
        etLocation.setText(intent.getStringExtra("location"));

        btnSave.setOnClickListener(v -> {
            String updatedName = etName.getText().toString();
            String updatedHeadline = etHeadline.getText().toString();
            String updatedLocation = etLocation.getText().toString();

            Intent resultIntent = new Intent();
            resultIntent.putExtra("name", updatedName);
            resultIntent.putExtra("headline", updatedHeadline);
            resultIntent.putExtra("location", updatedLocation);
            setResult(RESULT_OK, resultIntent);
            finish();

        });

    }
}