package com.example.finallabh071241050.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import com.example.finallabh071241050.MainActivity;
import com.example.finallabh071241050.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Handler untuk delay 2 detik (2000ms)
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Intent untuk pindah Activity (Syarat Poin 2 Terpenuhi)
            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Menutup SplashScreen agar tidak bisa kembali ke sini
        }, 2000);
    }
}
