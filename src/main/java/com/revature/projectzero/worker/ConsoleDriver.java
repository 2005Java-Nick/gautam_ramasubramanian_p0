package com.revature.projectzero.worker;

import com.revature.projectzero.serializable.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleDriver {

  private HashMap<String, String> lines;
  private HashMap<String, Boolean> shown;
  private Scanner scan = new Scanner(System.in);
  
  // Outline
  public String getTreeDiagram(CommitHistory history) {
    HashMap<String, ArrayList<Commit>> commitTree = history.getCommitTree();
    lines = new HashMap<>();
    shown = new HashMap<>();
    for (String key : commitTree.keySet()) {
      for (int i = 0; i < commitTree.get(key).size(); i++) {
        Commit c = commitTree.get(key).get(i);
        if (lines.get(c.getId()) == null) {       
          String s = "";
          int j = 0;
          while (j < i) { 
            s += "|";
            j++; 
          }
          s += "- " + "[" + c.getId() + "] " + "(" + c.getMessage() + ") " + key;
          lines.put(c.getId(), s);
          shown.put(c.getId(), false); 
        } else {
          String s = lines.get(c.getId());
          s += "," + key;
          lines.put(c.getId(), s);
        }
      }
    }
    lines.put(history.getHeadCommitId(), lines.get(history.getHeadCommitId()) + " <- HEAD");
    String result = "";
    for (String key : commitTree.keySet()) {
      for (Commit c : commitTree.get(key)) {
        if (!shown.get(c.getId())) {
          result += lines.get(c.getId());
          result += "\n";
          shown.put(c.getId(), true);
        }
      }
    }
    System.out.println("");
    System.out.println(result);
    return result;
  }

  public String newRootInput() {
    // TODO - Check whether name is unique in pz.rootdirs
    String newRemoteDir = "";
    while (newRemoteDir.equals("")) {
      System.out.print("Set Name of Remote Repository: ");
      newRemoteDir = scan.nextLine();
    }
    return newRemoteDir;
  } 

  public String cloneRootInput() {
    // TODO - Check whether name exists in pz.rootdirs
    String newRemoteDir = "";
    while (newRemoteDir.equals("")) {
      System.out.print("Set Name of Remote Repository: ");
      newRemoteDir = scan.nextLine();
    }
    return newRemoteDir;
  } 
  
  public String branchNameInput() {
    String branchName = "";
    while (branchName.equals("")) {
      System.out.print("Enter Branch Name: ");
      branchName = scan.nextLine();
    }
    return branchName;
  }

  public String commitMessageInput() {
    String commitMessage = "";
    while (commitMessage.equals("")) {
      System.out.print("Enter Commit Message: ");
      commitMessage = scan.nextLine();
    }
    return commitMessage;
  }

  public String commitIdInput() {
    String id = "";
    while (id.equals("")) {
      System.out.print("Restore to which commit (Type Id): ");
      id = scan.nextLine();
    }
    return id;
  }

} 
