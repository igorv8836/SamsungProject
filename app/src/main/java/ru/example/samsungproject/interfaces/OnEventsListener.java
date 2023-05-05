package ru.example.samsungproject.interfaces;

public interface OnEventsListener {

    void OnCreatedEvent();
    void OnNotCreatedEvent();

    void OnAddedAdminUser();
    void OnNotAddedAdminUser();

    void OnAddedUser();
    void OnNotAddedUser();

    void OnChangedEvent();
    void OnNotChangedEvent();

    void OnDeletedTask();
    void OnNotDeletedTask();

    void OnAddedTask();
    void OnNotAddedTask();

    void OnChangedTask();
    void OnNotChangedTask();
}
