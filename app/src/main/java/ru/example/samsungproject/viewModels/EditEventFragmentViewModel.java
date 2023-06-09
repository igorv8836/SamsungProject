package ru.example.samsungproject.viewModels;

import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.example.samsungproject.interfaces.EventsListeners.OnCreatedEventListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnDeletedTaskListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnLoadedEventListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnLoadedUsersForEventListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnSearchedUserListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnUserStatusClickListener;
import ru.example.samsungproject.repositories.FirestoreEventsRepository;
import ru.example.samsungproject.supportingClasses.Event;
import ru.example.samsungproject.supportingClasses.User;

public class EditEventFragmentViewModel extends ViewModel implements OnUserStatusClickListener {

    public MutableLiveData<ArrayList<User>> users = new MutableLiveData<>(new ArrayList<>());
    public MutableLiveData<String> ToastText = new MutableLiveData<>();
    public MutableLiveData<Boolean> eventIsCreated = new MutableLiveData<>();
    public MutableLiveData<String> title = new MutableLiveData<>();
    public MutableLiveData<String> description = new MutableLiveData<>();
    public MutableLiveData<Boolean> access = new MutableLiveData<>();
    public MutableLiveData<Boolean> returnFragment = new MutableLiveData<>();
    private String eventId;
    private boolean fromExistEvent = false;
    FirestoreEventsRepository repository = new FirestoreEventsRepository();

    public void addUser(String Email){
        repository.SearchUserByEmail(new OnSearchedUserListener() {
            @Override
            public void OnSearchedUser(String name, boolean isCreator) {
                if (!(isCreator))
                    ToastText.setValue("Пользователь успешно добавлен");
                ArrayList<User> temp = users.getValue();
                if (temp == null) {
                    temp = new ArrayList<>();
                }
                temp.add(new User(Email, name, isCreator, isCreator, isCreator));
                users.setValue(temp);
            }

            @Override
            public void OnNotSearchedUser() {
                ToastText.setValue("Пользователь не найден");
            }

            @Override
            public void OnAlreadyAdded(String email) {
                ToastText.setValue("Пользователь уже добавлен");
            }
        }, Email, Objects.requireNonNull(users.getValue()));
    }

    public void addCreatorUser(){
        addUser(repository.getCurrentUser().getEmail());
    }

    public void createEvent(String Title, String Description, String Date, List<User> users, Boolean access){
        if (fromExistEvent) {
            update(Title, Description, Date, users, access);
            return;
        }
        repository.CreateNewEvent(new OnCreatedEventListener() {
            @Override
            public void OnCreatedEvent() {
                eventIsCreated.setValue(true);
            }

            @Override
            public void OnNotCreatedEvent(String message) {
                ToastText.setValue(message);
                eventIsCreated.setValue(false);
            }
        }, Title, Description, Date, users, access);
    }

    public void loadFromBundle(Bundle bundle){
        String id = bundle.getString("id");
        if (!id.isEmpty()){
            fromExistEvent = true;
            eventId = id;
            repository.loadEvent(new OnLoadedEventListener() {
                @Override
                public void onLoadedEvent(Event event) {
                    title.setValue(event.getTitle());
                    description.setValue(event.getDescription());
                    access.setValue(event.getAccess());
                    users.setValue((ArrayList<User>) event.getMembers());
                }

                @Override
                public void onNotLoadedEvent(String message) {
                }
            }, id);
        }
    }

    public void update(String Title, String Description, String Date, List<User> users, Boolean access){
        repository.loadUsersForEvent(new OnLoadedUsersForEventListener() {
            @Override
            public void OnLoadedUsers(List<User> list) {
                repository.updateEvent(eventId, Title, Description, Date, users, list, access);
                returnFragment.setValue(true);
            }

            @Override
            public void OnNotLoadedUsers() {
                Log.w("TAG", "Error");
            }
        }, eventId);
    }

    @Override
    public void onClickUp(User user) {
        ArrayList<User> temp = users.getValue();

        if (temp == null || user.isCreator())
            return;

       temp.get(temp.indexOf(user)).setAdmin(true);
       users.setValue(temp);
    }

    @Override
    public void onClickDown(User user) {
        ArrayList<User> temp = users.getValue();
        if (temp == null || user.isCreator())
            return;

        if (user.isAdmin()){
            temp.get(temp.indexOf(user)).setAdmin(false);
        } else {
            repository.kickUser(eventId, user.getEmail());
            temp.remove(user);
        }
        users.setValue(temp);
    }

    public void deleteEvent(){
        repository.deleteEvent(new OnDeletedTaskListener() {
            @Override
            public void OnDeletedTask() {
                returnFragment.setValue(true);
            }

            @Override
            public void OnNotDeletedTask() {

            }
        }, eventId);
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

}
