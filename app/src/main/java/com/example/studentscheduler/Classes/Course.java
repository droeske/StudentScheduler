package com.example.studentscheduler.Classes;

/**
 * Course Class (holds information for courses)
 *
 * @author David Roeske
 */

public class Course {
    private int courseId;
    private String courseTitle;
    private String startDate;
    private String endDate;
    private int foreignKey;
    private String status;
    private String note;

    //Constructor
    public Course(int courseId, String courseTitle, String startDate, String endDate, String status, String note, int foreignKey) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.note = note;
        this.foreignKey = foreignKey;
    }

    //Getters and Setters
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(int foreignKey) {
        this.foreignKey = foreignKey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
