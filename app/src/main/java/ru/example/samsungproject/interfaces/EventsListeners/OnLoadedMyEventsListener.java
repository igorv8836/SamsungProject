package ru.example.samsungproject.interfaces.EventsListeners;

import java.util.List;

import ru.example.samsungproject.supportingClasses.Event;

public interface OnLoadedMyEventsListener {
    void onLoadedMyEvents(List<Event> data);

    void onNotLoadedMyEvents(String message);
}