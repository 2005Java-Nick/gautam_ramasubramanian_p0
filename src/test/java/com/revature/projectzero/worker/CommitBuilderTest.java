package com.revature.projectzero.worker;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import com.revature.projectzero.serializable.*;

import java.util.TreeSet;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Iterator;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

// TODO: Test private methods
// Add more tests later
public class CommitBuilderTest {

  HashMap<String, byte[]> previous;
  HashMap<String, byte[]> current; 
  Commit commit;
  Commit newCommit;

  private void setPrevious() {
    this.previous = new HashMap<String,byte[]>();
    this.previous.put("d1", null);
    this.previous.put("d1/f1", new byte[5]);
    this.previous.put("d2", null);
    this.previous.put("d2/f2", new byte[4]);
    this.previous.put("d2/f3", new byte[4]);
  }

  private void setCurrent() {
    this.current = new HashMap<String, byte[]>();
    this.current.put("d3", null);
    this.current.put("d3/f1", this.previous.get("d1/f1"));
    this.current.put("d3/f3", new byte[19]);
    this.current.put("d2", null);
    this.current.put("d2/f2", this.previous.get("d2/f2"));
    this.current.put("d2/f3", new byte[5]);
  }

  private void setCommit() {
    TreeSet<Change> changes = new TreeSet<Change>();
    changes.add(new DeleteDirectory("d1"));
    changes.add(new DeleteFile("d1/f1", previous.get("d1/f1")));
    changes.add(new AddDirectory("d3"));
    changes.add(new AddFile("d3/f1", current.get("d3/f1")));
    changes.add(new AddFile("d3/f3", current.get("d3/f3")));
    changes.add(new DeleteFile("d2/f3", previous.get("d2/f3")));
    changes.add(new AddFile("d2/f3", current.get("d2/f3")));
    this.commit = new Commit("id", changes, "message");
  }

  private void testCommitEquality() {
    assertEquals("CommitBuilder commit has wrong id", commit.getId(), newCommit.getId());
    assertEquals("CommitBuilder commit has wrong changeset", commit.getChanges(), newCommit.getChanges());
    assertEquals("CommitBuilder commit has wrong message", commit.getMessage(), newCommit.getMessage());
  }

  private void testChangeOrder() {
    Iterator<Change> it = newCommit.getChanges().iterator();
    assertEquals("CommitBuilder commit has wrong change in treeset index 0", it.next(), new DeleteFile("d2/f3", previous.get("d2/f3")));
    assertEquals("CommitBuilder commit has wrong change in treeset index 1", it.next(), new DeleteFile("d1/f1", previous.get("d1/f1")));
    assertEquals("CommitBuilder commit has wrong change in treeset index 2", it.next(), new DeleteDirectory("d1"));
    assertEquals("CommitBuilder commit has wrong change in treeset index 3", it.next(), new AddDirectory("d3"));
    assertEquals("CommitBuilder commit has wrong change in treeset index 4", it.next(), new AddFile("d2/f3", current.get("d2/f3")));
    assertEquals("CommitBuilder commit has wrong change in treeset index 5", it.next(), new AddFile("d3/f1", current.get("d3/f1")));
    assertEquals("CommitBuilder commit has wrong change in treeset index 6", it.next(), new AddFile("d3/f3", current.get("d3/f3")));
  }

  @Test
  public void testBuildCommit() {
    setPrevious();
    setCurrent();
    setCommit();
    this.newCommit = (new CommitBuilder()).buildCommit(this.previous, this.current, "id", "message");
    testCommitEquality();
    testChangeOrder();
  }

}
