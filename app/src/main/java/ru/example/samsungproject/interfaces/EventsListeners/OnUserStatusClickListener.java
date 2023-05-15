package ru.example.samsungproject.interfaces.EventsListeners;

import ru.example.samsungproject.supportingClasses.User;

public interface OnUserStatusClickListener {
    void onClickUp(User user);
    void onClickDown(User user);
}
