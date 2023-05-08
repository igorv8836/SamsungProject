package ru.example.samsungproject.supportingClasses;

public class User {
    private String email;
    private String name;
    private boolean admin;

    public User(String email, String name, boolean admin) {
        this.email = email;
        this.name = name;
        this.admin = admin;
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
}
