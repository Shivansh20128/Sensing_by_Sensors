package com.mc2023.template;
import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class MainActivity extends AppCompatActivity {
    TextView liveFeedback;
    TextView liveFeedbackDirection;
    ToggleButton proximityToggle;
    ToggleButton lightToggle;
    ToggleButton rotationVectorToggle;
    SensorManager sensorManager;
    Sensor proximitySensor;
    Sensor lightSensor;
    Sensor rotationVectorSensor;

    MainDatabase myDatabase;

    static int x=0;
    static int y=0;
    static int z=0;
    static float prox=0;
    static int acc=0;
    static float x_cor=0;
    static float y_cor=0;
    static float z_cor=0;
    static float scalar_cor=0;
    static float some_cor=0;
    static int acc_grv=0;
    static float inten=0;
    static int acc_light=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        liveFeedback = findViewById(R.id.rotationDegrees);
        liveFeedbackDirection = findViewById(R.id.rotationDirection);
        proximityToggle = findViewById(R.id.proximityToggle);
        lightToggle = findViewById(R.id.lightToggle);
        rotationVectorToggle = findViewById(R.id.rotationVectorToggle);

        myDatabase = Room.databaseBuilder(getApplicationContext(),MainDatabase.class,"myDatabase").allowMainThreadQueries().fallbackToDestructiveMigration().build();

        myDatabase.Dao_proximity().deleteAllProximityData();
        myDatabase.Dao_grv().deleteAllGRVData();
        myDatabase.Dao_light().deleteAllLightData();

        proximityToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
                if(isChecked) {
                    Log.d("sensor on","Light sensor turned on");
                    if (proximitySensor == null) {
                        Toast.makeText(buttonView.getContext(), "No proximity sensor found in device.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        // registering our sensor with sensor manager.
                        sensorManager.registerListener(proximitySensorEventListener, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }
                }
                else
                {
                    Log.d("sensor off","Proximity sensor turned off");
                    sensorManager.unregisterListener(proximitySensorEventListener,proximitySensor);
                    Toast.makeText(buttonView.getContext(), "Proximity sensor turned off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        lightToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
                if(isChecked) {
                    Log.d("sensor on","Light sensor turned on");
                    if (lightSensor == null) {
                        Toast.makeText(buttonView.getContext(), "No light sensor found in device.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        // registering our sensor with sensor manager.
                        sensorManager.registerListener(lightSensorEventListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }
                }
                else
                {
                    Log.d("sensor off","Light sensor turned off");
                    sensorManager.unregisterListener(lightSensorEventListener,lightSensor);
                    Toast.makeText(buttonView.getContext(), "Light sensor turned off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rotationVectorToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                        // from sensor service we are
                        // calling proximity sensor
                        rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR);
                        if(isChecked) {
                            Log.d("sensor on","Rotation vector sensor turned on");
                            if (rotationVectorSensor == null) {
                                Toast.makeText(buttonView.getContext(), "No rotation vector sensor found in device.", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                // registering our sensor with sensor manager.
                                sensorManager.registerListener(rotationVectorSensorEventListener, rotationVectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
                            }
                        }
                        else
                        {
                            Log.d("sensor off","Rotation vector sensor turned off");
                            sensorManager.unregisterListener(rotationVectorSensorEventListener,rotationVectorSensor);
                            liveFeedback.setText("");
                            Toast.makeText(buttonView.getContext(), "Rotation vector sensor turned off", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // calling the sensor event class to detect
    // the change in data when sensor starts working.
    SensorEventListener proximitySensorEventListener = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // method to check accuracy changed in sensor.
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            // check if the sensor type is proximity sensor.
            if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {

                if(event.values[0]==0.0){
                    x++;
                    myDatabase.Dao_proximity().insert(new ProximitySensorData(x,event.accuracy, event.values[0], event.timestamp));
                }
                prox = event.values[0];
                acc = event.accuracy;

            }
        }
    };

    // calling the sensor event class to detect
    // the change in data when sensor starts working.
    SensorEventListener rotationVectorSensorEventListener = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // method to check accuracy changed in sensor.
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onSensorChanged(SensorEvent event) {
            // check if the sensor type is GRV sensor.
            if (event.sensor.getType() == Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR) {
                if(x_cor!=event.values[0] || y_cor!=event.values[1] || z_cor!=event.values[2] || scalar_cor!=event.values[3] || some_cor!=event.values[4] || acc_grv!=event.accuracy){
                    y++;
                    myDatabase.Dao_grv().insert(new GeomagneticRotationVectorSensorData(y,event.values[0],event.values[1],event.values[2],event.values[3],event.values[4], event.accuracy,event.timestamp));
                }
                x_cor = event.values[0];
                y_cor = event.values[1];
                z_cor = event.values[2];
                scalar_cor = event.values[3];
                some_cor = event.values[4];
                acc_grv = event.accuracy;

                float[] rotationMatrix_array = new float[9];
                SensorManager.getRotationMatrixFromVector(rotationMatrix_array,event.values);
                float[] orientation_array = new float[3];
                SensorManager.getOrientation(rotationMatrix_array,orientation_array);
                float resultDegrees = (float) Math.toDegrees(orientation_array[0]);

                liveFeedback.setText(String.valueOf(resultDegrees));

                if(resultDegrees<=-2){
                    liveFeedbackDirection.setText("Move Clockwise");
                }else if(resultDegrees>=2){
                    liveFeedbackDirection.setText("Move Anti-clockwise");
                }else{
                    liveFeedbackDirection.setText("Success!");
                }



            }

        }
    };

    SensorEventListener lightSensorEventListener = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            // check if the sensor type is light sensor.
            if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                if(event.values[0]<17){
                    z++;
                    myDatabase.Dao_light().insert(new LightSensorData(z, event.accuracy, (int) event.values[0], event.timestamp));
                }
                inten = event.values[0];
                acc_light = event.accuracy;
            }
        }
    };
}