package ru.example.samsungproject.Database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.cert.Extension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import ru.example.samsungproject.interfaces.OnProfileLoadedListener;
import ru.example.samsungproject.supportingClasses.NewsElement;
import ru.example.samsungproject.interfaces.OnNewsLoadedListener;

public class FirestoreDB {
    private final FirebaseFirestore firebaseFirestore;

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
                            if (documentSnapshot.get("Title") != null
                            && documentSnapshot.get("Description") != null
                            && documentSnapshot.get("Date") != null) {

                                list.add(new NewsElement(
                                        documentSnapshot.get("Title").toString(),
                                        documentSnapshot.get("Description").toString(),
                                        documentSnapshot.get("Date").toString()));
                                Log.w("TAG", documentSnapshot.getId() + " --- " + documentSnapshot.getData());
                            } else{
                                Log.w("TAG", "документ news, одно поле имеет null");
                            }
                        }
                        Collections.reverse(list);
                        listener.onNewsLoaded(list);
                    } else {
                        listener.onNewsNotLoaded();
                        Log.w("LoadNewsFromNetwork", "Данные не загрузились");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onNewsNotLoaded();
                        Log.w("TAG", "данные новости не загрузились на телефон");
                    }
                });
    }

    public void LoadProfile(OnProfileLoadedListener listener, String Uid){
        firebaseFirestore.collection("users")
                .document(Objects.requireNonNull(Uid))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult().get("Email") != null && task.getResult().get("Name") != null){
                        StringBuilder email = new StringBuilder(task.getResult().get("Email").toString());
                        if (email.length() > 40){
                            email.replace(37, 40, "...");
                        }

                        StringBuilder name = new StringBuilder(Objects.requireNonNull(task.getResult().get("Name")).toString());
                        if (name.length() > 40){
                            name.replace(37, 40, "...");
                        }

                        listener.OnProfileLoaded(name.toString(), email.toString());
                    } else{
                        Log.i("TAG", Uid);
                        listener.OnProfileNotLoaded(task.getException());
                    }
                });
    }

}
