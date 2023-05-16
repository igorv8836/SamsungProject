package ru.example.samsungproject.supportingClasses;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private String Title;
    private String Description;
    private String admin;
    private Boolean access;
    private List<User> members;

    public Event(String title, String description, String admin, Boolean access, List<User> members) {
        Title = title;
        Description = description;
        this.admin = admin;
        this.access = access;
        this.members = members;
    }

    public Event(DocumentSnapshot doc, List<DocumentSnapshot> members){
        Title = doc.getString("Title");
        Description = doc.getString("Description");
        admin = doc.getString("admin");
        access = doc.getBoolean("access");
        this.members = new ArrayList<>();

        for (DocumentSnapshot userDoc : members){
            this.members.add(new User(userDoc));
        }
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

    public Integer sizeMembers(){
        int i = 0;
        for (User user : members)
            if (user.isAgreed())
                i++;
        return i++;
    }
}
