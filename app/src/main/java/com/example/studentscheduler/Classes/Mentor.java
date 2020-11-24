package com.example.studentscheduler.Classes;

/**
 * Mentor Class (holds information for mentors)
 *
 * @author David Roeske
 */

public class Mentor {
    private int mentorId;
    private String mentorName;
    private String phoneNumber;
    private String eMail;
    private int foreignKey;

    //Constructor
    public Mentor(int mentorId, String mentorName, String phoneNumber, String eMail, int foreignKey) {
        this.mentorId = mentorId;
        this.mentorName = mentorName;
        this.phoneNumber = phoneNumber;
        this.eMail = eMail;
        this.foreignKey = foreignKey;
    }

    //Getters and Setters
    public int getMentorId() {
        return mentorId;
    }

    public void setMentorId(int mentorId) {
        this.mentorId = mentorId;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public int getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(int foreignKey) {
        this.foreignKey = foreignKey;
    }
}
