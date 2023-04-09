package ru.example.samsungproject.interfaces;

import java.util.ArrayList;

import ru.example.samsungproject.supportingClasses.NewsElement;

public interface OnNewsLoadedListener {
    void onNewsLoaded(ArrayList<NewsElement> newsElements);
    void onNewsNotLoaded();
}
