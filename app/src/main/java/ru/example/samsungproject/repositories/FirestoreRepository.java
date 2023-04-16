package ru.example.samsungproject.repositories;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.example.samsungproject.Database.FirestoreDB;
import ru.example.samsungproject.interfaces.OnNewsLoadedListener;
import ru.example.samsungproject.interfaces.OnProfileLoadedListener;

public class FirestoreRepository {
    private final FirestoreDB firestoreDB;
    private final FirebaseAuth mAuth;
    private final FirebaseUser currentUser;

    public FirestoreRepository() {
        firestoreDB = new FirestoreDB();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

    public void LoadNewsFromFirebase(OnNewsLoadedListener listener) {
        firestoreDB.LoadNews(listener);
    }

    public void LoadSimpleProfileFromFirebase(OnProfileLoadedListener listener){
        firestoreDB.LoadProfile(listener, currentUser.getUid());
    }
}

