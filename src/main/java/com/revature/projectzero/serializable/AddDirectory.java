package com.revature.projectzero.serializable;

import java.io.Serializable;

/* Represents the adding of a directory */
public class AddDirectory extends Change implements Serializable {
  
  private String pathString;

  private AddDirectory() {

  }

  public AddDirectory(String pathString) {
    this.setPathString(pathString);
  }

  private void setPathString(String pathString) {
    this.pathString = pathString;
  }

  public String getPathString() {
    return this.pathString;
  }

  public byte[] getContent() {
    return null;
  }
  
  public int classOrder() {
    return 3;
  }

}
