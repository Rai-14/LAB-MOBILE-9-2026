package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView tvUsernameHeader, tvName, tvBio;
    private ImageView ivProfile, ivBottomProfile;
    private Button btnEdit;
    private Uri profileImageUri;

    private final ActivityResultLauncher<Intent> editLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Intent data = result.getData();
                tvUsernameHeader.setText(data.getStringExtra("RES_USERNAME"));
                tvName.setText(data.getStringExtra("RES_NAME"));
                tvBio.setText(data.getStringExtra("RES_BIO"));

                String uriString = data.getStringExtra("RES_IMAGE_URI");
                if (uriString != null) {
                    profileImageUri = Uri.parse(uriString);
                    ivProfile.setImageURI(profileImageUri);
                    ivBottomProfile.setImageURI(profileImageUri);
                }
            }
        }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvUsernameHeader = findViewById(R.id.tv_username_header);
        tvName = findViewById(R.id.tv_name_main);
        tvBio = findViewById(R.id.tv_bio_main);
        ivProfile = findViewById(R.id.iv_profile_main);
        ivBottomProfile = findViewById(R.id.iv_bottom_profile);
        btnEdit = findViewById(R.id.btn_edit_profile);

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
            intent.putExtra("CURR_USERNAME", tvUsernameHeader.getText().toString());
            intent.putExtra("CURR_NAME", tvName.getText().toString());
            intent.putExtra("CURR_BIO", tvBio.getText().toString());
            if (profileImageUri != null) {
                intent.putExtra("CURR_IMAGE", profileImageUri.toString());
            }
            editLauncher.launch(intent);
        });
    }
}
