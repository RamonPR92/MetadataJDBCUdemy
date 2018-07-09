/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rperez
 */
public class Conexion {
    
    private static String JDBC_DRIVER;
    private static String JDBC_URL;
    private static String JDBC_USER;
    private static String JDBC_PASS;
    private static Driver driver = null;
    private static String JDBC_FILE_NAME = "jdbc";
    
    public static Properties loadProperties(String file){
        Properties prop = new Properties();
        ResourceBundle bundle = ResourceBundle.getBundle(file);
        Enumeration enumeration = bundle.getKeys();
        String key = null;
        while(enumeration.hasMoreElements()){
            key = (String) enumeration.nextElement();
            prop.put(key, bundle.getObject(key));
        }
        
        JDBC_DRIVER = prop.getProperty("driver");
        JDBC_URL = prop.getProperty("url");
        JDBC_USER = prop.getProperty("user");
        JDBC_PASS = prop.getProperty("pass");
        
        return prop;
    }
    
    public static synchronized Connection getConnection() throws SQLException{
        if(driver == null){
            try {
                loadProperties(JDBC_FILE_NAME);
                Class jdbcDriverClass = Class.forName(JDBC_DRIVER);
                driver = (Driver)jdbcDriverClass.newInstance();
                DriverManager.registerDriver(driver);
            } catch (Exception e) {
                System.out.println("Fallo en cargar el driver JDBC");
                e.printStackTrace();
            }
        }
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
    }
    
    public static void close(ResultSet resultSet){
        try {
            if(resultSet != null){
                resultSet.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
     public static void close(PreparedStatement preparedStatement){
        try {
            if(preparedStatement != null){
                preparedStatement.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
      public static void close(Connection connection){
        try {
            if(connection != null){
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
