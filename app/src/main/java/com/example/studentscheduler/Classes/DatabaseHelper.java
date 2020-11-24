package com.example.studentscheduler.Classes;

/**
 * DatabaseHelper Class (holds methods to create/populate/view/manipulate SQLite)
 *
 * @author David Roeske
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "scheduler.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TERM_TABLE = "Terms";
    private static final String COURSE_TABLE = "Courses";
    private static final String ASSESSMENT_TABLE = "Assessments";
    private static final String MENTOR_TABLE = "Mentors";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //***CREATE TERM TABLE***
    public void createTermTable(String tableName){
        this.getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS " + tableName + "(termId INTEGER PRIMARY KEY AUTOINCREMENT, termName TEXT, startDate DATE, endDate DATE)");
    }

    //***CREATE COURSE TABLE***
    public void createCourseTable(String tableName){
        this.getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS " + tableName + "(courseId INTEGER PRIMARY KEY AUTOINCREMENT, courseTitle TEXT, startDate DATE, endDate DATE, status TEXT, note TEXT, foreignKey INTEGER)");
    }

    //***CREATE ASSESSMENT TABLE***
    public void createAssessmentTable(String tableName){
        this.getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS " + tableName + "(assessmentId INTEGER PRIMARY KEY AUTOINCREMENT, assessmentName TEXT, assessmentType TEXT, dueDate DATE, foreignKey INTEGER)");
    }

    //***CREATE MENTOR TABLE***
    public void createMentorTable(String tableName){
        this.getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS " + tableName + "(mentorId INTEGER PRIMARY KEY AUTOINCREMENT, mentorName TEXT, phoneNumber TEXT, eMail TEXT, foreignKey INTEGER)");
    }

    //***INSERT RECORD METHOD***
    public void insertRecord(String sqlStatement) {
        this.getWritableDatabase().execSQL(sqlStatement);
    }

    //***ADD TERM RECORD***
    public long addTermRecord(String nameKey, String nameValue, String startKey, String startValue, String endKey, String endValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(nameKey, nameValue);
        values.put(startKey, startValue);
        values.put(endKey, endValue);

        return db.insert("Terms", null, values);
    }

    //***ADD COURSE RECORD***
    public long addCourseRecord(String titleKey, String titleValue, String startKey, String startValue, String endKey, String endValue,
                                String statusKey, String statusValue, String noteKey, String noteValue, String foreignKey, int foreignValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(titleKey, titleValue);
        values.put(startKey, startValue);
        values.put(endKey, endValue);
        values.put(statusKey, statusValue);
        values.put(noteKey, noteValue);
        values.put(foreignKey, foreignValue);

        return db.insert("Courses", null, values);
    }

    //***ADD ASSESSMENT RECORD***
    public long addAssessmentRecord(String nameKey, String nameValue, String typeKey, String typeValue, String dueDateKey, String dueDateValue,
                                    String foriegnKey, int foriegnValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(nameKey, nameValue);
        values.put(typeKey, typeValue);
        values.put(dueDateKey, dueDateValue);
        values.put(foriegnKey, foriegnValue);

        return db.insert("Assessments", null, values);
    }

    //***ADD MENTOR RECORD***
    public long addMentorRecord(String nameKey, String nameValue, String phoneNumberKey, String phoneNumberValue, String eMailKey, String eMailValue,
                                String foriegnKey, int foriegnValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(nameKey, nameValue);
        values.put(phoneNumberKey, phoneNumberValue);
        values.put(eMailKey, eMailValue);
        values.put(foriegnKey, foriegnValue);

        return db.insert("Mentors", null, values);
    }

    //***EDIT TERM RECORD***
    public void editTermData(String name, String startDate, String endDate, String termId){
        this.getWritableDatabase().execSQL("UPDATE Terms SET termName='" + name + "', startDate='" + startDate + "', endDate='" + endDate + "' WHERE termId='" + termId + "'");
    }

    //***EDIT MENTOR RECORD***
    public void editMentorData(String name, String phone, String email, String key, String mentorId){
        this.getWritableDatabase().execSQL("UPDATE Mentors SET mentorName='" + name + "', phoneNumber='" + phone + "', eMail='" + email + "', foreignKey='" + key + "' WHERE mentorId='" + mentorId + "'");
    }

    //***EDIT ASSESSMENT RECORD***
    public void editAssessmentData(String name, String type, String duedate, String key, String assessmentId){
        this.getWritableDatabase().execSQL("UPDATE Assessments SET assessmentName='" + name + "', assessmentType='" + type + "', dueDate='" + duedate + "', foreignKey='" + key + "' WHERE assessmentId='" + assessmentId + "'");
    }

    //***EDIT COURSE RECORD***
    public void editCourseData(String name, String startDate, String enddate, String status, String note, String key, String courseId){
        this.getWritableDatabase().execSQL("UPDATE Courses SET courseTitle='" + name + "', startDate='" + startDate + "', endDate='" + enddate + "', status='" + status + "', note='" + note + "', foreignKey='" + key + "' WHERE courseId='" + courseId + "'");
    }


    //***READ TERM RECORDS***
    public ArrayList<Term> readTermRecords(String sqlStatement) {
        ArrayList<Term> allTerms = new ArrayList<Term>();
        int termId;
        String termName;
        String startDate;
        String endDate;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlStatement, null);

        while (cursor.moveToNext()){
            termId = cursor.getInt(cursor.getColumnIndex("termId"));
            termName = cursor.getString(cursor.getColumnIndex("termName"));
            startDate = cursor.getString(cursor.getColumnIndex("startDate"));
            endDate = cursor.getString(cursor.getColumnIndex("endDate"));

            allTerms.add(new Term(termId, termName, startDate, endDate));
        }
        return allTerms;
    }

    //***READ COURSE RECORDS***
    public ArrayList<Course> readCourseRecords(String sqlStatement) {
        ArrayList<Course> allCourses = new ArrayList<Course>();
        int courseId;
        String courseTitle;
        String startDate;
        String endDate;
        String status;
        String note;
        int foreignKey;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlStatement, null);

        while (cursor.moveToNext()){
            courseId = cursor.getInt(cursor.getColumnIndex("courseId"));
            courseTitle = cursor.getString(cursor.getColumnIndex("courseTitle"));
            startDate = cursor.getString(cursor.getColumnIndex("startDate"));
            endDate = cursor.getString(cursor.getColumnIndex("endDate"));
            status = cursor.getString(cursor.getColumnIndex("status"));
            note = cursor.getString(cursor.getColumnIndex("note"));
            foreignKey = cursor.getInt(cursor.getColumnIndex("foreignKey"));

            allCourses.add(new Course(courseId, courseTitle, startDate, endDate, status, note, foreignKey));
        }
        return allCourses;
    }

    //***READ ASSESSMENT RECORDS***
    public ArrayList<Assessment> readAssessmentRecords(String sqlStatement) {
        ArrayList<Assessment> allAssessments = new ArrayList<Assessment>();
        int assessmentId;
        String assessmentName;
        String assessmentType;
        String dueDate;
        int foreignKey;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlStatement, null);

        while (cursor.moveToNext()){
            assessmentId = cursor.getInt(cursor.getColumnIndex("assessmentId"));
            assessmentName = cursor.getString(cursor.getColumnIndex("assessmentName"));
            assessmentType = cursor.getString(cursor.getColumnIndex("assessmentType"));
            dueDate = cursor.getString(cursor.getColumnIndex("dueDate"));
            foreignKey = cursor.getInt(cursor.getColumnIndex("foreignKey"));

            allAssessments.add(new Assessment(assessmentId, assessmentName, assessmentType, dueDate, foreignKey));
        }
        return allAssessments;
    }

    //***READ MENTOR RECORDS***
    public ArrayList<Mentor> readMentorRecords(String sqlStatement) {
        ArrayList<Mentor> allMentors = new ArrayList<Mentor>();
        int mentorId;
        String mentorName;
        String phoneNumber;
        String eMail;
        int foreignKey;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlStatement, null);

        while (cursor.moveToNext()){
            mentorId = cursor.getInt(cursor.getColumnIndex("mentorId"));
            mentorName = cursor.getString(cursor.getColumnIndex("mentorName"));
            phoneNumber = cursor.getString(cursor.getColumnIndex("phoneNumber"));
            eMail = cursor.getString(cursor.getColumnIndex("eMail"));
            foreignKey = cursor.getInt(cursor.getColumnIndex("foreignKey"));

            allMentors.add(new Mentor(mentorId, mentorName, phoneNumber, eMail, foreignKey));
        }
        return allMentors;
    }

    //***VIEW TERM DATA***
    public Cursor viewTermData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TERM_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    //***VIEW COURSE DATA***
    public Cursor viewCourseData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + COURSE_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    //***VIEW ASSESSMENT DATA***
    public Cursor viewAssessmentData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + ASSESSMENT_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    //***VIEW MENTOR DATA***
    public Cursor viewMentorData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + MENTOR_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    //***VIEW COURSE WHERE DATA***
    public Cursor viewCourseWhereData(String key) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + COURSE_TABLE + " WHERE foreignKey = " + key;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    //***VIEW ASSESSMENT WHERE DATA***
    public Cursor viewAssessmentWhereData(String key) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + ASSESSMENT_TABLE + " WHERE foreignKey = " + key;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    //***VIEW MENTOR WHERE DATA***
    public Cursor viewMentorWhereData(String key) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + MENTOR_TABLE + " WHERE foreignKey = " + key;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    //***GET TERM COUNT***
    public long getTermCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TERM_TABLE);
        db.close();
        return count;
    }

    //***GET COURSE COUNT***
    public long getCourseCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, COURSE_TABLE);
        db.close();
        return count;
    }

    //***GET ASSESSMENT COUNT***
    public long getAssessmentCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, ASSESSMENT_TABLE);
        db.close();
        return count;
    }

    //***GET MENTOR COUNT***
    public long getMentorCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, MENTOR_TABLE);
        db.close();
        return count;
    }

    //***ADD COURSE TO TERM***
    public void addCourseToTerm(String newForeignKey, String courseId){
        this.getWritableDatabase().execSQL("UPDATE Courses SET foreignKey=" + newForeignKey + " WHERE courseId=" + courseId);
    }

    //***ADD ASSESSMENT TO COURSE***
    public void addAssessmentToCourse(String newForeignKey, String assessmentId){
        this.getWritableDatabase().execSQL("UPDATE Assessments SET foreignKey=" + newForeignKey + " WHERE assessmentId=" + assessmentId);
    }

    //***ADD MENTOR TO COURSE***
    public void addMentorToCourse(String newForeignKey, String mentorId){
        this.getWritableDatabase().execSQL("UPDATE Mentors SET foreignKey=" + newForeignKey + " WHERE mentorId=" + mentorId);
    }

    //***DOES TERM CONTAIN COURSE METHOD***
    public boolean doesTermContainCourse(String termSearchId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String Query = ("SELECT * fROM Courses WHERE foreignKey=" + termSearchId);
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    //***DELETE RECORD***
    public void deleteRecord(String sqlStatement) {
        this.getWritableDatabase().execSQL(sqlStatement);
    }

    //***DELETE DATABASE***
    public void deleteDatabase() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TERM_TABLE);
        db.execSQL("delete from " + COURSE_TABLE);
        db.execSQL("delete from " + ASSESSMENT_TABLE);
        db.execSQL("delete from " + MENTOR_TABLE);
        db.execSQL("vacuum");
    }

}
