package com.fromsys.services;

import java.sql.*;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

public class PsqlDatasource {
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String hostName = "localhost";
    static final String portNumber = "5432";
    static final String databaseName = "alphabet_timemein";
    static final String dbUsername = "postgres";
    static final String dbPassword = "psql";
    static final String psqlURL = String.format("jdbc:postgresql://%s:%s/%s", hostName, portNumber, databaseName);

    public static DataSource setupDataSource() {
        BasicDataSource bdsPsql = new BasicDataSource();
        bdsPsql.setDriverClassName(JDBC_DRIVER);
        bdsPsql.setUrl(psqlURL);
        bdsPsql.setUsername(dbUsername);
        bdsPsql.setPassword(dbPassword);
        return bdsPsql;
    } //  public static DataSource setupDataSource

    public static void shutdownDataSource(DataSource tDataSource) throws SQLException {
        BasicDataSource bdsPsql = (BasicDataSource) tDataSource;
        bdsPsql.close();
    } // public static void shutdownDataSource(DataSource tDataSource)

} // public class PsqlDriver