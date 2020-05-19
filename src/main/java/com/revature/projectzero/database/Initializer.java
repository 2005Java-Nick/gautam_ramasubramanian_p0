package com.revature.projectzero.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

public class Initializer {

  private ArrayList<String> sqlFunctions;

  private void createTables() {
    sqlFunctions.add(String.join("", 
          "create or replace procedure create_tables() language plpgsql as ",
          "$$ ",
          "begin ",
          "create type change_type as enum ('addfile','deletefile','adddirectory','deletedirectory'); ",
          "create table if not exists pz.rootdir ( id serial primary key, name text not null ); ",
          "create table if not exists pz.branch ( id serial primary key, name text not null, rootdirid int references pz.rootdir(id) ); ",
          "create table if not exists pz.commit ( id serial primary key, javaid int, message text, rootdirid int references pz.rootdir(id) ); ",
          "create table if not exists pz.bc_join ( branchid int references pz.branch(id), commitid int references pz.commit(id), order int, primary key(branchid, commitid) ); ",
          "create table if not exists pz.change (id serial primary key, type change_type, path text, content bytea, commitid references pz.commit(id), rootdirid int references pz.rootdir(id) ); ",
          "commit; ",
          "end; ",
          " $$;"));
  }

  private void insertRootDir() {
    sqlFunctions.add(String.join("",
          "create or replace procedure insert_root_dir(argname text) language plpgsql as ",
          "$$ ",
          "begin ",
          "insert into pz.rootdir values (argname); ",
          "commit; ",
          "end; ",
          " $$;"));
  }

  private void getRootId() {
    sqlFunctions.add(String.join("",
          "create or replace function get_root_id(argname text) returns int as ",
          "$$ ",
          "begin ",
          "return query select r.id from pz.rootdir r where name = argname; ",
          "commit; ",
          "end; ",
          " $$ language plpgsql;"));
  }

  private void insertBranch() {
    sqlFunctions.add(String.join("", 
          "create or replace procedure insert_branch(argname text, rootid int) language plpgsql as ",
          "$$ ",
          "begin ",
          "insert into pz.branch values (argname, rootid); ",
          "commit; ",
          "end; ",
          " $$;"));
  }
  
  private void getBranchId() {
    sqlFunctions.add(String.join("",
          "create or replace function get_branch_id(argname text, rootid int) returns int as ",
          "$$ ",
          "begin ",
          "return query select b.id from pz.branch b where name = argname and rootdirid = rootid; ",
          "commit; ",
          "end; ",
          " $$;"));
  }

  private void insertCommit() {
    sqlFunctions.add(String.join("",
          "create or replace procedure insert_commit(argid int, argmessage text, rootid int) language plpgsql as ",
          "$$ ",
          "begin ",
          "insert into pz.commit values (argid, argmessage, rootid); ",
          "commit; ",
          "end; ",
          " $$;"));
  }

  private void getCommitId() {
    sqlFunctions.add(String.join("",
          "create or replace function get_commit_id(argid int) returns int as ",
          "$$ ",
          "begin ",
          "return query select c.id from pz.commit c where c.javaid = argid; ",
          "commit; ",
          "end; ",
          " $$;"));
  }

  private void insertBC() {
    sqlFunctions.add(String.join("",
          "create or replace procedure insert_bc(argbranchid int, argcommitid int, argorder int) language plpgsql as ",
          "$$ ",
          "begin ",
          "insert into pz.bc_join values (argbranchid, argcommitid, argorder); ",
          "commit; ",
          "end; ",
          " $$;"));
  }

  private void insertChange() {
    sqlFunctions.add(String.join("",
          "create or replace procedure insert_change() language plpgsql as ",
          "$$ ",
          "begin ",
          "insert into pz.change values (argtype, argpath, argcontent, commitid, rootid); ",
          "commit; ",
          "end; ",
          " $$;"));
  }

  private void dropTables() {
    sqlFunctions.add(String.join("",
          "create or replace procedure drop_tables() language plpgsql as ",
          "$$ ",
          "begin ",
          "drop table pz.rootdir; ",
          "drop table pz.branch; ",
          "drop table pz.commit; ",
          "drop table pz.bc_join; ",
          "drop table pz.change; ",
          "commit; ",
          "end; ",
          " $$;"));
  }

  public Initializer() {
    sqlFunctions = new ArrayList<String>();
    createTables();
    insertRootDir();
    getRootId();
    insertBranch();
    getBranchId();
    insertCommit();
    getCommitId();
    insertBC(); 
    insertChange();
    dropTables(); 
  }

  private void initialize(Connection conn) {
    PreparedStatement ps;
    for (String query : sqlFunctions) {
      try {
        ps = conn.prepareStatement(query);
        ps.executeUpdate();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  private void callProcedure(String procedure_name, Connection conn) {
    PreparedStatement ps;
    try {
      ps = conn.prepareStatement("call ?();");
      ps.setString(1, procedure_name);
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private int callIntValuedFunction(String function_name, Connection conn) {
    PreparedStatement ps;
    int result = -1;
    try {
      ps = conn.prepareStatement("select ?();");
      ps.setString(1, function_name);
      ResultSet rs = ps.executeQuery();
      rs.next();
      result = rs.getInt("id");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }

}
