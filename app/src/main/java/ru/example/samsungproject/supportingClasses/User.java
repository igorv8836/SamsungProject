package ru.example.samsungproject.supportingClasses;

public class User {
    private String email;
    private String name;
    private boolean creator;
    private boolean admin;
    private boolean isAgreed;

    public User(String email, String name, boolean admin, boolean creator, boolean isAgreed) {
        this.email = email;
        this.name = name;
        this.admin = admin;
        this.creator = creator;
        this.isAgreed = isAgreed;
    }

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
