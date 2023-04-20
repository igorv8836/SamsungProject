package ru.example.samsungproject.interfaces;

public interface OnFeedbackSendedListener {
    void onFeedbackSended();
    void onFeedbackNotSended(Exception e);
}
