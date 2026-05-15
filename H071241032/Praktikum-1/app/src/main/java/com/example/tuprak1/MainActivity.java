package com.example.tuprak1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvName, tvUsername, tvBio;
    private Button btnEditProfile;


    private final ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {

                        tvName.setText(data.getStringExtra("EXTRA_NAME"));
                        tvUsername.setText(data.getStringExtra("EXTRA_USERNAME"));
                        tvBio.setText(data.getStringExtra("EXTRA_BIO"));
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvName = findViewById(R.id.tvName);
        tvUsername = findViewById(R.id.tvUsername);
        tvBio = findViewById(R.id.tvBio);
        btnEditProfile = findViewById(R.id.btnEditProfile);

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
                intent.putExtra("EXTRA_NAME", tvName.getText().toString());
                intent.putExtra("EXTRA_USERNAME", tvUsername.getText().toString());
                intent.putExtra("EXTRA_BIO", tvBio.getText().toString());


                resultLauncher.launch(intent);
            }
        });
    }
}