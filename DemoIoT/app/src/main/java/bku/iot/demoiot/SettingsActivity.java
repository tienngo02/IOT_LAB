package bku.iot.demoiot;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;


import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.Map;
import java.util.Set;


public class SettingsActivity extends AppCompatActivity {

    ImageButton btnBack;
    ImageButton btnGetKey;
    EditText edtKey;
    String newKey;

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
                Intent isConnectIntent = getIntent();
                Boolean isConnect = isConnectIntent.getBooleanExtra("isConnect", false);
                newKey = String.valueOf(edtKey.getEditableText());
                if(!TextUtils.isEmpty(newKey) && !isConnect) {
                    Intent newKeyIntent = new Intent(SettingsActivity.this, MainActivity.class);
                    newKeyIntent.putExtra("newKey",newKey);
                    newKeyIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(newKeyIntent);
                }
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

