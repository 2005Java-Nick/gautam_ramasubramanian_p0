package com.revature.projectzero.serializable;

import java.io.Serializable;

/* Represents the adding of a file */
public class AddFile implements Change, Serializable {
  
  private String pathString;
  private byte[] content;

  private AddFile() {

  }

  public AddFile(String pathString, byte[] content) {
    this.setPathString(pathString);
    this.setContent(content);
  } 

  private void setPathString(String pathString) {
    this.pathString = pathString;
  }

  private void setContent(byte[] content) {
    this.content = content;
  }

  public String getPathString() {
    return this.pathString;
  }

  public byte[] getContent() {
    return this.content;
  }

}
