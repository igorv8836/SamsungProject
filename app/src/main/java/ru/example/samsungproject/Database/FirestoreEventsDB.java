package ru.example.samsungproject.Database;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.example.samsungproject.interfaces.EventsListeners.OnAddedTaskListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnChangedEventListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnChangedTaskListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnCreatedEventListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnDeletedTaskListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnSearchedUserListener;
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

    public void CreateEvent(OnCreatedEventListener listener, String title, String description, String admin, Boolean access, List<String> users){
        if (title.isEmpty() || description.isEmpty()) {
            listener.OnNotCreatedEvent();
            return;
        }
        Map<String, Object> data = new HashMap<>();
        data.put("Title", title);
        data.put("Description", description);
        data.put("admin", admin);
        data.put("access", access);
        data.put("members", users);
        firebaseFirestore.collection("events")
                .add(data)
                .addOnSuccessListener(e -> listener.OnCreatedEvent())
                .addOnFailureListener(e -> listener.OnNotCreatedEvent());
    }

    public void ChangeEvent(OnChangedEventListener l, String name, String title, String description, Boolean access){
        Map<String, Object> data = new HashMap<>();
        data.put("Title", title);
        data.put("Description", description);
        data.put("access", access);
        firebaseFirestore.collection("events")
                .document(name).update(data)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        l.OnChangedEvent();
                    else
                        l.OnNotChangedEvent();
                });
    }

    public void DeleteEvent(OnDeletedTaskListener l, String name){
        firebaseFirestore.collection("events")
                .document(name)
                .delete().addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        l.OnDeletedTask();
                    else
                        l.OnNotDeletedTask();
                });
    }

    public void AddTask(OnAddedTaskListener l, String eventName, String title, String description, String user){
        Map<String, Object> data = new HashMap<>();
        data.put("Title", title);
        data.put("Description", description);
        data.put("authors", user);
        firebaseFirestore.collection("events")
                .document(eventName)
                .collection("tasks")
                .add(data)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        l.OnAddedTask();
                    else
                        l.OnNotAddedTask();
                });
    }

    public void ChangeTask(OnChangedTaskListener l, String eventName, String taskName, String title, String description, String user){
        Map<String, Object> data = new HashMap<>();
        data.put("Title", title);
        data.put("Description", description);
        data.put("authors", user);
        firebaseFirestore.collection("events")
                .document(eventName)
                .collection("tasks")
                .document(taskName)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        l.OnChangedTask();
                    else
                        l.OnNotChangedTask();
                });
    }

    public void DeleteTask(OnDeletedTaskListener l, String eventName, String taskName){
        firebaseFirestore.collection("events")
                .document(eventName)
                .collection("tasks")
                .document(taskName)
                .delete()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        l.OnDeletedTask();
                    else
                        l.OnNotDeletedTask();
                });
    }

    public void SearchUserByEmail(OnSearchedUserListener l, String email){
        firebaseFirestore.collection("users").document(email).get()
                .addOnCompleteListener(task -> {
                    if (task.getResult().exists())
                        l.OnSearchedUser();
                    else
                        l.OnNotSearchedUser();
                });
    }

}
