package com.example.tp_1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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

    TextView disName, disUsername, disPronouns, disBio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        disName = findViewById(R.id.disName);
        disUsername = findViewById(R.id.disUsername);
        disPronouns = findViewById(R.id.disPronouns);
        disBio = findViewById(R.id.disBio);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void goToEditProfile (View view) {
        Intent intent = new Intent(MainActivity.this, editProfile.class);

        intent.putExtra("name",disName.getText().toString());
        intent.putExtra("username",disUsername.getText().toString());
        intent.putExtra("pronouns",disPronouns.getText().toString());
        intent.putExtra("bio",disBio.getText().toString());

        launcher.launch(intent);
    }

    ActivityResultLauncher<Intent> launcher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {

                    Intent data = result.getData();

                    String name = data.getStringExtra("name");
                    String username = data.getStringExtra("username");
                    String pronouns = data.getStringExtra("pronouns");
                    String bio = data.getStringExtra("bio");
                    String image = data.getStringExtra("image");

                    if (image != null) {
                        Uri uri = Uri.parse(image);

                        ImageView img = findViewById(R.id.profileImage);
                        img.setImageURI(uri);
                    }

                    disName.setText(name);
                    disUsername.setText(username);
                    disPronouns.setText(pronouns);
                    disBio.setText(bio);
                }

            });
}