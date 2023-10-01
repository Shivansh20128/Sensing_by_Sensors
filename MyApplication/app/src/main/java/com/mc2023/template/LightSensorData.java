package com.mc2023.template;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "LightSensorDataTable")
public class LightSensorData {
    @PrimaryKey(autoGenerate = true)

    private final int id;

    private final int accuracy ;
    private final float intensity ;
    private final long timestamp ;

    public LightSensorData(int id, int accuracy, float intensity, long timestamp) {
        this.accuracy = accuracy;
        this.intensity = intensity;
        this.timestamp=timestamp;
        this.id=id;
    }

    public float getIntensity() {
        return intensity;
    }

    public int getAccuracy() {
        return accuracy;
    }
    public long getTimestamp() {
        return timestamp;
    }

    public int getId() {
        return id;
    }
}
