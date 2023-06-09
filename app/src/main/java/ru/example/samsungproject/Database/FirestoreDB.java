package ru.example.samsungproject.Database;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import ru.example.samsungproject.interfaces.UserListener.OnFeedbackSendedListener;
import ru.example.samsungproject.interfaces.UserListener.OnNameSendedListener;
import ru.example.samsungproject.interfaces.OnProfileLoadedListener;
import ru.example.samsungproject.supportingClasses.NewsElement;
import ru.example.samsungproject.interfaces.OnNewsLoadedListener;

public class FirestoreDB {
    private final FirebaseFirestore firebaseFirestore;
    private static FirestoreDB instance;

    private FirestoreDB(){
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public static synchronized FirestoreDB getInstance() {
        if (instance == null) {
            instance = new FirestoreDB();
        }
        return instance;

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
                                        Objects.requireNonNull(documentSnapshot.get("Title")).toString(),
                                        Objects.requireNonNull(documentSnapshot.get("Description")).toString(),
                                        Objects.requireNonNull(documentSnapshot.get("Date")).toString()));
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
                }).addOnFailureListener(e -> {
                    listener.onNewsNotLoaded();
                    Log.w("TAG", "данные новости не загрузились на телефон");
                });
    }

    public void LoadProfile(OnProfileLoadedListener listener, String email){
        firebaseFirestore.collection("users")
                .document(Objects.requireNonNull(email))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult().get("Email") != null && task.getResult().get("Name") != null){
                        StringBuilder email1 = new StringBuilder(email);
                        if (email1.length() > 40){
                            email1.replace(37, 40, "...");
                        }

                        StringBuilder name = new StringBuilder(Objects.requireNonNull(task.getResult().get("Name")).toString());
                        if (name.length() > 40){
                            name.replace(37, 40, "...");
                        }

                        listener.OnProfileLoaded(name.toString(), email1.toString());
                    } else{
                        Log.i("TAG", email);
                        listener.OnProfileNotLoaded(task.getException());
                    }
                });
    }

    public void SendNewName(OnNameSendedListener listener, String name){
        if (name.isEmpty()) {
            listener.onNameNotSended();
        }
        else {
            CollectionReference collection = firebaseFirestore.collection("users");
            DocumentReference newDocRef = collection.document(
                    Objects.requireNonNull(
                            Objects.requireNonNull(
                                    FirebaseAuth.getInstance().getCurrentUser()).getEmail()));
            Map<String, Object> data = new HashMap<>();
            data.put("Name", name);

            newDocRef.update(data).addOnCompleteListener(task -> {
                if (task.isSuccessful())
                    listener.onNameSended();
                else
                    listener.onNameNotSended();
            });
        }
    }

    public void SendFeedback(OnFeedbackSendedListener listener, String topic, String text, String email){
        if (topic.isEmpty() || text.isEmpty()) {
            listener.onFeedbackNotSended(new NullPointerException());
            return;
        }
        Map<String, String> data = new HashMap<>();
        data.put("Email", email);
        data.put("Topic", topic);
        data.put("Text", text);
        firebaseFirestore.collection("feedback")
                .add(data)
                .addOnSuccessListener(e -> listener.onFeedbackSended())
                .addOnFailureListener(listener::onFeedbackNotSended);
    }
}