package com.revature.projectzero.serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;

/* Represents commit history tree */
public class CommitHistory implements Serializable {
  
  /* Figure This out Later */
  private HashMap<String, ArrayList<Commit>> commitTree;

  private HashMap<String, byte[]> snapshot;
  
  private String rootDirectory;
  private int rootId;
  
  private int headCommitIndex;
  private String headCommitId;
  private String headBranchId;

  private int branchNameIndex = 0;

  // Getters to last 5 variables
  // Get Commit Names;
  // Add branch
  // Add commit to a branch

  private CommitHistory() { 
  
  }

  public CommitHistory(String rootDirectory, int rootId, String branchName, Commit initialcommit) {
    this.rootDirectory = rootDirectory;
    this.rootId = rootId;
    commitTree = new HashMap<>();
    commitTree.put(branchName, new ArrayList<Commit>());
    this.headBranchId = branchName;
    commitTree.get(this.headBranchId).add(initialcommit);    
    this.headCommitId = initialcommit.getId();
    this.headCommitIndex = 0;
    this.snapshot = new HashMap<>();
  }

  private String getBranchName() {
    this.branchNameIndex++;
    return "branch_" + this.branchNameIndex;
  }

  public void addBranch(String branchName) {
    commitTree.put(branchName, new ArrayList<Commit>());
    for (int i = 0; i <= this.headCommitIndex; i++) {
      Commit c = commitTree.get(this.headBranchId).get(i);
      commitTree.get(branchName).add(c);
    }
  }

  public void addCommit(Commit commit, HashMap<String, byte[]> snapshot) {
    if (this.headCommitIndex != commitTree.get(this.headBranchId).size() - 1) {
      String branchName = this.getBranchName();
      this.addBranch(branchName);
      this.headBranchId = branchName;
    }
    commitTree.get(this.headBranchId).add(commit);
    this.headCommitIndex++;
    this.headCommitId = commit.getId();
    this.snapshot = snapshot;
  }

  public boolean setHead(String commitId) {
    for (String key: commitTree.keySet()) {
      for (int i = 0; i < commitTree.get(key).size(); i++) {
        Commit c = commitTree.get(key).get(i);
        if (c.getId().equals(commitId)) {
          this.headBranchId = key;
          this.headCommitIndex = i;
          this.headCommitId = commitId;
          return true;
        }
      }
    }
    return false;
  }

  public ArrayList<Commit> getCurrentBranch() {
    return this.commitTree.get(this.headBranchId);
  }

  public ArrayList<Commit> getBranch(String branchName) {
    return this.commitTree.get(branchName);
  }

  public HashMap<String, ArrayList<Commit>> getCommitTree() {
    return this.commitTree;
  }

  public int getHeadCommitIndex() {
    return this.headCommitIndex;
  }

  public String getHeadCommitId() {
    return this.headCommitId;
  }

  public HashMap<String, byte[]> getSnapshot() {
    return this.snapshot;
  }

  public int getId() {
    return this.rootId;
  }
}
