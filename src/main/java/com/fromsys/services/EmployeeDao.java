package com.fromsys.services;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import static com.fromsys.services.PsqlDatasource.setupDataSource;

// Retrofit these stuff.
// Configure CustomRowProcessor https://solegaonkar.github.io/JavaApacheDbUtils.html
// Configure CustomDataSource

public class EmployeeDao {

    public static List<Employee> queryCreateEmployee (String tEmployeeName, String tEmployeeAddress,
                                                      String tEmployeeContact, String tEmployeeStatus) {
        List<Employee> lstResult = null;
        // BasicDataSource set-up
        DataSource dsPsql = setupDataSource();
        QueryRunner qrunEmployee = new QueryRunner();

        // JDBC + RSH set-up
        Connection connectStatus = null;
        ResultSetHandler<List<Employee>> rshEmployee = new BeanListHandler<Employee>(Employee.class);
        String querystrCreate = "INSERT INTO employee_details " +
                "(name, address, contact, employ_status)" +
                "VALUES (?, ?, ?, ?)";
        try {
            connectStatus = dsPsql.getConnection();
            lstResult = qrunEmployee.query(connectStatus, querystrCreate, rshEmployee,
                                           tEmployeeName, tEmployeeAddress,
                                           tEmployeeContact, tEmployeeStatus);
        } catch (SQLException objException) {
            objException.printStackTrace();
        } finally {
            try {
                if(connectStatus != null) DbUtils.close(connectStatus);
            } catch(Exception objException) {}
        }

        return lstResult;
    } // public static List<Employee> queryCreateEmployee (...)

    public static List<Employee> queryReadEmployee (int tEmployeeId) {
        List<Employee> lstResult = null;
        // BasicDataSource set-up
        DataSource dsPsql = setupDataSource();
        QueryRunner qrunEmployee = new QueryRunner();

        // JDBC + RSH set-up
        Connection connectStatus = null;
        ResultSetHandler<List<Employee>> rshEmployee = new BeanListHandler<Employee>(Employee.class);
        String querystrRead = "SELECT * FROM employee_details " +
                              "WHERE id=?";
        try {
            connectStatus = dsPsql.getConnection();
            lstResult = qrunEmployee.query(connectStatus, querystrRead, rshEmployee, tEmployeeId);
        } catch (SQLException objException) {
            objException.printStackTrace();
        } finally {
            try {
                if(connectStatus != null) DbUtils.close(connectStatus);
            } catch(Exception objException) {}
        }

        return lstResult;
    } // public static List<Employee> queryReadEmployee (...)

    public static List<Employee> queryUpdateEmployee (Employee tobjEmployee, int tEmployeeId, String tEmployeeName,
                                                      String tEmployeeAddress, String tEmployeeContact,
                                                      String tEmployeeStatus) {
        List<Employee> lstResult = null;
        // BasicDataSource set-up
        DataSource dsPsql = setupDataSource();
        QueryRunner qrunEmployee = new QueryRunner();

        // JDBC + RSH set-up
        Connection connectStatus = null;
        ResultSetHandler<List<Employee>> rshEmployee = new BeanListHandler<Employee>(Employee.class);
        String querystrUpdate = "UPDATE employee_details " +
                                "SET id=?, name=?, address=?, contact=?, status=? " +
                                "WHERE id=?";
        try {
            connectStatus = dsPsql.getConnection();
            lstResult = qrunEmployee.query(connectStatus, querystrUpdate, rshEmployee,
                                           tEmployeeId, tEmployeeName, tEmployeeAddress,
                                           tEmployeeContact, tEmployeeStatus, tobjEmployee.getId());
        } catch (SQLException objException) {
            objException.printStackTrace();
        } finally {
            try {
                if(connectStatus != null) DbUtils.close(connectStatus);
            } catch(Exception objException) {}
        }
        return lstResult;
    } // public static List<Employee> queryUpdateEmployee (...)

    public static List<Employee> queryDeleteEmployee (int tEmployeeId) {
        List<Employee> lstResult = null;
        // BasicDataSource set-up
        DataSource dsPsql = setupDataSource();
        QueryRunner qrunEmployee = new QueryRunner();

        // JDBC + RSH set-up
        Connection connectStatus = null;
        ResultSetHandler<List<Employee>> rshEmployee = new BeanListHandler<Employee>(Employee.class);
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
    } // public static List<Employee> queryDeleteEmployee (...)

} // public class EmployeeService
