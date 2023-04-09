package ru.example.samsungproject.repositories;

import ru.example.samsungproject.Database.FirestoreDB;
import ru.example.samsungproject.interfaces.OnNewsLoadedListener;
import ru.example.samsungproject.interfaces.OnProfileLoadedListener;

public class FirestoreRepository {
    private final FirestoreDB firestoreDB;

    public FirestoreRepository() {
        firestoreDB = new FirestoreDB();
    }

    public void LoadNewsFromFirebase(OnNewsLoadedListener listener) {
        firestoreDB.LoadNews(listener);
    }

    public void LoadProfileFromFirebase(OnProfileLoadedListener listener){
        firestoreDB.LoadProfile(listener);
    }
}

