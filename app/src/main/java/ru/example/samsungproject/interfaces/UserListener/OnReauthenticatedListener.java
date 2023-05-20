package ru.example.samsungproject.interfaces.UserListener;

public interface OnReauthenticatedListener {
    void OnReauthenticated();
    void OnNotReauthenticated(Exception e);
}
