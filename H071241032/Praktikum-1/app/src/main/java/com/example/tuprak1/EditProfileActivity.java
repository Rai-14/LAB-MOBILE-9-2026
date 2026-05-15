package com.example.tuprak1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {
    private EditText etEditName, etEditUsername, etEditBio;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        etEditName = findViewById(R.id.etEditName);
        etEditUsername = findViewById(R.id.etEditUsername);
        etEditBio = findViewById(R.id.etEditBio);
        btnSave = findViewById(R.id.btnSave);

        Intent intent = getIntent();
        String CurrentName = intent.getStringExtra("EXTRA_NAME");
        String CurrentUsername = intent.getStringExtra("EXTRA_USERNAME");
        String CurrentBio = intent.getStringExtra("EXTRA_BIO");

        etEditName.setText(CurrentName);
        etEditUsername.setText(CurrentUsername);
        etEditBio.setText(CurrentBio);

        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent resultIntent = new Intent();
                resultIntent.putExtra("EXTRA_NAME",etEditName.getText().toString());
                resultIntent.putExtra("EXTRA_USERNAME",etEditUsername.getText().toString());
                resultIntent.putExtra("EXTRA_BIO",etEditBio.getText().toString());

                setResult(Activity.RESULT_OK,resultIntent);

                finish();
            }
        });
    }
}
