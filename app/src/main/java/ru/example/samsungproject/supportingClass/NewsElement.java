package ru.example.samsungproject.supportingClass;

import android.util.Log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.DateFormat;

@Entity(tableName = "news_table")
public class NewsElement {

    @PrimaryKey(autoGenerate = true)
    public int id;
    private String title;
    private String description;
    private String date;
    private int day;
    private int month;
    private int year;

    public NewsElement(String title, String description, String date){
        this.title = title;
        this.description = description;
        this.date = date;

        try {
            day = Integer.parseInt(date.substring(0, 2));
            month = Integer.parseInt(date.substring(3, 5));
            year = Integer.parseInt(date.substring(6, 10));
        }catch (Exception e){
            Log.e("ERROR", "ошибка в дате в NewsElement");
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
