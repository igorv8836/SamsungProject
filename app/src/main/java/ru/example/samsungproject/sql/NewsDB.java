//package ru.example.samsungproject.sql;
//
//import android.content.Context;
//
//import androidx.room.Database;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;
//
//import ru.example.samsungproject.supportingClass.NewsElement;
//
//@Database(entities = {NewsElement.class}, version = 1)
//public abstract class NewsDB extends RoomDatabase {
//
//    private static NewsDB instance;
//
//    public abstract NewsDao newsDao();
//
//    public static synchronized NewsDB getInstance(Context context){
//        if (instance == null){
//            instance = Room.databaseBuilder(
//                    context.getApplicationContext(),
//                    NewsDB.class,
//                    "news_database")
//                    .build();
//        }
//        return instance;
//    }
//}
