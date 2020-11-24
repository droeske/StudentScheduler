package com.example.studentscheduler.Classes;

/**
 * Term Class (holds information for Terms)
 *
 * @author David Roeske
 */

public class Term {
    private int termId;
    private String termName;
    private String startDate;
    private String endDate;

    //Constructor
    public Term(int termId, String termName, String startDate, String endDate) {
        this.termId = termId;
        this.termName = termName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //Getters and Setters
    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
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
}
