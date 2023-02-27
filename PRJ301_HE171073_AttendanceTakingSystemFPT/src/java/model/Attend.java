/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author ADMIN
 */
public class Attend {
    private String studentId;
    private int sessionId;
    private boolean status;
    private Date recordTime;   
//    give me the example output use the datetime in sql be converted by Date in java
//    give me the example output use the datetime in sql be converted by Timestamp in java
    private String comment;

    public Attend() {
    }

    public Attend(String studentId, int sessionId, boolean status, Date recordTime, String comment) {
        this.studentId = studentId;
        this.sessionId = sessionId;
        this.status = status;
        this.recordTime = recordTime;
        this.comment = comment;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
}
