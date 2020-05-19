package com.revature.projectzero.serializable;

/* Represents a change of file/directory -> Adding or Deleting 
 *  Changing the contents of a file is equivalent to deleting the old file and adding the new one 
 */
import java.util.Arrays;

public abstract class Change implements Comparable {
  
  /* FilePath of file that you are changing */
  public abstract String getPathString();

  /* Content of the file you are changing */
  public abstract byte[] getContent();

  /* Needed to compare and sort changes of different classes */
  public abstract int classOrder();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Change)) {
      return false;
    }
    Change c = (Change) o;
    boolean samePathString = c.getPathString().equals(this.getPathString());
    boolean sameContent;
    if (c.getContent() == null && this.getContent() == null) {
      sameContent = true;
    } else if (c.getContent() != null && this.getContent() != null) {
      sameContent = Arrays.equals(c.getContent(), this.getContent());
    } else {
      sameContent = false;
    }
    return samePathString && sameContent;
  }

  @Override
  public int compareTo(Object o) {
    if (this == o) {
      return 0;
    }
    if (!(o instanceof Change)) {
      return 1;
    }
    Change c = (Change) o;
    
    if (this.classOrder() != c.classOrder()) {
      return this.classOrder() - c.classOrder();
    } else {
      if (this.classOrder() <= 2) {
        return c.getPathString().compareTo(this.getPathString());
      } else {
        return this.getPathString().compareTo(c.getPathString());
      }
    }
  }
  
}
