package ru.example.samsungproject.Database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import ru.example.samsungproject.interfaces.OnProfileLoadedListener;
import ru.example.samsungproject.supportingClasses.NewsElement;
import ru.example.samsungproject.interfaces.OnNewsLoadedListener;

public class FirestoreDB {
    private final FirebaseFirestore firebaseFirestore;
    private final FirebaseAuth mAuth;
    private final FirebaseUser currentUser;

    public FirestoreDB() {
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.mAuth = FirebaseAuth.getInstance();
        this.currentUser = mAuth.getCurrentUser();
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

    public void LoadProfile(OnProfileLoadedListener listener){
        firebaseFirestore.collection("users")
                .document(Objects.requireNonNull(currentUser.getEmail()))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){

                        StringBuilder email = new StringBuilder(Objects.requireNonNull(task.getResult().get("Email")).toString());
                        if (email.length() > 20){
                            email.replace(17, 20, "...");
                        }

                        StringBuilder name = new StringBuilder(Objects.requireNonNull(task.getResult().get("Name")).toString());
                        if (name.length() > 20){
                            name.replace(17, 20, "...");
                        }

                        listener.OnProfileLoaded(name.toString(), email.toString());
                    } else{
                        listener.OnProfileNotLoaded();
                    }
                });
    }

}
