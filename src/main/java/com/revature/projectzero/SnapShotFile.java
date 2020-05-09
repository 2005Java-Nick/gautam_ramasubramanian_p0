package com.revature.projectzero;

public class SnapShotFile extends SnapShotDirectory {

  // SnapShot file has content
  private byte[] content;

  // Have default constructor private
  private SnapShotFile() {
    // To make sure it does not break the fact that
      // SnapShotDirectory default constructor is private
    super("");
  }

  // Make sure the only constructor that the program is using
  // is this one, with two arguments.
  public SnapShotFile(String path, byte[] content) {
    super(path);
    this.setContent(content);
  }

  private byte[] deepCopy(byte[] input) {
    byte[] output = new byte[input.length];
    for (int i = 0; i < input.length; i++) {
      output[i] = input[i];
    }
    return output;
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
