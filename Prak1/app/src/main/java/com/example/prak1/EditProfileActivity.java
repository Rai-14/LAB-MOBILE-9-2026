import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    EditText edtNama, edtUsername, edtBio;
    Button btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R  .layout.activity_edit_profile);

        edtNama = findViewById(R.id.edtNama);
        edtUsername = findViewById(R.id.edtUsername);
        edtBio = findViewById(R.id.edtBio);
        btnSimpan = findViewById(R.id.btnSimpan);

        // ambil data dari MainActivity
        Intent intent = getIntent();
        edtNama.setText(intent.getStringExtra("nama"));
        edtUsername.setText(intent.getStringExtra("username"));
        edtBio.setText(intent.getStringExtra("bio"));

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent result = new Intent();

                result.putExtra("nama", edtNama.getText().toString());
                result.putExtra("username", edtUsername.getText().toString());
                result.putExtra("bio", edtBio.getText().toString());

                setResult(RESULT_OK, result);
                finish();
            }
        });
    }
}