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

public class MainDAOTest {
  
  MainDAO dao;
  byte[] content = { 1,2 };
  @Test
  public void byteHexConversionTest() {
    dao = new MainDAO();
    assertEquals(dao.convertToHex(content), "\\x0102");
    assertEquals(dao.convertToBytes("\\x0102")[0], (byte) 1);
    assertEquals(dao.convertToBytes("\\x0102")[1], (byte) 2);
  }
}
