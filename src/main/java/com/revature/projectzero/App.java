package com.revature.projectzero;

import org.apache.log4j.Logger;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Scanner;

import com.revature.projectzero.serializable.*;
import com.revature.projectzero.database.*;
import com.revature.projectzero.worker.*;

public class App {
    
    private static Logger logger = Logger.getLogger(App.class);
    public static MainDAO dao;
    public static Serializer serializer;
    public static ConsoleDriver consoleDriver;
    public static CommitHistory history;
    public static FileSystem fs;
    public static String rootDirPath; 
    public static String saveImageDir; 
    public static String saveImagePath;
      
    public static String getHelpMessage() {
      return "Help Message";
    }

    public static void initializeObjects() {
      dao = new MainDAO(); 
      consoleDriver = new ConsoleDriver();
      serializer = new Serializer();
      fs = FileSystems.getDefault();
      rootDirPath = System.getProperty("user.dir");
      saveImageDir = ".projectzero.snapshots"; 
      saveImagePath = fs.getPath(".projectzero.snapshots", ".projectzero.snapshot").toString();
    }

    public static void checkCorrectNumArgs(String[] args) {
      if (args.length != 1) {
        logger.warn("Program is used without arguments or with too many arguments"); 
        System.out.println(getHelpMessage());
        System.exit(0);
      }
    }

    public static void initAction() {
      String rootDirName = consoleDriver.newRootInput();
      dao.insertRootDir(rootDirName);
      int rootId = dao.getRootId(rootDirName);
      Commit commit = new Commit(Commiter.getRandomId(), new TreeSet<Change>(), "initial commit");
      history = new CommitHistory(rootDirPath, rootId, "master", commit);
      serializer.createDirectory(saveImageDir);
      serializer.writeToFile(saveImagePath, history);
    }

    public static void cloneAction() {
      String rootDirName = consoleDriver.cloneRootInput();
      int rootId = dao.getRootId(rootDirName);
      ArrayList<String> branchNames = dao.getBranches(rootId);
      int branchId = dao.getBranchId(branchNames.get(0), rootId);
      String commitId = dao.getCommitsOfBranch(branchId).get(0);
      String commitMessage = dao.getMessage(commitId);
      Commit commit = new Commit(commitId, new TreeSet<Change>(), commitMessage);
      history = new CommitHistory(rootDirPath, rootId, branchNames.get(0), commit);
      for (int i = 1; i < branchNames.size(); i++) {
        history.addBranch(branchNames.get(i));
      }
      consoleDriver.getTreeDiagram(history); 
      serializer.createDirectory(saveImageDir);
      serializer.writeToFile(saveImagePath, history);
    }

    public static void pushAction() {
        history = serializer.readFromFile(saveImagePath);
        consoleDriver.getTreeDiagram(history);
        String branchName = consoleDriver.branchNameInput();
        dao.push(branchName, history);
        serializer.writeToFile(saveImagePath, history);
    }

    public static void pullAction() {
      history = serializer.readFromFile(saveImagePath);
      String branchName = consoleDriver.branchNameInput();
      dao.pull(branchName, history);
      consoleDriver.getTreeDiagram(history); 
      serializer.writeToFile(saveImagePath, history);   
    }

    public static void commitAction() {
      history = serializer.readFromFile(saveImagePath);
      consoleDriver.getTreeDiagram(history); 
      String message = consoleDriver.commitMessageInput(); 
      (new Commiter()).commit(fs, rootDirPath, saveImageDir, history, message);
      consoleDriver.getTreeDiagram(history); 
      serializer.writeToFile(saveImagePath, history);
    }

    public static void restoreAction() {
      history = serializer.readFromFile(saveImagePath); 
      consoleDriver.getTreeDiagram(history); 
      String id = consoleDriver.commitIdInput();
      (new Restorer()).restoreToCommit(fs, rootDirPath, history, id);
      consoleDriver.getTreeDiagram(history); 
      serializer.writeToFile(saveImagePath, history);
    }


    public static void main( String[] args ) throws Exception {
      
      checkCorrectNumArgs(args);
      initializeObjects();
      dao.createTables();

      if (args[0].equals("init")) {
        initAction();
      }
      else if (args[0].equals("clone")) {
        cloneAction();
      }
      else if (args[0].equals("push")) {
        pushAction();
      }
      else if (args[0].equals("pull")) {
        pullAction();
      }
      else if (args[0].equals("commit")) {
        commitAction();
      }
      else if (args[0].equals("restore")) {
        restoreAction(); 
      } 
      else if (args[0].equals("debug")) {
        // Print Out anything here for debugging
        history = serializer.readFromFile(saveImagePath); 
      } 
      dao.closeConnection();    
    }

}
