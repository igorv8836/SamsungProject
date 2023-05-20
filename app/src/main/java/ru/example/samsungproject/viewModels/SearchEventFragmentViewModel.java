package ru.example.samsungproject.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.example.samsungproject.interfaces.EventsListeners.OnEventJoinedListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnSearchedEventListener;
import ru.example.samsungproject.repositories.FirestoreEventsRepository;
import ru.example.samsungproject.supportingClasses.Event;

public class SearchEventFragmentViewModel extends ViewModel implements OnEventJoinedListener {

    public MutableLiveData<List<Event>> events = new MutableLiveData<>();
    private FirestoreEventsRepository repository = new FirestoreEventsRepository();


    @Override
    public void OnJoined(String id) {
        repository.sendParticipation(id);
    }

    public void searchEvents(String name) {
        if (name.isEmpty())
            return;

        repository.searchEvents(new OnSearchedEventListener() {
            @Override
            public void onSearchedEvent(List<Event> data) {
                events.setValue(data);
            }

            @Override
            public void onNotSearchedEvent(String message) {

            }
        }, name);
    }
}
