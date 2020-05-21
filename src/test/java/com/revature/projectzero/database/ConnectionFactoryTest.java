package com.revature.projectzero.database;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

public class ConnectionFactoryTest {

  @Test
  public void test() throws SQLException {
    Connection c = ConnectionFactory.getConnection();
    c.close();
  }
}
