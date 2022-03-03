package com.fromsys.services;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import java.util.UUID;

import static com.fromsys.services.PsqlDatasource.setupDataSource;

// https://commons.apache.org/proper/commons-dbutils/apidocs/org/apache/commons/dbutils/QueryRunner.html
// Fix some stuff in select/insert
//
public class EmployeeDao {

    public static void queryCreateEmployee (UUID tEmployeeId, String tEmployeeName,
                                            String tEmployeeAddress, String tEmployeeContact,
                                            String tEmployeeStatus) {
        // BasicDataSource set-up
        DataSource dsPsql = setupDataSource();
        QueryRunner qrunEmployee = new QueryRunner();

        // JDBC set-up
        Connection connectStatus = null;
        String querystrCreate = "INSERT INTO employee " +
                                "(id, name, address, contact, employ_status)" +
                                "VALUES (?, ?, ?, ?, ?)";
        try {
            connectStatus = dsPsql.getConnection();
            qrunEmployee.update(connectStatus, querystrCreate, tEmployeeId,
                                tEmployeeName, tEmployeeAddress,
                                tEmployeeContact, tEmployeeStatus);
        } catch (SQLException objException) {
            objException.printStackTrace();
        } finally {
            try {
                if(connectStatus != null) DbUtils.close(connectStatus);
            } catch(Exception objException) {}
        }

        //return lstResult;
    } // public static List<Employee> queryCreateEmployee (...)

    public static List<Employee> queryReadEmployee (UUID tEmployeeId) {
        List<Employee> lstResult = null;
        // BasicDataSource set-up
        DataSource dsPsql = setupDataSource();
        QueryRunner qrunEmployee = new QueryRunner();

        // JDBC + RSH set-up
        Connection connectStatus = null;
        ResultSetHandler<List<Employee>> rshEmployee = new BeanListHandler<Employee>(Employee.class);
        String querystrRead = "SELECT * FROM employee " +
                              "WHERE id=?";
        try {
            connectStatus = dsPsql.getConnection();
            lstResult = qrunEmployee.query(connectStatus, querystrRead,
                                           rshEmployee, tEmployeeId);
        } catch (SQLException objException) {
            objException.printStackTrace();
        } finally {
            try {
                if(connectStatus != null) DbUtils.close(connectStatus);
            } catch(Exception objException) {}
        }
        return lstResult;
    } // public static List<Employee> queryReadEmployee (...)

    public static void queryUpdateEmployee (UUID tEmployeeId, String tEmployeeName,
                                                      String tEmployeeAddress, String tEmployeeContact,
                                                      String tEmployeeStatus) {
        // BasicDataSource set-up
        DataSource dsPsql = setupDataSource();
        QueryRunner qrunEmployee = new QueryRunner();

        // JDBC set-up
        Connection connectStatus = null;
        String querystrUpdate = "UPDATE employee " +
                                "SET id=?, name=?, address=?, contact=?, employ_status=? " +
                                "WHERE id=?";
        try {
            connectStatus = dsPsql.getConnection();
            qrunEmployee.update(connectStatus, querystrUpdate,
                                tEmployeeId, tEmployeeName, tEmployeeAddress,
                                tEmployeeContact, tEmployeeStatus, tEmployeeId);
        } catch (SQLException objException) {
            objException.printStackTrace();
        } finally {
            try {
                if(connectStatus != null) DbUtils.close(connectStatus);
            } catch(Exception objException) {}
        }
    } // public static List<Employee> queryUpdateEmployee (...)

    public static void queryDeleteEmployee (UUID tEmployeeId) {
        // BasicDataSource set-up
        DataSource dsPsql = setupDataSource();
        QueryRunner qrunEmployee = new QueryRunner();

        // JDBC set-up
        Connection connectStatus = null;
        String querystrDelete = "DELETE FROM employee " +
                                "WHERE id=?";
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
    } // public static List<Employee> queryDeleteEmployee (...)

} // public class EmployeeService
