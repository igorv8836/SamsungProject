package ru.example.samsungproject.repositories;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.example.samsungproject.Database.FirestoreDB;
import ru.example.samsungproject.interfaces.UserListener.OnFeedbackSendedListener;
import ru.example.samsungproject.interfaces.UserListener.OnNameSendedListener;
import ru.example.samsungproject.interfaces.OnNewsLoadedListener;
import ru.example.samsungproject.interfaces.OnProfileLoadedListener;

public class FirestoreRepository {
    private final FirestoreDB firestoreDB;
    private final FirebaseAuth mAuth;
    private final FirebaseUser currentUser;

    public FirestoreRepository() {
        firestoreDB = FirestoreDB.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

    public void LoadNewsFromFirebase(OnNewsLoadedListener listener) {
        firestoreDB.LoadNews(listener);
    }

    public void LoadSimpleProfileFromFirebase(OnProfileLoadedListener listener){
        firestoreDB.LoadProfile(listener, currentUser.getEmail());
    }

    public void SendName(OnNameSendedListener listener, String name){
        firestoreDB.SendNewName(listener, name);
    }

    public void SendFeedback(OnFeedbackSendedListener listener, String topic, String text){
        firestoreDB.SendFeedback(listener, topic, text, currentUser.getEmail());
    }

}

