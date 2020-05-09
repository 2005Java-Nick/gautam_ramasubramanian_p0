package com.revature.projectzero;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;
import org.junit.Before;

import java.util.Arrays;

public class SnapShotFileTest {

  private SnapShotFile file;
  private static String PATH = "/home/username/Documents/revature/test-folder/test-file";
  private static byte[] CONTENT = { 104, 100, -128, -3, 89, 35, 82, -93, -2, 93, -49, 84, -123 };

  @Before
  public void setup() {
    file = new SnapShotFile(PATH, CONTENT); 
  }

  @Test
  public void testGetPath() {
    assertEquals("SnapShotFile getPath should return PATH constant - /home/username/..../testfile", PATH, file.getPath());
  }

  @Test 
  public void testGetContent() {
    assertTrue("SnapShotFile getContent should get CONTENT constant - bytearray", Arrays.equals(CONTENT, file.getContent()));
  }

  @Test
  public void testSetContentCreatesDeepCopy() {
    CONTENT[0] = 30;
    assertEquals("SnapShotFile content does not change if CONTENT constant changes", file.getContent()[0], 104);
    CONTENT[0] = 104;
  }

  @Test
  public void testGetContentGivesDeepCopy() {
    byte[] content = file.getContent();
    content[0] = 30;
    assertEquals("SnapShotFile content does not change if CONTENT constant changes", file.getContent()[0], 104);
  }

}
