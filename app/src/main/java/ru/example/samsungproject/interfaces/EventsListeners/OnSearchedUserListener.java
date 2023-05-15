package ru.example.samsungproject.interfaces.EventsListeners;

public interface OnSearchedUserListener {
    void OnSearchedUser(String name, boolean isCreator);
    void OnNotSearchedUser();
    void OnAlreadyAdded(String email);
}
