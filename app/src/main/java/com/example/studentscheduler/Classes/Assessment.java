package com.example.studentscheduler.Classes;

/**
 * Assessment Class (holds information for assessments)
 *
 * @author David Roeske
 */

public class Assessment {
    private int assessmentId;
    private String assessmentName;
    private String assessmentType;
    private String dueDate;
    private int foreignKey;

    //Constructor
    public Assessment(int assessmentId, String assessmentName, String assessmentType, String dueDate, int foreignKey) {
        this.assessmentId = assessmentId;
        this.assessmentName = assessmentName;
        this.assessmentType = assessmentType;
        this.dueDate = dueDate;
        this.foreignKey = foreignKey;
    }

    //Getters and Setters
    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public int getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(int foreignKey) {
        this.foreignKey = foreignKey;
    }
}
