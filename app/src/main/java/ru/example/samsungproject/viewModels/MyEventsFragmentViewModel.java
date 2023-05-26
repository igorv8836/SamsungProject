package ru.example.samsungproject.viewModels;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.example.samsungproject.interfaces.EventsListeners.OnEventManagedListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnLoadedMyEventsListener;
import ru.example.samsungproject.interfaces.UserListener.OnInvitationsLoadedListener;
import ru.example.samsungproject.interfaces.UserListener.OnResponseToInvitationListener;
import ru.example.samsungproject.repositories.FirestoreEventsRepository;
import ru.example.samsungproject.supportingClasses.Event;
import ru.example.samsungproject.supportingClasses.Invitation;

public class MyEventsFragmentViewModel extends ViewModel implements OnResponseToInvitationListener, OnEventManagedListener {
    public MutableLiveData<List<Event>> myEvents = new MutableLiveData<>(new ArrayList<>());
    public MutableLiveData<List<Event>> otherEvents = new MutableLiveData<>(new ArrayList<>());
    public MutableLiveData<List<Invitation>> invitations = new MutableLiveData<>(new ArrayList<>());
    public MutableLiveData<Integer> myEventsRecyclerViewVisibility = new MutableLiveData<>(View.GONE);
    public MutableLiveData<Integer> otherEventsRecyclerViewVisibility = new MutableLiveData<>(View.GONE);
    public MutableLiveData<Integer> invitationsRecyclerViewVisibility = new MutableLiveData<>(View.VISIBLE);
    public MutableLiveData<String> manageEvent = new MutableLiveData<>();
    public MutableLiveData<String> eventTasks = new MutableLiveData<>();
    FirestoreEventsRepository repository = new FirestoreEventsRepository();

    public void loadMyEvents(){
        repository.loadEvents(new OnLoadedMyEventsListener() {
            @Override
            public void onLoadedMyEvents(List<Event> myEvents1, List<Event> otherEvents1) {
                if (myEvents1 != null)
                    myEvents.setValue(myEvents1);
                if (otherEvents1 != null)
                    otherEvents.setValue(otherEvents1);
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

    public void changeOtherEventsRecyclerViewVisibility(){
        if (otherEventsRecyclerViewVisibility.getValue() == View.GONE)
            otherEventsRecyclerViewVisibility.setValue(View.VISIBLE);
        else
            otherEventsRecyclerViewVisibility.setValue(View.GONE);
    }

    public void changeInvitationsRecyclerViewVisibility(){
        if (invitationsRecyclerViewVisibility.getValue() == View.GONE)
            invitationsRecyclerViewVisibility.setValue(View.VISIBLE);
        else
            invitationsRecyclerViewVisibility.setValue(View.GONE);
    }

    public void loadInvitations(){
        repository.loadInvitations(new OnInvitationsLoadedListener() {
            @Override
            public void OnLoaded(List<Invitation> list) {
                invitations.setValue(list);
            }

            @Override
            public void OnNotLoaded(String message) {

            }
        });
    }

    @Override
    public void OnUserAgreed(String id) {
        repository.invitationAgree(id);
        deleteInvitation(id);
    }

    @Override
    public void OnUserDisagreed(String id) {
        repository.invitationDisagree(id);
        deleteInvitation(id);
    }

    public void deleteInvitation(String id){
        List<Invitation> temp = invitations.getValue();
        for (Invitation invitation : Objects.requireNonNull(temp)){
            if (invitation.geteventId().equals(id)) {
                temp.remove(invitation);
                invitations.setValue(temp);
                return;
            }
        }
    }

    @Override
    public void OnEventManaged(Event event) {
        manageEvent.setValue(event.getId());
    }

    @Override
    public void OnTasksShowed(String id) {
        eventTasks.setValue(id);
    }
}
