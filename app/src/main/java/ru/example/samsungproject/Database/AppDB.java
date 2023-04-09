package ru.example.samsungproject.Database;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ru.example.samsungproject.supportingClasses.NewsElement;

@Database(entities = {NewsElement.class}, version = 1)
public abstract class AppDB extends RoomDatabase {
    private static AppDB instance;

    public static synchronized AppDB getInstance(Application application){
        if (instance == null){
            instance = Room.databaseBuilder(
                    application.getApplicationContext(),
                    AppDB.class,
                    "database").build();
        }
        return instance;
    }
}
