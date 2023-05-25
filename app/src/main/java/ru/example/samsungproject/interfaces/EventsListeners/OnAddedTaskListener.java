package ru.example.samsungproject.interfaces.EventsListeners;

public interface OnAddedTaskListener {
    void OnAddedTask(String id, String author);
    void OnNotAddedTask();
}
