package com.fromsys.services;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import static com.fromsys.services.PsqlDatasource.setupDataSource;
import java.util.UUID;

/*
 *  public static List<AttendanceRecord> queryLogin (int tEmployeeId)
 *  public static List<AttendanceRecord> queryLogout (int tEmployeeId)
 *  public static List<AttendanceRecord> queryUpdateHours (int tEmployeeId)
 *  public static List<AttendanceRecord> queryReadTimestamp (int tEmployeeId)
 *  public static List<AttendanceRecord> queryDeleteTimestamp (int tEmployeeId)
 */

public class AttendanceRecordDao {

    public static void queryLogin (UUID tEmployeeId) {
        List<AttendanceRecord> lstResult = null;
        // BasicDataSource set-up

        DataSource dsPsql = setupDataSource();
        QueryRunner qrunRecord = new QueryRunner();

        // JDBC + RSH set-up
        Connection connectStatus = null;
        ResultSetHandler<List<AttendanceRecord>> rshAttendanceRecord =
                new BeanListHandler<AttendanceRecord>(AttendanceRecord.class);

        String querystrCreate = "INSERT INTO time_record " +
                                "(employee_id, date, log_in) " +
                                "VALUES (?, CURRENT_DATE, CURRENT_TIME)";
        try {
            connectStatus = dsPsql.getConnection();
            qrunRecord.update(connectStatus, querystrCreate, tEmployeeId);
        } catch (SQLException objException) {
            objException.printStackTrace();
        } finally {
            try {
                if(connectStatus != null) DbUtils.close(connectStatus);
            } catch(Exception objException) {}
        }
    } // public static List<Employee> queryLogin

    public static void queryLogout (UUID tEmployeeId) {
        List<AttendanceRecord> lstResult = null;
        // BasicDataSource set-up
        DataSource dsPsql = setupDataSource();
        QueryRunner qrunRecord = new QueryRunner();

        // JDBC + RSH set-up
        Connection connectStatus = null;
        ResultSetHandler<List<AttendanceRecord>> rshAttendanceRecord =
                new BeanListHandler<AttendanceRecord>(AttendanceRecord.class);
        // Check first if there is existing record for the day:
        String queryifLoggedin = "SELECT * FROM time_record " +
                                 "WHERE employee_id=? AND date=CURRENT_DATE" +
                                 "AND log_out IS NULL";

        String querystrUpdate = "UPDATE time_record " +
                                "SET log_out=CURRENT_TIME " +
                                "WHERE employee_id=? AND date=CURRENT_DATE" +
                                "AND log_out IS NULL";
        try {
            connectStatus = dsPsql.getConnection();
            qrunRecord.update(connectStatus, querystrUpdate, tEmployeeId);
        } catch (SQLException objException) {
            objException.printStackTrace();
        } finally {
            try {
                if(connectStatus != null) DbUtils.close(connectStatus);
            } catch(Exception objException) {}
        }
    } // public static List<Employee> queryLogout (...)

    public static List<AttendanceRecord> queryUpdateHours (UUID tEmployeeId) {
        List<AttendanceRecord> lstResult = null;
        // BasicDataSource set-up
        DataSource dsPsql = setupDataSource();
        QueryRunner qrunEmployee = new QueryRunner();

        // JDBC + RSH set-up
        Connection connectStatus = null;
        ResultSetHandler<List<AttendanceRecord>> rshAttendanceRecord =
                new BeanListHandler<AttendanceRecord>(AttendanceRecord.class);
        String querystrUpdate = "UPDATE time_record " +
                                "SET total_hours=DATE_PART('hour', log_out - log_in )::int " +
                                "WHERE employee_id=? AND date=CURRENT_DATE";
        try {
            connectStatus = dsPsql.getConnection();
            qrunEmployee.update(connectStatus, querystrUpdate,
                    rshAttendanceRecord, tEmployeeId);
        } catch (SQLException objException) {
            objException.printStackTrace();
        } finally {
            try {
                if(connectStatus != null) DbUtils.close(connectStatus);
            } catch(Exception objException) {}
        }
        return lstResult;
    } // public static List<Employee> queryLogout (...)

    public static List<AttendanceRecord> queryReadTimestamp (UUID tEmployeeId) {
        List<AttendanceRecord> lstResult = null;
        // BasicDataSource set-up
        DataSource dsPsql = setupDataSource();
        QueryRunner qrunEmployee = new QueryRunner();

        // JDBC + RSH set-up
        Connection connectStatus = null;
        ResultSetHandler<List<AttendanceRecord>> rshAttendanceRecord =
                new BeanListHandler<AttendanceRecord>(AttendanceRecord.class);
        String querystrRead = "SELECT * FROM time_record " +
                              "WHERE employee_id=?";
        try {
            connectStatus = dsPsql.getConnection();
            lstResult = qrunEmployee.query(connectStatus, querystrRead,
                                           rshAttendanceRecord, tEmployeeId);
        } catch (SQLException objException) {
            objException.printStackTrace();
        } finally {
            try {
                if(connectStatus != null) DbUtils.close(connectStatus);
            } catch(Exception objException) {}
        }

        return lstResult;
    } // public static List<Employee> queryReadTimestamp (...)

    public static List<AttendanceRecord> queryDeleteTimestamp (UUID tEmployeeId) {
        List<AttendanceRecord> lstResult = null;
        // BasicDataSource set-up
        DataSource dsPsql = setupDataSource();
        QueryRunner qrunEmployee = new QueryRunner();

        // JDBC + RSH set-up
        Connection connectStatus = null;
        ResultSetHandler<List<AttendanceRecord>> rshEmployee =
                new BeanListHandler<AttendanceRecord>(AttendanceRecord.class);
        String querystrDelete = "DELETE FROM time_record " +
                                "WHERE employee_id=?";
        try {
            connectStatus = dsPsql.getConnection();
            lstResult = qrunEmployee.query(connectStatus, querystrDelete, rshEmployee, tEmployeeId);
        } catch (SQLException objException) {
            objException.printStackTrace();
        } finally {
            try {
                if(connectStatus != null) DbUtils.close(connectStatus);
            } catch(Exception objException) {}
        }
        return lstResult;
    } // public static List<Employee> queryDeleteTimestamp (...)

} // public class timeDAO
