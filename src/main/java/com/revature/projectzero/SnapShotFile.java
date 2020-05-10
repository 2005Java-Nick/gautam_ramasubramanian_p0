package com.revature.projectzero;

import java.io.Serializable;

public class SnapShotFile implements SnapShotItem, Serializable {

  // SnapShot file has path and content
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

  private byte[] deepCopy(byte[] input) {
    byte[] output = new byte[input.length];
    for (int i = 0; i < input.length; i++) {
      output[i] = input[i];
    }
    return output;
  }

  @Override
  public String getPath() {
    return this.path;
  }

  // path setter method
  private void setPath(String path) {
    this.path = path;
  }

  // content getter method
  public byte[] getContent() {
    return this.deepCopy(this.content);
  }
  
  // content setter method
  private void setContent(byte[] content) {
    this.content = this.deepCopy(content);
  }

  @Override
  public String getType() {
    return "file";
  }

}
