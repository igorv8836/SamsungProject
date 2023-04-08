package ru.example.samsungproject.sql;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import ru.example.samsungproject.supportingClass.NewsElement;

@Dao
public interface NewsDao {

    @Insert
    Completable addNews(NewsElement newsElement);

    @Query("SELECT * FROM news_table WHERE id = :id")
    Observable<NewsElement> getNewsById(int id);

    @Query("SELECT * FROM news_table")
    Observable<List<NewsElement>> getAllNews();

    @Delete
    Completable deleteAllNews(NewsElement newsElement);
}
