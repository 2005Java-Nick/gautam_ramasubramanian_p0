package com.revature.projectzero.worker;

import static org.junit.Assert.assertTrue;

import com.revature.projectzero.serializable.*;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

// TODO: Test setters/getters and private methods
// We only test apply here

public class CommitApplierTest {
/*
  CommitApplier caInstance;
  FileSystem fs;
  Commit[] commits;
  byte[] content = { 5,6,7,8,9 };

  private void getInstances() {
    fs = Jimfs.newFileSystem(Configuration.unix());
    caInstance = CommitApplier.getInstance(fs);
    snapshot = new TreeMap<String, byte[]>();
  }

  private void setFiles() {
    Files.createDirectory(fs.getPath("/d1"));
    Files.createFile(fs.getPath("/d1/f1"));
    Files.write(fs.getPath("/d1/f1"), content, StandardOpenOption.WRITE);
    Files.createDirectory(fs.getPath("/d2"));
    Files.createFile(fs.getPath("/d2/f2"));
    Files.write(fs.getPath("/d2/f2"), content, StandardOpenOption.WRITE);
  }
  
  private void setFiles() {
    commits = new Commit[5];
    ArrayList<Change> changes = new ArrayList<Change>;    
    
  }

  @Before
  public void before() {
    setInstances();
    setFiles();
  }
  
  @Test
  public void SingletonTest() {
    CommitApplier caInstance2 = CommitApplier.getInstance();
    assertTrue("getInstance returns the same instance of CommitApplier object", caInstance == caInstance2);
  }
*/
} 
