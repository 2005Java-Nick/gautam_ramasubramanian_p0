package com.revature.projectzero.serializable;

import java.io.Serializable;

/* Represents the deletion of a directory */
public class DeleteDirectory implements Change, Serializable {
   
  private String pathString;

  private DeleteDirectory() {

  }

  public DeleteDirectory(String pathString) {
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


}
