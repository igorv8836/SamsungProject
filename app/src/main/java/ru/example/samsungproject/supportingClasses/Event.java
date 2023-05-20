package ru.example.samsungproject.supportingClasses;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.PropertyName;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Event {
    @PropertyName("Title")
    private String Title;
    @PropertyName("Description")
    private String Description;
    @PropertyName("admin")
    private String admin;
    @PropertyName("access")
    private Boolean access;

    private List<User> members;

    @PropertyName("users")
    private List<String> users;

    @PropertyName("id")
    private String id;

    private String currentUserEmail;

    public Event(){
    }

    public Event(String title, String description, String admin, Boolean access, List<User> members) {
        Title = title;
        Description = description;
        this.admin = admin;
        this.access = access;
        this.members = members;
    }

    public Event(String title, String admin, Boolean access, List<User> members, String id) {
        Title = title;
        this.admin = admin;
        this.access = access;
        this.members = members;
        this.id = id;
    }

    public void setMembersFromFirebase(QuerySnapshot a){
        members = a.toObjects(User.class);
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public Boolean getAccess() {
        return access;
    }

    public void setAccess(Boolean access) {
        this.access = access;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public Integer sizeMembers(){
        if (members == null)
            return 0;
        int i = 0;
        for (User user : members)
            if (user.isAgreed())
                i++;
        return i++;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrentUserEmail() {
        return currentUserEmail;
    }

    public void setCurrentUserEmail(String myEvent) {
        this.currentUserEmail = myEvent;
    }
}
