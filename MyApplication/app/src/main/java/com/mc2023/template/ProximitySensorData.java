package com.mc2023.template;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ProximitySensorDataTable")
public class ProximitySensorData {
    @PrimaryKey(autoGenerate = true)

    private final int id;
    private final int accuracy ;
    private final float proximity ;
    private final long timestamp ;

    public ProximitySensorData(int id, int accuracy, float proximity, long timestamp) {
        this.accuracy = accuracy;
        this.proximity = proximity;
        this.timestamp=timestamp;
        this.id=id;
    }

    public float proximity() {
        return proximity;
    }

    public int accuracy() {
        return accuracy;
    }
    public long timestamp() {
        return timestamp;
    }

    public int getId() {
        return id;
    }

}
