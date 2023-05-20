package ru.example.samsungproject.interfaces.UserListener;

public interface OnFeedbackSendedListener {
    void onFeedbackSended();
    void onFeedbackNotSended(Exception e);
}
