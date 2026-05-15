package com.example.tp_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class EditIntroActivity extends AppCompatActivity {

    private EditText etNamaDepan, etNamaBelakang;
    private Button btnSimpan;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_intro);

        etNamaDepan = findViewById(R.id.etNamaDepan);
        etNamaBelakang = findViewById(R.id.etNamaBelakang);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnBack = findViewById(R.id.btnBack);

        // Tombol kembali (Back)
        btnBack.setOnClickListener(v -> finish());

        // Tombol Simpan
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaDepan = etNamaDepan.getText().toString();
                String namaBelakang = etNamaBelakang.getText().toString();

                Intent resultIntent = new Intent();

                resultIntent.putExtra("namaDepan", namaDepan);
                resultIntent.putExtra("namaBelakang", namaBelakang);

                setResult(RESULT_OK, resultIntent);
                finish();

            }
        });
    }
}