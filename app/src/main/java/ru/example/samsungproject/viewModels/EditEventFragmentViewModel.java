package ru.example.samsungproject.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import ru.example.samsungproject.interfaces.EventsListeners.OnSearchedUserListener;
import ru.example.samsungproject.interfaces.OnUserAddedListener;
import ru.example.samsungproject.repositories.FirestoreEventsRepository;
import ru.example.samsungproject.supportingClasses.User;

public class EditEventFragmentViewModel extends ViewModel {

    MutableLiveData<ArrayList<User>> users = new MutableLiveData<>();
    FirestoreEventsRepository repository = new FirestoreEventsRepository();

    public void addUser(String Email){
        repository.SearchUserByEmail(new OnSearchedUserListener() {
            @Override
            public void OnSearchedUser() {

            }

            @Override
            public void OnNotSearchedUser() {

            }
        }, Email);
    }

    public void createEvent(String Title, String Description, String Date, List<User> users){

    }

}
