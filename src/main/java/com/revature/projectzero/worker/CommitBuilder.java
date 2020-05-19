package com.revature.projectzero.worker;

import com.revature.projectzero.serializable.*;

import java.util.TreeMap;
import java.util.HashSet;

public class CommitBuilder {

  private static CommitBuilder commitBuilder;

  public Commit buildCommit(TreeMap<String, byte[]> previous, TreeMap<String, byte[]> current, String id, String message) {
    HashSet<Change> changes = new HashSet<Change>();
    for (String key : previous.keySet()) {
      if (!current.keySet().contains(key)) {
        if (previous.get(key) == null) {
          changes.add(new DeleteDirectory(key));
        } else {
          changes.add(new DeleteFile(key, previous.get(key)));
        }
      } else {
        if (previous.get(key) != current.get(key)) {
          changes.add(new DeleteFile(key, previous.get(key)));
          changes.add(new AddFile(key, current.get(key)));
        }
      }
    }

    for (String key : current.keySet()) {
      if (!previous.keySet().contains(key)) {
        if (current.get(key) == null) {
          changes.add(new AddDirectory(key));
        } else {
          changes.add(new AddFile(key, current.get(key)));
        }
      }
    }

    return new Commit(id, changes, message);
  }
}
