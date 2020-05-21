package com.revature.projectzero.database;

import com.revature.projectzero.serializable.*;

import java.util.ArrayList;
import java.util.TreeSet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainDAO {

  private Connection conn;
  private PreparedStatement ps;
  private ResultSet res;

  public MainDAO() {
    this.conn = ConnectionFactory.getConnection();
    (new Initializer()).initialize(conn);
  }

  public void closeConnection() {
    try {
      this.conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void createTables() {
    try {
      this.ps = this.conn.prepareStatement("call create_tables();");
      this.ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  public void dropTables() {
    try {
      this.ps = this.conn.prepareStatement("call drop_tables();");
      this.ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void insertRootDir(String rootDirName) {
    try {
      String query = "call insert_root_dir('" + rootDirName + "');";
      this.ps = this.conn.prepareStatement(query);
      this.ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public ArrayList<String> getRootDirs() {  
    ArrayList<String> rootDirs = new ArrayList<String>();
    try {
      String query = "select name from pz.rootdir;";
      this.ps = this.conn.prepareStatement(query);
      this.res = this.ps.executeQuery();
      while (this.res.next()) {
        rootDirs.add(this.res.getString("name"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      return rootDirs;
    }
  }

  public int getRootId(String rootDirName) {
    int rootId = -1;
    try {
      String query = "select get_root_id('" + rootDirName + "');";
      this.ps = this.conn.prepareStatement(query);
      this.res = this.ps.executeQuery();
      res.next();
      rootId = res.getInt("get_root_id");
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      return rootId;
    }
  } 

  public void insertBranch(String branchName, int rootDirId) {
    try {
      String query = "call insert_branch('" + branchName + "', " + rootDirId + ");";
      this.ps = this.conn.prepareStatement(query);
      this.ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public int getBranchId(String branchName, int rootDirId) {
    int branchId = -1;
    try {
      String query = "select get_branch_id('" + branchName + "', " + rootDirId + ");";
      this.ps = this.conn.prepareStatement(query);
      this.res = this.ps.executeQuery();
      res.next();
      branchId = res.getInt("get_branch_id");
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      return branchId;
    }
  }

  public ArrayList<String> getBranches(int rootDirId) {
    ArrayList<String> branches = new ArrayList<String>();
    try {
      String query = "select name from pz.branch where rootdirid = " + rootDirId + ";";
      this.ps = this.conn.prepareStatement(query);
      this.res = this.ps.executeQuery();
      while (res.next()) {
        branches.add(res.getString("name"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      return branches;
    }
  }

  public ArrayList<String> getCommitsOfBranch(int branchid) {
    ArrayList<String> commitIds = new ArrayList<String>();
    try {
      String query = "select commitid from pz.bc_join where branchid = " + branchid + " order by ordernum asc;"; 
      this.ps = this.conn.prepareStatement(query);
      this.res = this.ps.executeQuery();
      while (res.next()) {
        commitIds.add(res.getString("commitid"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      return commitIds;
    }
  }

  public void insertCommit(String commitId, String message, int rootDirId) {
    try {
      String query = "call insert_commit('" + commitId + "', '" + message + "', " + rootDirId + ");";
      this.ps = this.conn.prepareStatement(query);
      this.ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public String getMessage(String commitId) {
    String message = "";
    try {
      String query = "select message from pz.commit where javaid = '" + commitId + "';";
      this.ps = this.conn.prepareStatement(query);
      this.res = this.ps.executeQuery();
      this.res.next();
      message = this.res.getString("message");
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      return message;
    }
  }

  public void insertBC(int branchId, String commitId, int ordernum) {
    try {
      String query = "call insert_bc(" + branchId + ", '" + commitId + "', " + ordernum + ");";
      this.ps = this.conn.prepareStatement(query);
      this.ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  public String convertToHex(byte[] content) {
    if (content == null) { 
      return null; 
    }
    String s = "\\x";
    for (byte b : content) {
      s += String.format("%02x", b);
    }
    return s;
  }


  public byte[] convertToBytes(String dbcontent) {
    if (dbcontent == null) {
      return null;
    }
    String hex = dbcontent.substring(2); // Get rid of \x
    byte[] bytes = new byte[dbcontent.length() / 2];
    int i = 0;
    while (!hex.equals("")) {
      String byteHex = hex.substring(0,2);
      bytes[i] = Byte.parseByte(byteHex, 16); 
      hex = hex.substring(2);
      i++;
    }
    return bytes;
  }

  public void insertChange(String typestring, String path, byte[] content, String commitid, int rootDirId) {
    try {
      String hexContent = this.convertToHex(content);
      String query = "call insert_change('" + typestring + "', '" + path + "', '" + hexContent + "', '" + commitid + "', " + rootDirId + ");";
      this.ps = this.conn.prepareStatement(query);
      this.ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  public String getTypeString(Change c) {
    if (c.classOrder() == 1) { return "deletefile"; }
    else if (c.classOrder() == 2) { return "deletedirectory"; }
    else if (c.classOrder() == 3) { return "adddirectory"; }
    else { return "addfile"; }
  }

  public ArrayList<Integer> getChangeIds(String commitId, int rootDirId) {
    ArrayList<Integer> changeIds = new ArrayList<Integer>();
    try {
      String query = "select id from pz.change where commitid = '" + commitId + "' and rootdirid = " + rootDirId + ";";
      this.ps = this.conn.prepareStatement(query);
      this.res = this.ps.executeQuery();
      while(this.res.next()) {
        changeIds.add(this.res.getInt("id"));
      } 
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      return changeIds;
    }
  }

  public Change getChange(int changeId) {
    Change change = null;
    try {
      String query = "select * from pz.change where id = " + changeId + ";";
      this.ps = this.conn.prepareStatement(query);
      this.res = this.ps.executeQuery();
      this.res.next();
      if (this.res.getString("typestring").equals("addfile")) {
        String path = res.getString("path");
        byte[] content = this.convertToBytes(res.getString("content"));
        change = new AddFile(path, content);
      } else if (this.res.getString("typestring").equals("deletefile")) {
        String path = res.getString("path");
        byte[] content = this.convertToBytes(res.getString("content"));
        change = new DeleteFile(path, content);
      } else if (this.res.getString("typestring").equals("adddirectory")) {
        String path = res.getString("path");
        change = new AddDirectory(path);
      } else {
        String path = res.getString("path");
        change = new DeleteDirectory(path);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      return change;
    }
  }

  public void push(String branchName, CommitHistory history) {
    int rootId = history.getId();
    insertBranch(branchName, rootId);
    int branchId = getBranchId(branchName, rootId);
    ArrayList<String> remoteCommitIds = getCommitsOfBranch(branchId);
    ArrayList<Commit> branch = history.getCommitTree().get(branchName);
    for (int i = 0; i < branch.size(); i++) {
      Commit commit = branch.get(i);
      String commitId = commit.getId();
      if (i < remoteCommitIds.size()) {
        if (!remoteCommitIds.get(i).equals(commitId)) {
          break;
        }
      } else {
        insertCommit(commitId, commit.getMessage(), rootId);
        insertBC(branchId, commitId, i);
        for (Change change : commit.getChanges()) {
          String typestring = getTypeString(change);
          insertChange(typestring, change.getPathString(), change.getContent(), commitId, rootId);
        }
      }
    }
  }

  public void pull(String branchName, CommitHistory history) {
    int rootId = history.getId();
    int branchId = this.getBranchId(branchName, rootId);
    ArrayList<String> remoteCommitIds = getCommitsOfBranch(branchId);
    ArrayList<Commit> branch = history.getCommitTree().get(branchName);
    for (int i = 0; i < remoteCommitIds.size(); i++) {
      String commitId = remoteCommitIds.get(i);
      if (i < branch.size()) {
        if (!commitId.equals(branch.get(i).getId())) {
           break;
        }
      } else {
        TreeSet<Change> changes = new TreeSet<Change>();
        ArrayList<Integer> changeIds = getChangeIds(commitId, rootId);
        for (Integer id : changeIds) {
          changes.add(getChange(id));
        }
        String message = getMessage(commitId);
        branch.add(new Commit(commitId, changes, message));
      }
    } 
  }

}
