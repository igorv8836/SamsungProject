package ru.example.samsungproject.interfaces;

public interface OnReauthenticatedListener {
    void OnReauthenticated();
    void OnNotReauthenticated(Exception e);
}
