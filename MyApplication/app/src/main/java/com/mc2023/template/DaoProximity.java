package com.mc2023.template;


import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@androidx.room.Dao
public interface DaoProximity {
    @Insert
    void insert(ProximitySensorData model);

    @Query("DELETE FROM proximitysensordatatable")
    void deleteAllProximityData();



}
