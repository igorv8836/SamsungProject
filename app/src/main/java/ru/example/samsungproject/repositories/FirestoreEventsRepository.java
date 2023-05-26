package ru.example.samsungproject.repositories;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.example.samsungproject.Database.FirestoreEventsDB;
import ru.example.samsungproject.interfaces.EventsListeners.OnAddedTaskListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnAddedTasksListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnChangedTaskListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnCreatedEventListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnDeletedTaskListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnLoadedEventListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnLoadedMyEventsListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnLoadedUsersForEventListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnSearchedEventListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnSearchedUserListener;
import ru.example.samsungproject.interfaces.OnUserAddedListener;
import ru.example.samsungproject.interfaces.UserListener.OnInvitationsLoadedListener;
import ru.example.samsungproject.supportingClasses.User;

public class FirestoreEventsRepository {
    private final FirestoreEventsDB firestoreEventsDB;
    private final FirebaseAuth mAuth;
    private final FirebaseUser currentUser;

    public FirestoreEventsRepository() {
        this.firestoreEventsDB = FirestoreEventsDB.getInstance();
        this.mAuth = FirebaseAuth.getInstance();
        this.currentUser = mAuth.getCurrentUser();
    }

    public void SearchUserByEmail(OnSearchedUserListener listener, String email, ArrayList<User> users){
        for (int i = 0; i < users.size(); i++){
            if (Objects.equals(users.get(i).getEmail(), email)){
                listener.OnAlreadyAdded(email);
                return;
            }
        }
        firestoreEventsDB.SearchUserByEmail(listener, email, Objects.equals(currentUser.getEmail(), email));
    }

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public void CreateNewEvent(OnCreatedEventListener l, String Title, String Description, String Date, List<User> users, Boolean access){
        firestoreEventsDB.CreateEvent(l, Title, Description, currentUser.getEmail(), access, users);
    }

    public void updateEvent(String eventId, String Title, String Description, String Date, List<User> users, List<User> newUsers, Boolean access){
        firestoreEventsDB.updateEvent(eventId, Title, Description, access, users, newUsers);
    }

    public void loadUsersForEvent(OnLoadedUsersForEventListener listener, String eventId){
        firestoreEventsDB.loadUsersForEvent(listener, eventId);
    }

    public void loadEvents(OnLoadedMyEventsListener l){
        firestoreEventsDB.loadEvents(l, currentUser.getEmail());
    }

    public void loadInvitations(OnInvitationsLoadedListener l){
        firestoreEventsDB.loadInvitation(l, currentUser.getEmail());
    }

    public void invitationAgree(String id){
        firestoreEventsDB.invitationAgree(id, currentUser.getEmail());
    }

    public void invitationDisagree(String id){
        firestoreEventsDB.invitationDisagree(id, currentUser.getEmail());
    }

    public void searchEvents(OnSearchedEventListener l, String name){
        firestoreEventsDB.searchEvents(l, name, currentUser.getEmail());
    }

    public void sendParticipation(String id){
        firestoreEventsDB.sendParticipation(id, currentUser.getEmail());
    }

    public void loadEvent(OnLoadedEventListener l, String id){
        firestoreEventsDB.loadEvent(l, id);
    }

    public void loadTasks(OnAddedTasksListener listener, String eventId){
        firestoreEventsDB.loadTasks(listener, eventId);
    }

    public void addTask(OnAddedTaskListener listener,
                        String eventId,
                        String title,
                        String description,
                        String user, int price){
        firestoreEventsDB.AddTask(listener, eventId, title, description, currentUser.getEmail(), price);
    }

    public void changeTask(OnChangedTaskListener listener,
                           String taskId,
                           String eventId,
                           String title,
                           String description,
                           int price,
                           boolean isCompleted,
                           int percentCompleted){
        firestoreEventsDB.ChangeTask(
                listener, taskId, eventId, title,
                description, price, isCompleted,
                percentCompleted);
    }

    public void changeCompleted(String taskId,
                                String eventId,
                                int percent,
                                boolean isCompleted){
        firestoreEventsDB.changeCompletedTask(eventId, taskId, percent, isCompleted);
    }

    public void deleteTask(OnDeletedTaskListener listener, String taskId, String eventId){
        firestoreEventsDB.DeleteTask(listener, eventId, taskId);
    }
}
