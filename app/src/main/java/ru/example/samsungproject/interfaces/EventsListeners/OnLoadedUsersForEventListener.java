package ru.example.samsungproject.interfaces.EventsListeners;

import java.util.List;

import ru.example.samsungproject.supportingClasses.User;

public interface OnLoadedUsersForEventListener {
    void OnLoadedUsers(List<User> list);
    void OnNotLoadedUsers();
}
