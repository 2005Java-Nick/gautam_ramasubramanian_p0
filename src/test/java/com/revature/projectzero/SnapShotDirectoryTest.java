package com.revature.projectzero;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.Before;

public class SnapShotDirectoryTest {

  private SnapShotDirectory dir;
  private static String PATH = "home/username/Documents/revature/test-folder";

  @Before
  public void setup() {
    dir = new SnapShotDirectory(PATH);
  }

  @Test
  public void testGetPath() {
    assertTrue("SnapShotDirectory getPath should return PATH constant - home/username/.../test-folder", dir.getPath().equals(PATH));
  }
 
  @Test
  public void testGetType() {
    assertTrue("SnapShotDirectory getType should return 'directory'", dir.getType().equals("directory"));
  } 

}
