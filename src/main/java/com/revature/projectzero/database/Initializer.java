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
          "create schema if not exists pz; ",
          "create table if not exists pz.rootdir ( id serial primary key, name text not null unique ); ",
          "create table if not exists pz.branch ( id serial primary key, name text not null, rootdirid int references pz.rootdir(id), unique (name, rootdirid)); ",
          "create table if not exists pz.commit ( javaid text primary key, message text, rootdirid int references pz.rootdir(id)); ",
          "create table if not exists pz.bc_join ( branchid int references pz.branch(id), commitid text references pz.commit(javaid), ordernum int, primary key (branchid, ordernum) ); ",
          "create table if not exists pz.change (id serial primary key, typestring text, path text, content bytea, commitid text references pz.commit(javaid), rootdirid int references pz.rootdir(id), unique (typestring, path, content, commitid, rootdirid) ); ",
          "commit; ",
          "end; ",
          " $$;"));
  }

  private void insertRootDir() {
    sqlFunctions.add(String.join("",
          "create or replace procedure insert_root_dir(argname text) language plpgsql as ",
          "$$ ",
          "begin ",
          "insert into pz.rootdir (name) values (argname); ",
          "commit; ",
          "end; ",
          " $$;"));
  }

  private void getRootId() {
    sqlFunctions.add(String.join("",
          "create or replace function get_root_id(argname text) returns int as ",
          "$$ ",
          "declare ",
          "result integer; ",
          "begin ",
          "select r.id from pz.rootdir r where name = argname into result; ",
          "return result; ",
          "commit; ",
          "end; ",
          " $$ language plpgsql;"));
  }

  private void insertBranch() {
    sqlFunctions.add(String.join("", 
          "create or replace procedure insert_branch(argname text, rootid int) language plpgsql as ",
          "$$ ",
          "begin ",
          "insert into pz.branch (name, rootdirid) values (argname, rootid) on conflict (name, rootdirid) do nothing; ",
          "commit; ",
          "end; ",
          " $$;"));
  }
  
  private void getBranchId() {
    sqlFunctions.add(String.join("",
          "create or replace function get_branch_id(argname text, rootid int) returns int as ",
          "$$ ",
          "declare ",
          "result integer; ",
          "begin ",
          "select b.id from pz.branch b where name = argname and rootdirid = rootid into result; ",
          "return result; ",
          "commit; ",
          "end; ",
          " $$ language plpgsql;"));
  }

  private void insertCommit() {
    sqlFunctions.add(String.join("",
          "create or replace procedure insert_commit(argid text, argmessage text, rootid int) language plpgsql as ",
          "$$ ",
          "begin ",
          "insert into pz.commit (javaid, message, rootdirid) values (argid, argmessage, rootid) on conflict (javaid) do nothing; ",
          "commit; ",
          "end; ",
          " $$;"));
  }

/*
  private void getCommitId() {
    sqlFunctions.add(String.join("",
          "create or replace function get_commit_id(argid text) returns int as ",
          "$$ ",
          "declare ",
          "result integer; ",
          "begin ",
          "select c.id from pz.commit c where c.javaid = argid into result; ",
          "return result; ",
          "commit; ",
          "end; ",
          " $$ language plpgsql;"));
  }
*/
  private void insertBC() {
    sqlFunctions.add(String.join("",
          "create or replace procedure insert_bc(argbranchid int, argcommitid text, argorder int) language plpgsql as ",
          "$$ ",
          "begin ",
          "insert into pz.bc_join values (argbranchid, argcommitid, argorder) on conflict (branchid, ordernum) do nothing; ",
          "commit; ",
          "end; ",
          " $$;"));
  }

  private void insertChange() {
    sqlFunctions.add(String.join("",
          "create or replace procedure insert_change(argtype text, argpath text, argcontent bytea, argcommitid text, argrootid int) language plpgsql as ",
          "$$ ",
          "begin ",
          "insert into pz.change (typestring, path, content, commitid, rootdirid) values (argtype, argpath, argcontent, argcommitid, argrootid) on conflict (typestring, path, content, commitid, rootdirid) do nothing; ",
          "commit; ",
          "end; ",
          " $$;"));
  }

  private void dropTables() {
    sqlFunctions.add(String.join("",
          "create or replace procedure drop_tables() language plpgsql as ",
          "$$ ",
          "begin ",
          "drop table pz.change; ",
          "drop table pz.bc_join; ",
          "drop table pz.commit; ",
          "drop table pz.branch; ",
          "drop table pz.rootdir; ",
          "drop schema pz; ",
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
    //getCommitId();
    insertBC(); 
    insertChange();
    dropTables(); 
  }

  public void initialize(Connection conn) {
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

}
