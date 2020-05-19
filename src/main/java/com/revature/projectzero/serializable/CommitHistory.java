package com.revature.projectzero.serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.io.Serializable;

/* Represents commit history tree */
public class CommitHistory implements Serializable {
  
  /* Figure This out Later */
  private HashMap<String, ArrayList<Commit>> commitTree;
  
  private String rootDirectory;
  
  private String headCommitIndex;
  private String headCommitId;
  private String headBranchId;

  // Getters to last 5 variables
  // Get Commit Names;
  // Add branch
  // Add commit to a branch

  private CommitHistory() { 
  
  }

  public CommitHistory(String rootDirectory) {
    this.rootDirectory = rootDirectory;
    commitTree = new HashMap<>();
  }

  public void addBranch(String branchName) {
    commitTree.put(branchName, new ArrayList<Commit>());
    this.headBranchId = branchName;
  }

  public void addCommit(Commit commit) {
    
  }

  public void setHead(String commitId) {
    
  }

}
