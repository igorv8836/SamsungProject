package ru.example.samsungproject.interfaces.EventsListeners;

import java.util.List;

import ru.example.samsungproject.supportingClasses.Event;

public interface OnSearchedEventListener {
    void onSearchedEvent(List<Event> data);
    void onNotSearchedEvent(String message);
}
