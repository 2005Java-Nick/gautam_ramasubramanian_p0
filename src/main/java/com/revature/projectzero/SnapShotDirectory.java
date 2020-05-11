package com.revature.projectzero;

import java.io.Serializable;

public class SnapShotDirectory implements SnapShotItem, Serializable {

  // SnapShot directory (including files) have content
  private String path;

  // Have default constructor private
  private SnapShotDirectory() {

  } 

  public SnapShotDirectory(String path) {
    this.setPath(path);
  }

  @Override
  public String getPath() {
    return this.path;
  }

  // path setter method
  private void setPath(String path) {
    this.path = path;
  }

  @Override
  public String getType() {
    return "directory";
  }

  @Override
  public byte[] getContent() {
    return null;
  }

}
