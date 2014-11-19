package com.rulez.db;

import org.json.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by dukaa on 17.11.2014.
 */
public class ConnectionFactory {

    private static String url;
    private static String driver;
    private static String login;
    private static String password;

    static {
        Properties configFile = new Properties();
        InputStream inputStream = ConnectionFactory.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            configFile.load(inputStream);
            driver = configFile.getProperty("db.driver");
            url = configFile.getProperty("db.url");
            login = configFile.getProperty("db.login");
            password = configFile.getProperty("db.password");
        } catch (IOException e) {
            /*NOP*/
        }
    }

    public static Connection getConnection() {
        Connection connection;
        try {
            Class.forName(driver).newInstance();
            connection = DriverManager.getConnection(url, login, password);
            return connection;
        } catch (Exception e) { /*NOP*/ }
        return null;
    }
}
