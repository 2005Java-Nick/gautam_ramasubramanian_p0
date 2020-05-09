package com.revature.projectzero;

public class SnapShotDirectory {

  // SnapShot directory (including files) have content
  private String path;

  // Have default constructor private
  private SnapShotDirectory() {
       
  } 

  public SnapShotDirectory(String path) {
    this.setPath(path);
  }

  // path getter method
  public String getPath() {
    return this.path;
  }

  // path setter method
  private void setPath(String path) {
    this.path = path;
  }

  public String getType() {
    return "directory";
  }

}
