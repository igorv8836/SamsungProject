package ru.example.samsungproject.supportingClasses;

import java.util.List;

public class Task {
    private String title;
    private String description;
    private int price;
    private boolean isCompleted;
    private int percentCompleted;
    private String author;
    private boolean editable;
    private String id;

    public Task(String id, String title, String description, int price, String author, boolean editable) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.author = author;
        this.editable = editable;
    }

    public Task() {
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public int getPercentCompleted() {
        return percentCompleted;
    }

    public void setPercentCompleted(int percentCompleted) {
        this.percentCompleted = percentCompleted;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
