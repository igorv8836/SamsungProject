package ru.example.samsungproject.repositories;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.example.samsungproject.Database.FirestoreEventsDB;
import ru.example.samsungproject.interfaces.EventsListeners.OnCreatedEventListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnSearchedUserListener;
import ru.example.samsungproject.interfaces.OnUserAddedListener;
import ru.example.samsungproject.supportingClasses.User;

public class FirestoreEventsRepository {
    private final FirestoreEventsDB firestoreEventsDB;
    private final FirebaseAuth mAuth;
    private final FirebaseUser currentUser;

    public FirestoreEventsRepository() {
        this.firestoreEventsDB = FirestoreEventsDB.getInstance();
        this.mAuth = FirebaseAuth.getInstance();
        this.currentUser = mAuth.getCurrentUser();
    }

    public void SearchUserByEmail(OnSearchedUserListener listener, String email, ArrayList<User> users){
        for (int i = 0; i < users.size(); i++){
            if (Objects.equals(users.get(i).getEmail(), email)){
                listener.OnAlreadyAdded(email);
                return;
            }
        }
        firestoreEventsDB.SearchUserByEmail(listener, email, Objects.equals(currentUser.getEmail(), email));
    }

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public void CreateNewEvent(OnCreatedEventListener l, String Title, String Description, String Date, List<User> users, Boolean access){
        firestoreEventsDB.CreateEvent(l, Title, Description, currentUser.getEmail(), access, users);
    }
}
