package ru.example.samsungproject.supportingClasses;

import java.util.ArrayList;

public interface OnNewsLoadedListener {
    void onNewsLoaded(ArrayList<NewsElement> newsElements);
    void onNewsNotLoaded();
}
