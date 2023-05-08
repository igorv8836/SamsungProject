package ru.example.samsungproject.Database;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import ru.example.samsungproject.interfaces.OnEventsListener;
import ru.example.samsungproject.interfaces.OnUserAddedListener;

public class FirestoreEventsDB {
    private FirebaseFirestore firebaseFirestore;
    private static FirestoreEventsDB instance;

    private FirestoreEventsDB(){
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public static synchronized FirestoreEventsDB getInstance() {
        if (instance == null) {
            instance = new FirestoreEventsDB();
        }
        return instance;

    }

    public void CreateEvent(OnEventsListener listener, String title, String description){
        if (title.isEmpty() || description.isEmpty()) {
            listener.OnNotCreatedEvent();
            return;
        }
        Map<String, String> data = new HashMap<>();
        data.put("Title", title);
        data.put("Description", description);
        firebaseFirestore.collection("feedback")
                .add(data)
                .addOnSuccessListener(e -> listener.OnCreatedEvent())
                .addOnFailureListener(e -> listener.OnNotCreatedEvent());
    }

    public void ChangeEvent(){

    }

    public void DeleteEvent(){

    }

    public void AddTask(){

    }

    public void ChangeTask(){

    }

    public void DeleteTask(){

    }

    public void SearchUserByEmail(OnUserAddedListener l, String email){
        firebaseFirestore.collection("users").whereEqualTo("Email", email).get()
                .addOnSuccessListener(task -> {
                    if (!(task.isEmpty()))
                        Log.i("TAG", "поле существует");
                    else
                        Log.i("TAG", "поле не существует");
                }).addOnFailureListener(task -> {
                    Log.i("TAG", task.getMessage());
                });
    }

}
