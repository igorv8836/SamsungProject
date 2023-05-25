package ru.example.samsungproject.interfaces.EventsListeners;

import java.util.List;

import ru.example.samsungproject.supportingClasses.Task;

public interface OnAddedTasksListener {
    void OnAddedTasks(List<Task> data);
    void OnNotAddedTasks();
}
