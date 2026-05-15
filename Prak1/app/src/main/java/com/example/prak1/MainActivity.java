import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView txtNama, txtUsername, txtBio;
    Button btnEdit;
    Button btnShare;
    ImageView imgProfile;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNama = findViewById(R.id.txtNama);
        txtUsername = findViewById(R.id.txtUsername);
        txtBio = findViewById(R.id.txtBio);
        btnEdit = findViewById(R.id.btnEdit);
        btnShare = findViewById(R.id.btnShare);
        imgProfile = findViewById(R.id.imgProfile);

        // INIT PREFERENCES
        preferences = getSharedPreferences("profile", MODE_PRIVATE);

        // LOAD FOTO SAAT APP DIBUKA
        String savedImage = preferences.getString("image", null);
        if (savedImage != null) {
            imgProfile.setImageURI(Uri.parse(savedImage));
        }

        // EDIT PROFILE
        btnEdit.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);

            intent.putExtra("nama", txtNama.getText().toString());
            intent.putExtra("username", txtUsername.getText().toString());
            intent.putExtra("bio", txtBio.getText().toString());

            startActivityForResult(intent, 1);
        });

        // SHARE
        btnShare.setOnClickListener(view -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT,
                    "Cek profil saya: " + txtUsername.getText().toString());

            startActivity(Intent.createChooser(shareIntent, "Bagikan via"));
        });

        // PILIH FOTO
        imgProfile.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 2);
        });

        imgProfile.setOnLongClickListener(view -> {

            // hapus dari SharedPreferences
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("image");
            editor.apply();

            // kembali ke foto default
            imgProfile.setImageResource(R.mipmap.ic_launcher);

            return true;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // EDIT PROFILE
        if (requestCode == 1 && resultCode == RESULT_OK) {
            txtNama.setText(data.getStringExtra("nama"));
            txtUsername.setText(data.getStringExtra("username"));
            txtBio.setText(data.getStringExtra("bio"));
        }

        // FOTO
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            imgProfile.setImageURI(selectedImage);

            // SIMPAN URI
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("image", selectedImage.toString());
            editor.apply();
        }
    }
}