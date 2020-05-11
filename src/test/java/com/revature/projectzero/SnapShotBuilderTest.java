package com.revature.projectzero;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.ArrayList;

public class SnapShotBuilderTest {
/*
  ArrayList<Path> dirPath;
  ArrayList<Path> filePath;
  ArrayList<SnapShotItem> snapShot;
  byte[] tmpcontent = { 1 };
  
  @BeforeClass
  public void setup() throws IOException {
    String rootDirString = System.getProperty("user.dir") + System.getProperty("path.separator") + "root";
    dirPath.add(Files.createDirectory(Paths.get(rootDirString)));
    dirPath.add(Files.createDirectory(Paths.get(dirPath.get(0).toString(), "tmp2")));
    dirPath.add(Files.createDirectory(Paths.get(dirPath.get(1).toString(), "tmp3")));
    filePath.add(Files.createFile(Paths.get(dirPath.get(0).toString(), "tmp.txt")));
    filePath.add(Files.createFile(Paths.get(dirPath.get(1).toString(), "tmp2.txt")));
    filePath.add(Files.createFile(Paths.get(dirPath.get(2).toString(), "tmp3.txt")));
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
      SnapShotBuilder.build(dirPath.get(0)).equals(snapShot));
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
*/
}

