module com.fromsys {
    requires java.sql;
    requires org.postgresql.jdbc;
    requires com.google.gson;
    requires webcam.capture;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires java.desktop;
    requires commons.dbcp2;
    requires org.apache.commons.pool2;
    requires commons.dbutils;
    requires java.management;
    requires java.logging;
    exports com.fromsys;
}
