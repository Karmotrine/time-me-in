package com.fromsys;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Date;
import java.util.UUID;

public class AttendanceRecord implements Serializable {
    // Properties
    private int event_id;
    private UUID employee_id;
    private Date date;
    private Time log_in;
    private Time log_out;
    private int total_hours;
    // Generator
    public AttendanceRecord(){}
    // Getters
    public int getEvent_id() { return event_id; }
    public UUID getEmployee_id() { return employee_id; }
    public Date getDate() { return date; }
    public Time getLog_in() { return log_in; }
    public Time getLog_out() { return log_out; }
    public int getTotal_hours() { return total_hours; }
    // Setters
    public void setEvent_id(int tRecordId) { this.event_id = tRecordId; }
    public void setEmployee_id(UUID tEmployeeId) { this.employee_id = tEmployeeId; }
    public void setDate(Date tDate) { this.date = tDate; }
    public void setLog_in(Time tLoginTimestamp) { this.log_in = tLoginTimestamp; }
    public void setLog_out(Time tLogoutTimestamp) { this.log_out = tLogoutTimestamp; }
    public void setTotal_hours(int tTotalHours) { this.total_hours = tTotalHours; }

} // public class AttendanceRecord implements Serializable
