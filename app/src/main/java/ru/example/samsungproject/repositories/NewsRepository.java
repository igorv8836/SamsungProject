package ru.example.samsungproject.repositories;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

import ru.example.samsungproject.sql.AppDB;
import ru.example.samsungproject.supportingClass.NewsElement;
import ru.example.samsungproject.supportingClass.OnNewsLoadedListener;

public class NewsRepository{

    public final AppDB appDB;
    public final FirebaseFirestore firebaseFirestore;

    public NewsRepository(Application application) {
        appDB = AppDB.getInstance(application);
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public void LoadNewsFromDB(){

    }

    public void LoadNewsFromNetwork(OnNewsLoadedListener listener) {
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
                        Log.w("LoadNewsFromNetwork", "Данные не загрузились");
                    }
                });
    }
}

