package ru.example.samsungproject.interfaces.UserListener;

public interface OnResponseToInvitationListener {
    void OnUserAgreed(String id);
    void OnUserDisagreed(String id);
}
