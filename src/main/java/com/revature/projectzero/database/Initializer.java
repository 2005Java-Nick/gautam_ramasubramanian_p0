package com.revature.projectzero.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

public class Initializer {

  private ArrayList<String> sqlQueries;

  private void createTables() {
    sqlQueries.add(String.join("", 
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
    sqlQueries.add(String.join("",
          "create or replace procedure insert_root_dir(argname text) language plpgsql as ",
          "$$ ",
          "begin ",
          "insert into pz.rootdir values (argname); ",
          "commit; ",
          "end; ",
          " $$;"));
  }

  private void getRootId() {
    sqlQueries.add(String.join("",
          "create or replace function get_root_id(argname text) returns int as ",
          "$$ ",
          "begin ",
          "return query select r.id from pz.rootdir r where name = argname; ",
          "commit; ",
          "end; ",
          " $$ language plpgsql;"));
  }

  private void insertBranch() {
    sqlQueries.add(String.join("", 
          "create or replace procedure insert_branch(argname text, rootid int) language plpgsql as ",
          "$$ ",
          "begin ",
          "insert into pz.branch values (argname, rootid); ",
          "commit; ",
          "end; ",
          " $$;"));
  }
  
  private void getBranchId() {
    sqlQueries.add(String.join("",
          "create or replace function get_branch_id(argname text, rootid int) returns int as ",
          "$$ ",
          "begin ",
          "return query select b.id from pz.branch b where name = argname and rootdirid = rootid; ",
          "commit; ",
          "end; ",
          " $$;"));
  }

  private void insertCommit() {
    sqlQueries.add(String.join("",
          "create or replace procedure insert_commit(argid int, argmessage text, rootid int) language plpgsql as ",
          "$$ ",
          "begin ",
          "insert into pz.commit values (argid, argmessage, rootid); ",
          "commit; ",
          "end; ",
          " $$;"));
  }

  private void getCommitId() {
    sqlQueries.add(String.join("",
          "create or replace function get_commit_id(argid int) returns int as ",
          "$$ ",
          "begin ",
          "return query select c.id from pz.commit c where c.javaid = argid; ",
          "commit; ",
          "end; ",
          " $$;"));
  }

  private void insertBC() {
    sqlQueries.add(String.join("",
          "create or replace procedure insert_bc(argbranchid int, argcommitid int, argorder int) language plpgsql as ",
          "$$ ",
          "begin ",
          "insert into pz.bc_join values (argbranchid, argcommitid, argorder); ",
          "commit; ",
          "end; ",
          " $$;"));
  }

  private void insertChange() {
    sqlQueries.add(String.join("",
          "create or replace procedure insert_change() language plpgsql as ",
          "$$ ",
          "begin ",
          "insert into pz.change values (argtype, argpath, argcontent, commitid, rootid); ",
          "commit; ",
          "end; ",
          " $$;"));
  }

  public Initializer() {
    sqlQueries = new ArrayList<String>();
    createTables();
    insertRootDir();
    getRootId();
    insertBranch();
    getBranchId();
    insertCommit();
    getCommitId();
    insertBC(); 
    insertChange(); 
  }

  public void initialize(Connection conn) {
     
  }

}
