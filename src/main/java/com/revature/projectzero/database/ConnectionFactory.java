package com.revature.projectzero.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionFactory {

  private static Connection connection = null;

  private static Connection getNewConnection() {
      String url = System.getenv("DB_URL");
      String dbname = System.getenv("DB_DATABASE");
      String port = System.getenv("DB_PORT");
      String username = System.getenv("DB_USERNAME");
      String password = System.getenv("DB_PASSWORD");
      String fullURL = "jdbc:postgresql://" + url + ":" + port + "/" + dbname + "?";
  
      Connection conn = null;  
      try {
        Class.forName("org.postgresql.Driver");
        conn = DriverManager.getConnection(fullURL, username, password);
      } catch (ClassNotFoundException e) {
        // TODO
        e.printStackTrace();
      } catch (SQLException e) {
        // TODO
        e.printStackTrace();
      } 
      return conn;
  }

  public static Connection getConnection() {
    if (connection == null) {
      connection = getNewConnection();
    }
    return connection;
  }

}
