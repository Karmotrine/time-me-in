package com.fromsys.services;

import java.io.Serializable;
import java.sql.Timestamp;

public class AttendanceRecord implements Serializable {
    // Properties
    private int recordId;
    private int employeeId;
    private Timestamp loginTimestamp;
    private Timestamp logoutTimestamp;
    private int totalHours;
    // Generator
    public AttendanceRecord(){}
    // Getters
    public int getRecordId() { return recordId; }
    public int getEmployeeId() { return employeeId; }
    public Timestamp getLoginTimestamp() { return loginTimestamp; }
    public Timestamp getLogoutTimestamp() { return logoutTimestamp; }
    public int getTotalHours() { return totalHours; }
    // Setters
    public void setRecordId(int tRecordId) { this.recordId = tRecordId; }
    public void setEmployeeId(int tEmployeeId) { this.employeeId = tEmployeeId; }
    public void setLoginTimestamp(Timestamp tLoginTimestamp) { this.loginTimestamp = tLoginTimestamp; }
    public void setLogoutTimestamp(Timestamp tLogoutTimestamp) { this.logoutTimestamp = tLogoutTimestamp; }
    public void setTotalHours(int tTotalHours) { this.totalHours = tTotalHours; }

} // public class AttendanceRecord implements Serializable
