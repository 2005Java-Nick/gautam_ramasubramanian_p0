package com.revature.projectzero;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;
import org.junit.Before;

public class SnapShotFileTest {

  private SnapShotFile file;
  private static final String PATH = "/home/username/Documents/revature/test-folder/test-file";
  private static final byte[] CONTENT = { 104, 100, -128, -3, 89, 35, 82, -93, -2, 93, -49, 84, -123 };

  @Before
  public void setup() {
    file = new SnapShotFile(PATH, CONTENT); 
  }

  @Test
  public void testGetPath() {
    assertEquals("SnapShot getPath should return PATH constant - /home/username/..../testfile", PATH, file.getPath());
  }

  @Test 
  public void testGetContent() {
    assertEquals("SnapShot getContent should get CONTENT constant - bytearray", CONTENT, file.getContent());
  }

}
