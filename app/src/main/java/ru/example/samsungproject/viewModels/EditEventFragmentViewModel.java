package ru.example.samsungproject.viewModels;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.example.samsungproject.interfaces.EventsListeners.OnCreatedEventListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnLoadedMyEventsListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnSearchedUserListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnUserStatusClickListener;
import ru.example.samsungproject.interfaces.OnUserAddedListener;
import ru.example.samsungproject.repositories.FirestoreEventsRepository;
import ru.example.samsungproject.supportingClasses.Event;
import ru.example.samsungproject.supportingClasses.User;

public class EditEventFragmentViewModel extends ViewModel implements OnUserStatusClickListener {

    public MutableLiveData<ArrayList<User>> users = new MutableLiveData<>(new ArrayList<>());
    public MutableLiveData<String> ToastText = new MutableLiveData<>();
    public MutableLiveData<Boolean> eventIsCreated = new MutableLiveData<>();
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
            temp.remove(user);
        }
        users.setValue(temp);
    }
}
