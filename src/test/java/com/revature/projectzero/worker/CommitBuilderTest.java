package com.revature.projectzero.worker;

import static org.junit.Assert.assertTrue;

import com.revature.projectzero.serializable.*;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.Arrays;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

// TODO: Test private methods
// Add more tests later
public class CommitBuilderTest {

  private TreeMap<String, byte[]> getPrevious() {
    TreeMap<String, byte[]> previous = new TreeMap<String,byte[]>();
    previous.put("d1", null);
    previous.put("d1/f1", new byte[5]);
    previous.put("d2", null);
    previous.put("d2/f2", new byte[4]);
    previous.put("d2/f3", new byte[4]);
    return previous;
  }

  private TreeMap<String, byte[]> getCurrent(TreeMap<String, byte[]> previous) {
    TreeMap<String, byte[]> current = new TreeMap<String, byte[]>();
    current.put("d3", null);
    current.put("d3/f1", previous.get("d1/f1"));
    current.put("d3/f3", new byte[19]);
    current.put("d2", null);
    current.put("d2/f2", previous.get("d2/f2"));
    current.put("d2/f3", new byte[4]);
    return current;
  }

  private Commit getCommit(TreeMap<String, byte[]> previous, TreeMap<String, byte[]> current) {
    HashSet<Change> changes = new HashSet<Change>();
    changes.add(new DeleteDirectory("d1"));
    changes.add(new DeleteFile("d1/f1", previous.get("d1/f1")));
    changes.add(new AddDirectory("d3"));
    changes.add(new AddFile("d3/f1", current.get("d3/f1")));
    changes.add(new AddFile("d3/f3", current.get("d3/f3")));
    changes.add(new DeleteFile("d2/f3", previous.get("d2/f3")));
    changes.add(new AddFile("d2/f3", current.get("d2/f3")));
    return new Commit("id", changes, "message");
  }

  @Test
  public void testBuildCommit() {
    TreeMap<String,byte[]> previous = getPrevious();
    TreeMap<String,byte[]> current = getCurrent(previous);
    Commit commit = getCommit(previous, current);
    Commit newCommit = (new CommitBuilder()).buildCommit(previous, current, "id", "message");
    assertTrue("CommitBuilder built a commit with the right id", commit.getId().equals(newCommit.getId()));
    assertTrue("CommitBuilder built a commit with same number of changes", commit.getChanges().size() == newCommit.getChanges().size());
    for (Change c : commit.getChanges()) {
      assertTrue("CommitBuilder built a commit with the right changes " + c.toString(), newCommit.getChanges().contains(c));
    }
    assertTrue("CommitBuilder built a commit with the right message", commit.getMessage().equals(newCommit.getMessage()));
  }
}
