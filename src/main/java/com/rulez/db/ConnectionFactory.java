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

    public static Connection getConnection() {
        Connection connection;
        Properties configFile = new Properties();
        try {
            InputStream inputStream = ConnectionFactory.class.getClassLoader().getResourceAsStream("config.properties");
            configFile.load(inputStream);
            if (inputStream != null) {
                Class.forName(configFile.getProperty("db.driver")).newInstance();
                connection = DriverManager.getConnection(configFile.getProperty("db.url"),
                        configFile.getProperty("db.login"),
                        configFile.getProperty("db.password"));
                return connection;
            }
        } catch (Exception e) { /*NOP*/ }
        return null;
    }
}
