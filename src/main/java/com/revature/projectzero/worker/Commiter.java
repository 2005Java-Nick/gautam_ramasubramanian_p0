package com.revature.projectzero.worker;

import com.revature.projectzero.serializable.Commit;
import com.revature.projectzero.serializable.CommitHistory;

import java.nio.file.FileSystem;
import java.util.HashMap;

public class Commiter {

  public static String getRandomId() {
    // TODO - Fix this
    String s = "";
    String alphabet = "abcdef012345";
    int i = 6;
    while (i > 0) {
      int j = Math.round(11 * (float)Math.random()); 
      s += alphabet.substring(j, j+1);
      i--;
    }
    return s;
  }

  public boolean commit(FileSystem fs, String rootDir, String imageDir, CommitHistory history, String message) {
    HashMap<String, byte[]> snapshot = (new SnapshotFactory(fs, rootDir, imageDir)).buildSnapshot();
    Commit commit = (new CommitBuilder()).buildCommit(history.getSnapshot(), snapshot, Commiter.getRandomId(), message);
    history.addCommit(commit, snapshot);
    return true;
  }
}
