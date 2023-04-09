package ru.example.samsungproject.Database;

import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;

import ru.example.samsungproject.supportingClasses.NewsElement;
import ru.example.samsungproject.supportingClasses.OnNewsLoadedListener;

public class FirestoreDB {
    private FirebaseFirestore firebaseFirestore;

    public FirestoreDB() {
        this.firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public void LoadNews(OnNewsLoadedListener listener){
        firebaseFirestore.collection("news")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<NewsElement> list = new ArrayList<>();
                        for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                            list.add(new NewsElement(
                                    documentSnapshot.get("Title").toString(),
                                    documentSnapshot.get("Description").toString(),
                                    documentSnapshot.get("Date").toString()));
                            Log.w("TAG", documentSnapshot.getId() + " --- " + documentSnapshot.getData());
                        }
                        Collections.reverse(list);
                        listener.onNewsLoaded(list);
                    } else {
                        listener.onNewsNotLoaded();
                        Log.w("LoadNewsFromNetwork", "Данные не загрузились");
                    }
                });
    }
}
