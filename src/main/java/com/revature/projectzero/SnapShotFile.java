package com.revature.projectzero;

public class SnapShotFile {

  // Object consists of filepath and content
  private String path;
  private byte[] content;

  // Have default constructor private
  private SnapShotFile() {
    
  } 

  // Make sure the only constructor that the program is using
  // is this one, with two arguments.
  public SnapShotFile(String path, byte[] content) {
    this.setPath(path);
    this.setContent(content);
  }

  // path getter method
  public String getPath() {
    return this.path;
  }

  // path setter method
  private void setPath(String path) {
    this.path = path;
  }

  // content getter method
  public byte[] getContent() {
    return this.content;
  }
  
  // content setter method
  private void setContent(byte[] content) {
    this.content = content;
  }

}
