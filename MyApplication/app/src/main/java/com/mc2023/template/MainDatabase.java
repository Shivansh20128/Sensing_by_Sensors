package com.mc2023.template;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {ProximitySensorData.class,LightSensorData.class,GeomagneticRotationVectorSensorData.class}, version = 4, exportSchema = false)
public abstract class MainDatabase extends RoomDatabase {
    private static MainDatabase instance;

    public abstract DaoProximity Dao_proximity();
    public abstract DaoLight Dao_light();
    public abstract DaoGRV Dao_grv();
    public static synchronized MainDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),MainDatabase.class, "main_database").fallbackToDestructiveMigration().addCallback(roomCallback).build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        PopulateDbAsyncTask(MainDatabase instance) {
            DaoProximity dao_proximity = instance.Dao_proximity();
            DaoLight dao_light = instance.Dao_light();
            DaoGRV dao_grv = instance.Dao_grv();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
