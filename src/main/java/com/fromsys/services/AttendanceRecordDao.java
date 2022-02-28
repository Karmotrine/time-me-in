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

/*
 *  public static List<AttendanceRecord> queryLogin (int tEmployeeId)
 *  public static List<AttendanceRecord> queryLogout (int tEmployeeId)
 *  public static List<AttendanceRecord> queryUpdateHours (int tEmployeeId)
 *  public static List<AttendanceRecord> queryReadTimestamp (int tEmployeeId)
 *  public static List<AttendanceRecord> queryDeleteTimestamp (int tEmployeeId)
 */

public class AttendanceRecordDao {

    public static List<AttendanceRecord> queryLogin (int tEmployeeId) {
        List<AttendanceRecord> lstResult = null;
        // BasicDataSource set-up

        DataSource dsPsql = setupDataSource();
        QueryRunner qrunEmployee = new QueryRunner();

        // JDBC + RSH set-up
        Connection connectStatus = null;
        ResultSetHandler<List<AttendanceRecord>> rshAttendanceRecord =
                new BeanListHandler<AttendanceRecord>(AttendanceRecord.class);
        String querystrCreate = "INSERT INTO time_record " +
                                "(employeeId, loginTimeStamp) " +
                                "VALUES (?, CURRENT_TIMESTAMP)";
        try {
            connectStatus = dsPsql.getConnection();
            lstResult = qrunEmployee.query(connectStatus, querystrCreate,
                                           rshAttendanceRecord, tEmployeeId);
        } catch (SQLException objException) {
            objException.printStackTrace();
        } finally {
            try {
                if(connectStatus != null) DbUtils.close(connectStatus);
            } catch(Exception objException) {}
        }

        return lstResult;
    } // public static List<Employee> queryLogin

    public static List<AttendanceRecord> queryLogout (int tEmployeeId) {
        List<AttendanceRecord> lstResult = null;
        // BasicDataSource set-up
        DataSource dsPsql = setupDataSource();
        QueryRunner qrunEmployee = new QueryRunner();

        // JDBC + RSH set-up
        Connection connectStatus = null;
        ResultSetHandler<List<AttendanceRecord>> rshAttendanceRecord =
                new BeanListHandler<AttendanceRecord>(AttendanceRecord.class);
        String querystrUpdate = "UPDATE employee_details " +
                                "SET logoutTimeStamp=CURRENT_TIMESTAMP" +
                                "WHERE employeeId=?";
        try {
            connectStatus = dsPsql.getConnection();
            lstResult = qrunEmployee.query(connectStatus, querystrUpdate,
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

    public static List<AttendanceRecord> queryUpdateHours (int tEmployeeId) {
        List<AttendanceRecord> lstResult = null;
        // BasicDataSource set-up
        DataSource dsPsql = setupDataSource();
        QueryRunner qrunEmployee = new QueryRunner();

        // JDBC + RSH set-up
        Connection connectStatus = null;
        ResultSetHandler<List<AttendanceRecord>> rshAttendanceRecord =
                new BeanListHandler<AttendanceRecord>(AttendanceRecord.class);
        String querystrUpdate = "UPDATE employee_details " +
                                "SET totalHours=(days_diff * 24 + DATE_PART('hour',logoutTimestamp-loginTimestamp ), " +
                                "WHERE employeeId=?";
        try {
            connectStatus = dsPsql.getConnection();
            lstResult = qrunEmployee.query(connectStatus, querystrUpdate,
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

    public static List<AttendanceRecord> queryReadTimestamp (int tEmployeeId) {
        List<AttendanceRecord> lstResult = null;
        // BasicDataSource set-up
        DataSource dsPsql = setupDataSource();
        QueryRunner qrunEmployee = new QueryRunner();

        // JDBC + RSH set-up
        Connection connectStatus = null;
        ResultSetHandler<List<AttendanceRecord>> rshAttendanceRecord =
                new BeanListHandler<AttendanceRecord>(AttendanceRecord.class);
        String querystrRead = "SELECT * FROM employee_details " +
                              "WHERE employeeId=?";
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

    public static List<AttendanceRecord> queryDeleteTimestamp (int tEmployeeId) {
        List<AttendanceRecord> lstResult = null;
        // BasicDataSource set-up
        DataSource dsPsql = setupDataSource();
        QueryRunner qrunEmployee = new QueryRunner();

        // JDBC + RSH set-up
        Connection connectStatus = null;
        ResultSetHandler<List<AttendanceRecord>> rshEmployee =
                new BeanListHandler<AttendanceRecord>(AttendanceRecord.class);
        String querystrDelete = "DELETE FROM employee_details " +
                                "WHERE id=?";
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
