package ru.example.samsungproject.supportingClasses;

public class Invitation {
    private int countMembers;
    private String eventName;
    private String adminEmail;
    private String eventId;

    public Invitation(int countMembers, String eventName, String adminEmail, String eventId) {
        this.countMembers = countMembers;
        this.eventName = eventName;
        this.adminEmail = adminEmail;
        this.eventId = eventId;
    }

    public int getCountMembers() {
        return countMembers;
    }

    public void setCountMembers(int countMembers) {
        this.countMembers = countMembers;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String geteventId() {
        return eventId;
    }

    public void seteventId(String eventId) {
        this.eventId = eventId;
    }
}
