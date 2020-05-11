package com.revature.projectzero;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.Arrays;
import java.util.ArrayList;

public class SnapShotBuilderTest {

  ArrayList<Path> dirPath;
  ArrayList<Path> filePath;
  ArrayList<SnapShotItem> snapShot;
  byte[] tmpcontent = { 1 };
  
  @BeforeClass
  public void setup() {
    dirPath.add(Files.createTempDirectory(Paths.get(System.getProperty("user.dir")), "root"));
    dirPath.add(Files.createTempDirectory(dirPath.get(0), "tmp2"));
    dirPath.add(Files.createTempDirectory(dirPath.get(1), "tmp3"));
    filePath.add(Files.createTempFile(dirPath.get(0), "tmp.txt", ""));
    filePath.add(Files.createTempFile(dirPath.get(1), "tmp2.txt", ""));
    filePath.add(Files.createTempFile(dirPath.get(2), "tmp3.txt", ""));
    Files.write(filePath.get(0), tmpcontent, StandardOpenOption.WRITE);
    Files.write(filePath.get(1), tmpcontent, StandardOpenOption.WRITE);
    Files.write(filePath.get(2), tmpcontent, StandardOpenOption.WRITE);
    snapShot.add(new SnapShotDirectory(dirPath.get(1).toString())); 
    snapShot.add(new SnapShotDirectory(dirPath.get(2).toString())); 
    snapShot.add(new SnapShotFile(filePath.get(0).toString(), tmpcontent)); 
    snapShot.add(new SnapShotFile(filePath.get(1).toString(), tmpcontent)); 
    snapShot.add(new SnapShotFile(filePath.get(2).toString(), tmpcontent)); 
  }

  @Test
  public void testBuildSnapShot() {
    assertTrue("Check that SnapShotBuilder builds a snapshot arraylist of root/ tmp-directory",  
      Arrays.equal(SnapShotBuilder.build(dirPath.get(0)), snapShot));
  }

  @AfterClass
  public void tearDown() {
    Files.delete(filePath.get(2));
    Files.delete(filePath.get(1));
    Files.delete(filePath.get(0));
    Files.delete(dirPath.get(2));
    Files.delete(dirPath.get(1));
    Files.delete(dirPath.get(0));
  }

}

