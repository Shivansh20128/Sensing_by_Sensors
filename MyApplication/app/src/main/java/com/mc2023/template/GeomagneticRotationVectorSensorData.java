package com.mc2023.template;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


// below line is for setting table name.
@Entity(tableName = "GeomagneticRotationVectorSensorDataTable")
public class GeomagneticRotationVectorSensorData {
    @PrimaryKey(autoGenerate = true)

    private final int id;

    private final float rotation_vector_x ;
    private final float rotation_vector_y ;
    private final float rotation_vector_z ;
    private final float rotation_vector_scalar ;
    private final float rotation_vector_something ;

    private final int accuracy ;
    private final long timestamp ;

    public GeomagneticRotationVectorSensorData(int id, float rotation_vector_x, float rotation_vector_y, float rotation_vector_z, float rotation_vector_scalar, float rotation_vector_something, int accuracy, long timestamp) {
        this.rotation_vector_x = rotation_vector_x;
        this.rotation_vector_y = rotation_vector_y;
        this.rotation_vector_z = rotation_vector_z;
        this.rotation_vector_scalar = rotation_vector_scalar;
        this.rotation_vector_something = rotation_vector_something;
        this.accuracy = accuracy;
        this.timestamp=timestamp;
        this.id=id;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public float getRotation_vector_x() {
        return rotation_vector_x;
    }
    public float getRotation_vector_y() {
        return rotation_vector_y;
    }
    public float getRotation_vector_z() {
        return rotation_vector_z;
    }
    public float getRotation_vector_scalar() {
        return rotation_vector_scalar;
    }
    public float getRotation_vector_something() {
        return rotation_vector_something;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getId() {
        return id;
    }
}
