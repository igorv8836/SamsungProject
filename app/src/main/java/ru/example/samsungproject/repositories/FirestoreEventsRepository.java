package ru.example.samsungproject.repositories;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.example.samsungproject.Database.FirestoreEventsDB;
import ru.example.samsungproject.interfaces.EventsListeners.OnSearchedUserListener;
import ru.example.samsungproject.interfaces.OnUserAddedListener;

public class FirestoreEventsRepository {
    private final FirestoreEventsDB firestoreEventsDB;
    private final FirebaseAuth mAuth;
    private final FirebaseUser currentUser;

    public FirestoreEventsRepository() {
        this.firestoreEventsDB = FirestoreEventsDB.getInstance();
        this.mAuth = FirebaseAuth.getInstance();
        this.currentUser = mAuth.getCurrentUser();
    }

    public void SearchUserByEmail(OnSearchedUserListener listener, String email){
        firestoreEventsDB.SearchUserByEmail(listener, email);
    }
}
