package ru.example.samsungproject.viewModels;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.example.samsungproject.interfaces.EventsListeners.OnLoadedMyEventsListener;
import ru.example.samsungproject.repositories.FirestoreEventsRepository;
import ru.example.samsungproject.supportingClasses.Event;

public class MyEventsFragmentViewModel extends ViewModel {
    public MutableLiveData<List<Event>> myEvents = new MutableLiveData<>();
    public MutableLiveData<Integer> myEventsRecyclerViewVisibility = new MutableLiveData<>(View.GONE);
    FirestoreEventsRepository repository = new FirestoreEventsRepository();

    public void loadMyEvents(){
        repository.loadMyEvents(new OnLoadedMyEventsListener() {
            @Override
            public void onLoadedMyEvents(List<Event> data) {
                myEvents.setValue(data);
            }

            @Override
            public void onNotLoadedMyEvents(String message) {

            }
        });
    }

    public void changeMyEventsRecyclerViewVisibility(){
        if (myEventsRecyclerViewVisibility.getValue() == View.GONE)
            myEventsRecyclerViewVisibility.setValue(View.VISIBLE);
        else
            myEventsRecyclerViewVisibility.setValue(View.GONE);
    }
}
