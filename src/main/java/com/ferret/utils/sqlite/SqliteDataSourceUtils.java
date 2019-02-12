package com.ferret.utils.sqlite;


import org.springframework.stereotype.Component;
import org.sqlite.SQLiteConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author cc;
 * @since 2018/4/15;
 */
@Component
public class SqliteDataSourceUtils {

    public Connection connect(String dbPath) {
        SQLiteConnection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = (SQLiteConnection) DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }




}
