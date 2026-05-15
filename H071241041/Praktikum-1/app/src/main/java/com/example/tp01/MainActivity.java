package com.example.tp01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvUsername, tvName, tvBio, tvPosts, tvFollowers, tvFollowing;
    private ImageView ivProfile;
    private Button btnEditProfile;

    private String currentName = "rikiakugua";
    private String currentUsername = "rikirizz";
    private String currentBio = "gotcha";
    private String currentPosts = "3";
    private String currentFollowers = "2M";
    private String currentFollowing = "0";

    private final ActivityResultLauncher<Intent> editProfileLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        currentName = data.getStringExtra("EXTRA_NAME");
                        currentUsername = data.getStringExtra("EXTRA_USERNAME");
                        currentBio = data.getStringExtra("EXTRA_BIO");

                        updateUI();
                        Toast.makeText(this, "Profil berhasil diperbarui", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvUsername = findViewById(R.id.tvUsername);
        tvName = findViewById(R.id.tvName);
        tvBio = findViewById(R.id.tvBio);
        tvPosts = findViewById(R.id.tvPosts);
        tvFollowers = findViewById(R.id.tvFollowers);
        tvFollowing = findViewById(R.id.tvFollowing);
        ivProfile = findViewById(R.id.ivProfile);
        btnEditProfile = findViewById(R.id.btnEditProfile);

        updateUI();

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
                intent.putExtra("EXTRA_NAME", currentName);
                intent.putExtra("EXTRA_USERNAME", currentUsername);
                intent.putExtra("EXTRA_BIO", currentBio);

                editProfileLauncher.launch(intent);
            }
        });
    }

    private void updateUI() {
        tvUsername.setText(currentUsername);
        tvName.setText(currentName);
        tvBio.setText(currentBio);
        tvPosts.setText(currentPosts);
        tvFollowers.setText(currentFollowers);
        tvFollowing.setText(currentFollowing);
    }
}