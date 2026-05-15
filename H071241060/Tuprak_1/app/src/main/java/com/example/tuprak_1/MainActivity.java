package com.example.tuprak_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView tvName, tvHeadline, tvLocation;
    private ImageButton btnEditProfile;

    private final ActivityResultLauncher<Intent> editProfileLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    String name = data.getStringExtra("name");
                    String headline = data.getStringExtra("headline");
                    String location = data.getStringExtra("location");

                    if (name != null) tvName.setText(name);
                    if (headline != null) tvHeadline.setText(headline);
                    if (location != null) tvLocation.setText(location);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        tvName = findViewById(R.id.tv_name);
        tvHeadline = findViewById(R.id.tv_headline);
        tvLocation = findViewById(R.id.tv_location);
        btnEditProfile = findViewById(R.id.btn_edit_profile);

        btnEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
            intent.putExtra("name", tvName.getText().toString());
            intent.putExtra("headline", tvHeadline.getText().toString());
            intent.putExtra("location", tvLocation.getText().toString());
            editProfileLauncher.launch(intent);
        });

    }
}
