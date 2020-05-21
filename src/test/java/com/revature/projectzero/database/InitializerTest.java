package com.revature.projectzero.database;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

public class InitializerTest {

  Connection conn;
  Initializer init;
  PreparedStatement ps;
  ResultSet res;
  int rootId = -1;
  int branchId = -1;
  String commitId = "";

  @Before
  public void before() throws SQLException {
    conn = ConnectionFactory.getConnection();
    init = new Initializer();
    init.initialize(this.conn);
    this.ps = this.conn.prepareStatement("call create_tables();");
    this.ps.executeUpdate();
  }

  private void testRootTable() throws SQLException {
    ps = this.conn.prepareStatement("call insert_root_dir('rootDir1');");
    ps.executeUpdate();

    ps = this.conn.prepareStatement("select * from pz.rootdir;");
    res = ps.executeQuery();
    res.next();
    assertEquals("Database does not insert into root_dir", res.getString("name"), "rootDir1");
    
    ps = this.conn.prepareStatement("select get_root_id('rootDir1');");
    res = ps.executeQuery();
    res.next();
    this.rootId = res.getInt("get_root_id");
    assertTrue("Get Root ID function does not work", this.rootId != -1);
  }

  private void testBranchTable() throws SQLException {
    ps = this.conn.prepareStatement("call insert_branch('branch1', ?);");
    ps.setInt(1, this.rootId); 
    ps.executeUpdate();

    ps = this.conn.prepareStatement("select * from pz.branch;");
    res = ps.executeQuery();
    res.next();
    assertEquals("Database does not insert into branch - Name is wrong", res.getString("name"), "branch1");
    assertEquals("Database does not insert into branch - RootId is wrong", res.getInt("rootdirid"), this.rootId);

    ps = this.conn.prepareStatement("select get_branch_id('branch1', ?);");
    ps.setInt(1, this.rootId);
    res = ps.executeQuery();
    res.next();
    this.branchId = res.getInt("get_branch_id");
    assertTrue("Get Branch ID function does not work", this.branchId != -1);
  }

  private void testCommitTable() throws SQLException {
    this.commitId = "123456";
    ps = this.conn.prepareStatement("call insert_commit('" + commitId + "', 'commit1', ?);");
    ps.setInt(1, this.rootId);
    ps.executeUpdate();

    ps = this.conn.prepareStatement("select * from pz.commit;");
    res = ps.executeQuery();
    res.next();
    assertEquals("Database does not insert into commit - Message is wrong", res.getString("message"), "commit1");
    assertEquals("Database does not insert into commit - JavaID is wrong", res.getString("javaid"), "123456");
    assertEquals("Database does not insert into commit - RootID is wrong", res.getInt("rootdirid"), this.rootId);
  }

  private void testBCTable() throws SQLException {
    ps = this.conn.prepareStatement("call insert_bc(?, ?, 0);");
    ps.setInt(1, this.branchId);
    ps.setString(2, this.commitId);
    ps.executeUpdate();

    ps = this.conn.prepareStatement("select * from pz.bc_join;");
    res = ps.executeQuery();
    res.next();
    assertEquals("Database does not insert into bc - Branch Id is wrong", res.getInt("branchid"), this.branchId);
    assertEquals("Database does not insert into bc - Commit Id is wrong", res.getString("commitid"), this.commitId);
    assertEquals("Database does not insert into bc - Order is wrong", res.getInt("ordernum"), 0);
  }

  private void testChangeTable() throws SQLException {
    ps = this.conn.prepareStatement("call insert_change('deletedirectory', 'd1', null, ?, ?);");
    ps.setString(1, this.commitId);
    ps.setInt(2, this.rootId);
    ps.executeUpdate();

    ps = this.conn.prepareStatement("call insert_change('addfile', 'f1', '\\x0123456789', ?, ?);");
    ps.setString(1, this.commitId);
    ps.setInt(2, this.rootId);
    ps.executeUpdate();

    ps = this.conn.prepareStatement("select * from pz.change;");
    res = ps.executeQuery();
    res.next();
    assertEquals("Database does not insert into change - type is wrong", res.getString("typestring"), "deletedirectory");
    assertEquals("Database does not insert into change - path is wrong", res.getString("path"), "d1");
    assertEquals("Database does not insert into change - content is wrong", res.getString("content"), null);
    assertEquals("Database does not insert into change - commitid is wrong", res.getString("commitid"), this.commitId);
    assertEquals("Database does not insert into change - rootid is wrong", res.getInt("rootdirid"), this.rootId);
  
    res.next();
    assertEquals("Database does not insert into change - type is wrong", res.getString("typestring"), "addfile");
    assertEquals("Database does not insert into change - path is wrong", res.getString("path"), "f1");
    assertEquals("Database does not insert into change - content is wrong", res.getString("content"), "\\x0123456789");
    assertEquals("Database does not insert into change - commitid is wrong", res.getString("commitid"), this.commitId);
    assertEquals("Database does not insert into change - rootid is wrong", res.getInt("rootdirid"), this.rootId);
  
  }

  @Test
  public void testTables() throws SQLException {
    testRootTable();
    testBranchTable();
    testCommitTable(); 
    testBCTable();
    testChangeTable();
  }

  @After
  public void after() throws SQLException {
    PreparedStatement ps = this.conn.prepareStatement("call drop_tables();");
    ps.executeUpdate();
    this.conn.close();
  }

}
