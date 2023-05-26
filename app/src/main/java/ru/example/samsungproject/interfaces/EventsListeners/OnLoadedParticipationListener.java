package ru.example.samsungproject.interfaces.EventsListeners;

import java.util.List;

public interface OnLoadedParticipationListener {
    void onLoaded(List<String> emails);
    void onNotLoaded();
}
