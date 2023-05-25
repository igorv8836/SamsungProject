package ru.example.samsungproject.Database;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ru.example.samsungproject.interfaces.EventsListeners.OnAddedTaskListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnAddedTasksListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnChangedEventListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnChangedTaskListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnCreatedEventListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnDeletedTaskListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnLoadedEventListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnLoadedMyEventsListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnSearchedEventListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnSearchedUserListener;
import ru.example.samsungproject.interfaces.UserListener.OnInvitationsLoadedListener;
import ru.example.samsungproject.supportingClasses.Event;
import ru.example.samsungproject.supportingClasses.Invitation;
import ru.example.samsungproject.supportingClasses.Task;
import ru.example.samsungproject.supportingClasses.User;

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

    public void CreateEvent(OnCreatedEventListener listener, String title, String description, String admin, Boolean access, List<User> users){
        if (title.isEmpty() || description.isEmpty()) {
            listener.OnNotCreatedEvent("Заполните все поля");
            return;
        }

        List<String> emails = new ArrayList<>();
        emails.add(admin);

        DocumentReference newDoc = firebaseFirestore.collection("events").document();

        Map<String, Object> data = new HashMap<>();
        data.put("Title", title);
        data.put("Description", description);
        data.put("admin", admin);
        data.put("access", access);
        data.put("users", emails);
        data.put("id", newDoc.getId());

        newDoc.set(data);

        CollectionReference members = newDoc.collection("members");
        for (User user : users){
            members.document(user.getEmail()).set(user);
            if (!admin.equals(user.getEmail()))
                SendInvitation(user.getEmail(), newDoc.getId());
        }
        listener.OnCreatedEvent();
    }

    public void updateEvent(String eventId, String title, String description, Boolean access, List<User> users){
        if (title.isEmpty() || description.isEmpty()) {
            return;
        }

        List<String> emails = new ArrayList<>();

        DocumentReference newDoc = firebaseFirestore.collection("events").document(eventId);

        Map<String, Object> data = new HashMap<>();
        data.put("Title", title);
        data.put("Description", description);
        data.put("access", access);
        data.put("users", emails);
        data.put("id", newDoc.getId());

        newDoc.update(data);

        CollectionReference members = newDoc.collection("members");
        for (User user : users){
            members.document(user.getEmail()).set(user);
            if (!admin.equals(user.getEmail()))
                SendInvitation(user.getEmail(), newDoc.getId());
        }
    }

    public void SendInvitation(String email, String id){
        firebaseFirestore.collection("users")
                .document(email)
                .update("invitations", FieldValue.arrayUnion(id));
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

    public void DeleteEvent(OnDeletedTaskListener l, String id){
        firebaseFirestore.collection("events")
                .document(id)
                .delete().addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        l.OnDeletedTask();
                    else
                        l.OnNotDeletedTask();
                });
    }

    public void AddTask(OnAddedTaskListener l, String eventId, String title, String description, String user, int price){
        Map<String, Object> data = new HashMap<>();
        data.put("title", title);
        data.put("description", description);
        data.put("author", user);
        data.put("price", price);
        data.put("isCompleted", false);
        data.put("percentCompleted", 0);
        data.put("id", "");
        firebaseFirestore.collection("events")
                .document(eventId)
                .collection("tasks")
                .add(data)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        l.OnAddedTask(task.getResult().getId(), user);
                        task.getResult().update("id", task.getResult().getId());
                    }else
                        l.OnNotAddedTask();
                });
    }

    public void ChangeTask(OnChangedTaskListener l,
                           String taskId,
                           String eventId,
                           String title,
                           String description,
                           int price,
                           boolean isCompleted,
                           int percentCompleted
                           ){
        Map<String, Object> data = new HashMap<>();
        data.put("title", title);
        data.put("description", description);
        data.put("price", price);
        data.put("isCompleted", isCompleted);
        data.put("percentCompleted", percentCompleted);
        firebaseFirestore.collection("events")
                .document(eventId)
                .collection("tasks")
                .document(taskId)
                .update(data)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        l.OnChangedTask();
                    else
                        l.OnNotChangedTask();
                });
    }

    public void changeCompletedTask(String eventId, String taskId, int percent, boolean isCompleted){
        HashMap<String, Object> temp = new HashMap<>();
        temp.put("percentCompleted", percent);
        temp.put("isCompleted", isCompleted);
        firebaseFirestore.collection("events").document(eventId).
                collection("tasks").document(taskId).update(temp);
    }

    public void DeleteTask(OnDeletedTaskListener l, String eventId, String taskId){
        firebaseFirestore.collection("events")
                .document(eventId)
                .collection("tasks")
                .document(taskId)
                .delete()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        l.OnDeletedTask();
                    else
                        l.OnNotDeletedTask();
                });
    }

    public void SearchUserByEmail(OnSearchedUserListener l, String email, boolean isCreator){
        if (email.isEmpty()) {
            l.OnNotSearchedUser();
            return;
        }
        firebaseFirestore.collection("users").document(email).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().exists())
                            l.OnSearchedUser(task.getResult().get("Name").toString(), isCreator);
                        else
                            l.OnNotSearchedUser();
                    } else
                        l.OnNotSearchedUser();
                });
    }

    public void loadEvents(OnLoadedMyEventsListener l, String email){
        firebaseFirestore.collection("events").whereArrayContains("users", email)
                .get().addOnSuccessListener(t -> {
                    List<Event> temp = t.toObjects(Event.class);
                    List<Event> myEvents = new ArrayList<>();
                    List<Event> otherEvents = new ArrayList<>();
                    int i = 0;
                    for (Event event : temp){
                        t.getDocuments().get(i).getReference().collection("members").get().addOnCompleteListener(d -> {
                            if (d.isSuccessful()) {
                                event.setMembersFromFirebase(d.getResult());
                                //System.out.println(123);
                            } else
                                l.onNotLoadedMyEvents("Error");
                            event.setCurrentUserEmail(email);
                            if (!event.getAdmin().equals(email)){
                                otherEvents.add(event);
                            } else {
                                myEvents.add(event);
                            }
                            l.onLoadedMyEvents(myEvents, otherEvents);
                        });
                        i++;
                    }
                });
    }

    public void loadEvent(OnLoadedEventListener l, String id){
        firebaseFirestore.collection("events").document(id).get().addOnCompleteListener(t -> {
            Event event = t.getResult().toObject(Event.class);
            firebaseFirestore.collection("events").document(id).collection("members").get().addOnCompleteListener(d -> {
                event.setMembersFromFirebase(d.getResult());
                l.onLoadedEvent(event);
            });
        });
    }

    public void loadInvitation(OnInvitationsLoadedListener l, String email){
        ArrayList<Invitation> invitations = new ArrayList<>();
        firebaseFirestore.collection("users").document(email).get().addOnCompleteListener(t -> {
            if (t.isSuccessful()){
                ArrayList<String> invitation = (ArrayList<String>) t.getResult().get("invitations");
                if (invitation == null) {
                    l.OnLoaded(invitations);
                    return;
                }
                for (String i : invitation){
                    firebaseFirestore.collection("events").document(i).get().addOnCompleteListener(d -> {
                        String sender = d.getResult().getString("admin");
                        String name = d.getResult().getString("Title");
                        int count = ((ArrayList<String>) (d.getResult().get("users"))).size();
                        if (d.isSuccessful()){
                            invitations.add(new Invitation(count, name, sender, d.getResult().getId()));
                        } else
                            l.OnNotLoaded(d.getException().toString());
                        l.OnLoaded(invitations);
                    });
                }
            } else {
                l.OnNotLoaded(t.getException().toString());
            }
        });
    }

    public void invitationAgree(String id, String email){
        firebaseFirestore.collection("events").document(id).update("users", FieldValue.arrayUnion(email));
        firebaseFirestore.collection("events").document(id).collection("members").document(email).update("agreed", true);
        firebaseFirestore.collection("users").document(email).get().addOnCompleteListener(t -> {
            if (t.isSuccessful()){
                List<String> temp = (List<String>) t.getResult().get("invitations");
                temp.remove(id);
                firebaseFirestore.collection("users").document(email).update("invitations", temp);
            }
        });
    }

    public void invitationDisagree(String id, String email){
        firebaseFirestore.collection("users").document(email).get().addOnCompleteListener(t -> {
            if (t.isSuccessful()){
                List<String> temp = (List<String>) t.getResult().get("invitations");
                temp.remove(id);
                firebaseFirestore.collection("users").document(email).update("invitations", temp);
            }
        });
    }

    public void searchEvents(OnSearchedEventListener l, String name, String currentEmail){
        firebaseFirestore.collection("events").
                whereGreaterThanOrEqualTo("Title", name).whereLessThan("Title", name + "\uf8ff").get().addOnCompleteListener(t -> {
                    if (t.isSuccessful()){
                        List<Event> temp = t.getResult().toObjects(Event.class);
                        List<Event> search_event = new ArrayList<>();
                        for (Event event : temp){
                            if (!event.getUsers().contains(currentEmail))
                                search_event.add(event);
                        }
                        l.onSearchedEvent(search_event);
                    } else{
                        l.onNotSearchedEvent(t.getException().toString());
                    }
                });
    }

    public void sendParticipation(String id, String email){
        //List<String> users;

        firebaseFirestore.collection("events").document(id).update("participations", FieldValue.arrayUnion(email));

//        firebaseFirestore.collection("events").document(id).get().addOnSuccessListener(t -> {
//            List<String> users = (List<String>) t.get("participations");
//            if (!users.isEmpty()){
//                firebaseFirestore.collection("events").document(id).update("participations", FieldValue.arrayUnion(email));
//            } else
//                firebaseFirestore.collection("events").document(id).set("participations", FieldValue.arrayUnion(users));
//        });
    }

    public void loadTasks(OnAddedTasksListener listener, String eventId){
        firebaseFirestore.
                collection("events").
                document(eventId).collection("tasks").get().
                addOnCompleteListener(t -> {
                    if (t.isSuccessful()){
                        List<Task> data = t.getResult().toObjects(Task.class);
                        for (Task task : data) {
                            task.setCompleted(task.getPercentCompleted() == 100);
                        }
                        listener.OnAddedTasks(data);
                    } else {
                        listener.OnNotAddedTasks();
                        Log.w("TAG", t.getException());
                    }

        });

    }

}
