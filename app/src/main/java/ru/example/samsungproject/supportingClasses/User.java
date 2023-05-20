package ru.example.samsungproject.supportingClasses;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.PropertyName;

public class User {
    @PropertyName("email")
    private String email;
    @PropertyName("name")
    private String name;
    @PropertyName("creator")
    private boolean creator;
    @PropertyName("admin")
    private boolean admin;
    @PropertyName("agreed")
    private boolean isAgreed;

    public User(){

    }

    public User(String email, String name, boolean admin, boolean creator, boolean isAgreed) {
        this.email = email;
        this.name = name;
        this.admin = admin;
        this.creator = creator;
        this.isAgreed = isAgreed;
    }

//    public User(DocumentSnapshot user){
//        email = user.getString("email");
//        name = user.getString("name");
//        admin = Boolean.TRUE.equals(user.getBoolean("admin"));
//        creator = Boolean.TRUE.equals(user.getBoolean("creator"));
//        isAgreed = Boolean.TRUE.equals(user.getBoolean("agreed"));
//    }

    public boolean isCreator() {
        return creator;
    }

    public void setCreator(boolean creator) {
        this.creator = creator;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isAgreed() {
        return isAgreed;
    }

    public void setAgreed(boolean agreed) {
        isAgreed = agreed;
    }

}
