package com.fromsys;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.UUID;

import static com.fromsys.PsqlDatasource.*;

public class AttendanceRecordDao {

    public static void queryLogin (UUID tEmployeeId) {
        // BasicDataSource set-up

        DataSource dsPsql = setupDataSource();
        QueryRunner qrunRecord = new QueryRunner();

        // JDBC + RSH set-up
        Connection connectStatus = null;
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
    } // public static void queryLogin (UUID tEmployeeId)

    public static void queryLogout (UUID tEmployeeId) {
        // BasicDataSource set-up
        DataSource dsPsql = setupDataSource();
        QueryRunner qrunRecord = new QueryRunner();

        // JDBC + RSH set-up
        Connection connectStatus = null;
        String querystrUpdate = "UPDATE time_record " +
                                "SET log_out=CURRENT_TIME " +
                                "WHERE employee_id=? AND date=CURRENT_DATE " +
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
    } // public static void queryLogout (UUID tEmployeeId)

    public static ResultSet queryViewAll () {
        // JDBC + RSH set-up
        Connection connectStatus = null;
        ResultSet lstResult = null;
        String querystrRead = "SELECT * FROM time_record";
        try {
            connectStatus = DriverManager.getConnection(PsqlDatasource.psqlURL,dbUsername,dbPassword);
            Statement stmtView = connectStatus.createStatement();
            lstResult = stmtView.executeQuery(querystrRead);
        } catch (SQLException objException) {
            objException.printStackTrace();
        } finally {
            try {
                if(connectStatus != null) connectStatus.close();
            } catch(Exception objException) {}
        }
        return lstResult;
    } // public static ResultSet queryViewAll ()

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
    } // public static List<AttendanceRecord> queryReadTimestamp (UUID tEmployeeId)

    public static void queryUpdateHours (UUID tEmployeeId) {
        // BasicDataSource set-up
        DataSource dsPsql = setupDataSource();
        QueryRunner qrunEmployee = new QueryRunner();

        // JDBC set-up
        Connection connectStatus = null;
        String querystrUpdate = "UPDATE time_record " +
                                "SET total_hours=DATE_PART('hour', log_out - log_in )::int " +
                                "WHERE employee_id=? AND date=CURRENT_DATE";
        try {
            connectStatus = dsPsql.getConnection();
            qrunEmployee.update(connectStatus, querystrUpdate, tEmployeeId);
        } catch (SQLException objException) {
            objException.printStackTrace();
        } finally {
            try {
                if(connectStatus != null) DbUtils.close(connectStatus);
            } catch(Exception objException) {}
        }
    } // public static void queryUpdateHours (UUID tEmployeeId)


    public static void queryDeleteTimestamp (UUID tEmployeeId) {
        // BasicDataSource set-up
        DataSource dsPsql = setupDataSource();
        QueryRunner qrunEmployee = new QueryRunner();

        // JDBC + RSH set-up
        Connection connectStatus = null;
        String querystrDelete = "DELETE FROM time_record " +
                "WHERE employee_id=?";
        try {
            connectStatus = dsPsql.getConnection();
            qrunEmployee.update(connectStatus, querystrDelete, tEmployeeId);
        } catch (SQLException objException) {
            objException.printStackTrace();
        } finally {
            try {
                if(connectStatus != null) DbUtils.close(connectStatus);
            } catch(Exception objException) {}
        }
    } // public static void queryDeleteTimestamp (UUID tEmployeeId)


    public static Boolean queryIsLoggedin (UUID tEmployeeId) {
        List<AttendanceRecord> lstResult = null;
        // BasicDataSource set-up
        DataSource dsPsql = setupDataSource();
        QueryRunner qrunEmployee = new QueryRunner();

        // JDBC + RSH set-up
        Connection connectStatus = null;
        ResultSetHandler<List<AttendanceRecord>> rshAttendanceRecord =
                new BeanListHandler<AttendanceRecord>(AttendanceRecord.class);
        String querystrRead = "SELECT * FROM time_record " +
                              "WHERE employee_id=? AND DATE=CURRENT_DATE " +
                              "AND log_in IS NOT NULL AND log_out IS NULL";
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
        return (lstResult.size() != 0);
    } // public static Boolean queryIsLoggedin (UUID tEmployeeId)

    public static Boolean queryIsLoggedout (UUID tEmployeeId) {
        List<AttendanceRecord> lstResult = null;
        // BasicDataSource set-up
        DataSource dsPsql = setupDataSource();
        QueryRunner qrunEmployee = new QueryRunner();

        // JDBC + RSH set-up
        Connection connectStatus = null;
        ResultSetHandler<List<AttendanceRecord>> rshAttendanceRecord =
                new BeanListHandler<AttendanceRecord>(AttendanceRecord.class);
        String querystrRead = "SELECT * FROM time_record " +
                "WHERE employee_id=? AND DATE=CURRENT_DATE " +
                "AND log_in IS NOT NULL AND log_out IS NOT NULL";
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
        return (lstResult.size() != 0);
    } // public static Boolean queryIsLoggedout (UUID tEmployeeId)

    public static Boolean queryHasRecord (UUID tEmployeeId) {
        List<AttendanceRecord> lstResult = null;
        // BasicDataSource set-up
        DataSource dsPsql = setupDataSource();
        QueryRunner qrunEmployee = new QueryRunner();

        // JDBC + RSH set-up
        Connection connectStatus = null;
        ResultSetHandler<List<AttendanceRecord>> rshAttendanceRecord =
                new BeanListHandler<AttendanceRecord>(AttendanceRecord.class);
        String querystrRead = "SELECT * FROM time_record " +
                "WHERE employee_id=? AND DATE=CURRENT_DATE ";
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
        return (lstResult.size() != 0);
    } // public static Boolean queryHasRecord (UUID tEmployeeId)

} // public class timeDAO
