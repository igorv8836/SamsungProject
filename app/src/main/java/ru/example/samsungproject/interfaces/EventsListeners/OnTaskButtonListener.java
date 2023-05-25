package ru.example.samsungproject.interfaces.EventsListeners;

import ru.example.samsungproject.supportingClasses.Task;

public interface OnTaskButtonListener {
    void onPressed(String taskId, String title, String description, String author, int price, int percent, boolean isCompleted, boolean isSave);

    void changeCompleted(String taskId, int percent, boolean isCompleted);

    void deleteTask(String taskId);
}
