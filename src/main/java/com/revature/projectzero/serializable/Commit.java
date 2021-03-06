package com.revature.projectzero.serializable;

import java.util.TreeSet;
import java.io.Serializable;

/* Represents a Commit in version control */
public class Commit implements Serializable {

  private String id;
  private TreeSet<Change> changes;
  private String message;

  private Commit() {

  }

  public Commit(String id, TreeSet<Change> changes, String message) {
    this.setId(id);
    this.setChanges(changes);
    this.setMessage(message);
  } 

  private void setId(String id) {
    this.id = id;
  }

  private void setChanges(TreeSet<Change> changes) {
    this.changes = changes;
  }

  private void setMessage(String message) {
    this.message = message;
  }

  public String getId() {
    return this.id;
  }

  public TreeSet<Change> getChanges() {
    return this.changes;
  }

  public String getMessage() {
    return this.message;
  }

}
