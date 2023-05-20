package ru.example.samsungproject.interfaces.UserListener;

import java.util.List;

import ru.example.samsungproject.supportingClasses.Invitation;

public interface OnInvitationsLoadedListener {
    void OnLoaded(List<Invitation> list);
    void OnNotLoaded(String message);
}
