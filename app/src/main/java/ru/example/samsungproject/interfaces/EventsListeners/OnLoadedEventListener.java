package ru.example.samsungproject.interfaces.EventsListeners;

import ru.example.samsungproject.supportingClasses.Event;

public interface OnLoadedEventListener {
    void onLoadedEvent(Event event);
    void onNotLoadedEvent(String message);
}
