package bku.iot.demoiot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.Charset;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;


public class MainActivity extends AppCompatActivity {

    MQTTHelper mqttHelper;
    TextView txtTemp, txtLig, txtHumi;
    LabeledSwitch btnLED, btnPUMP;
    ImageButton btnSettings;

    String[] array_feed_url = {"https://io.adafruit.com/api/v2/nvtien/feeds/sensor1/data",
                               "https://io.adafruit.com/api/v2/nvtien/feeds/sensor2/data",
                               "https://io.adafruit.com/api/v2/nvtien/feeds/sensor3/data",
                               "https://io.adafruit.com/api/v2/nvtien/feeds/button1/data",
                               "https://io.adafruit.com/api/v2/nvtien/feeds/button2/data",};

    String[] array_output_file = {"sensor1_data.json", "sensor2_data.json", "sensor3_data.json",
                                  "button1_data.json", "button2_data.json"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTemp = findViewById(R.id.txtTemperature);
        txtLig = findViewById(R.id.txtLight);
        txtHumi = findViewById(R.id.txtHumidity);
        btnLED = findViewById(R.id.btnLED);
        btnPUMP = findViewById(R.id.btnPUMP);
        btnSettings = findViewById(R.id.btnSettings);

        btnLED.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(isOn == true){
                    sendDataMQTT("nvtien/feeds/button1","1");
                }
                else{
                    sendDataMQTT("nvtien/feeds/button1","0");
                }
            }
        });

        btnPUMP.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(isOn == true){
                    sendDataMQTT("nvtien/feeds/button2","1");
                }
                else{
                    sendDataMQTT("nvtien/feeds/button2","0");
                }
            }
        });

        startMQTT();

    }

    public void sendDataMQTT(String topic, String value){
        MqttMessage msg = new MqttMessage();
        msg.setId(1234);
        msg.setQos(0);
        msg.setRetained(false);

        byte[] b = value.getBytes(Charset.forName("UTF-8"));
        msg.setPayload(b);

        try {
            mqttHelper.mqttAndroidClient.publish(topic, msg);
        }
        catch (MqttException e){
        }
    }

    public void startMQTT(){
        mqttHelper = new MQTTHelper(this);
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                btnLED.setEnabled(true);
                btnPUMP.setEnabled(true);
                Log.d("TEST", "connectComplete!");
            }

            @Override
            public void connectionLost(Throwable cause) {
                btnLED.setEnabled(false);
                btnPUMP.setEnabled(false);
                Log.d("TEST", "connectionLost!");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.d("TEST", topic + "***" + message.toString());
                if(topic.contains("sensor1")){
                    txtTemp.setText(message.toString()+ "°C");
                }
                else if(topic.contains("sensor2")){
                    txtLig.setText(message.toString()+ "lux");
                }
                else if(topic.contains("sensor3")){
                    txtHumi.setText(message.toString()+ "%");
                }
                else if(topic.contains("button1")){
                    if(message.toString().equals("1")){
                        btnLED.setOn(true);
                    }
                    else{
                        btnLED.setOn(false);
                    }
                }
                else if(topic.contains("button2")){
                    if(message.toString().equals("1")){
                        btnPUMP.setOn(true);
                    }
                    else{
                        btnPUMP.setOn(false);
                    }
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }


}