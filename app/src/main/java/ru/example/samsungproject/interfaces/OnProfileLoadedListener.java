package ru.example.samsungproject.interfaces;

public interface OnProfileLoadedListener {
    void OnProfileLoaded(String name, String email);

    void OnProfileNotLoaded();
}
