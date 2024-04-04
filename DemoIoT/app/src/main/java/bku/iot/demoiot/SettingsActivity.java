package bku.iot.demoiot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.textfield.TextInputLayout;

public class SettingsActivity extends AppCompatActivity {

    ImageButton btnBack;
    ImageButton btnGetKey;
    EditText edtKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btnBack = findViewById(R.id.btnBack);
        btnGetKey = findViewById(R.id.btnGetKey);
        edtKey = findViewById(R.id.edtKey);

        btnGetKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newKeyIntent = new Intent(SettingsActivity.this,MainActivity.class);
                String newKey = String.valueOf(edtKey.getEditableText());
                Log.d("TEST", newKey);
//                Bundle newKeyBundle = new Bundle();
//                newKeyBundle.putString("newKey",newKey);
                newKeyIntent.putExtra("newKey",newKey);
                startActivity(newKeyIntent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}