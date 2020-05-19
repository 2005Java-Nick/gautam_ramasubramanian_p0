package com.revature.projectzero.worker;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertSame;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import java.util.HashMap;

import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import java.util.Arrays;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

// TODO: Test private methods
// Add more tests later
public class SnapshotFactoryTest {
  
  FileSystem fs;
  HashMap<String, byte[]> snapshot;
  HashMap<String, byte[]> newSnapshot;

  private void setSnapshot() {
    this.snapshot = new HashMap<String, byte[]>();
    this.snapshot.put("d1", null);
    this.snapshot.put(fs.getPath("d1","f1").toString(), new byte[5]);
    this.snapshot.put("d2", null);
    this.snapshot.put(fs.getPath("d2","f2").toString(), new byte[5]);
  }

  private void setSnapshot2() {
    this.snapshot = new HashMap<String, byte[]>();
    this.snapshot.put("f1", new byte[15]);
  }

  private void setFileSystemtoUnix() {
    this.fs = Jimfs.newFileSystem(Configuration.unix()); 
  }

  private void setFileSystemtoWindows() {
    this.fs = Jimfs.newFileSystem(Configuration.windows()); 
  }

  private void setFileSystem() throws Exception {
    Files.createDirectory(this.fs.getPath("root"));
    Files.createDirectory(this.fs.getPath("root", "d1"));
    Files.createDirectory(this.fs.getPath("root", "d2"));
    Files.createFile(this.fs.getPath("root", "d1", "f1"));
    Files.write(this.fs.getPath("root", "d1", "f1"), 
        this.snapshot.get(this.fs.getPath("d1", "f1").toString()), StandardOpenOption.WRITE);
    Files.createFile(this.fs.getPath("root", "d2", "f2"));
    Files.write(this.fs.getPath("root", "d2", "f2"), 
        this.snapshot.get(this.fs.getPath("d2", "f2").toString()), StandardOpenOption.WRITE);
  }

  private void setFileSystem2() throws Exception {
    Files.createDirectory(this.fs.getPath("root"));
    Files.createDirectory(this.fs.getPath("root", "newroot"));
    Files.createDirectory(this.fs.getPath("root", "newroot", ".snapshots"));
    Files.createFile(this.fs.getPath("root", "newroot", ".snapshots", ".snapshot"));
    Files.write(this.fs.getPath("root", "newroot", ".snapshots", ".snapshot"), new byte[3], StandardOpenOption.WRITE);
    Files.createFile(this.fs.getPath("root", "newroot", "f1"));
    Files.write(this.fs.getPath("root", "newroot", "f1"), this.snapshot.get("f1"), StandardOpenOption.WRITE); 
  }

  private void testSnapshots() {
    assertEquals("Make snapshot of a root directory configuration. Size is as expected", snapshot.size(), newSnapshot.size());
    assertEquals("Make snapshot of a root directory configuration. Keys are as expected", snapshot.keySet(), newSnapshot.keySet());
    for (String key : snapshot.keySet()) {
      assertArrayEquals("Make snapshot of a root directory configuration. Values are as expected", snapshot.get(key), newSnapshot.get(key));
    } 
  }

  @Test
  public void testBuildSnapshotUnix1() throws Exception {
    this.setFileSystemtoUnix();
    this.setSnapshot();
    this.setFileSystem();
    this.newSnapshot = (new SnapshotFactory(fs, "root", ".snapshots")).buildSnapshot();
    this.testSnapshots();
  }
 
  @Test
  public void testBuildSnapshotUnix2() throws Exception {
    this.setFileSystemtoUnix();
    this.setSnapshot2();
    this.setFileSystem2();
    this.newSnapshot = (new SnapshotFactory(fs, fs.getPath("root", "newroot").toString(), ".snapshots")).buildSnapshot();
    this.testSnapshots();
  }
 
  @Test
  public void testBuildSnapshotWindows() throws Exception {
    this.setFileSystemtoWindows();
    this.setSnapshot();
    this.setFileSystem();
    this.newSnapshot = (new SnapshotFactory(fs, "root", ".snapshots")).buildSnapshot();
    this.testSnapshots();
  }
 
  @Test
  public void testBuildSnapshotWindows2() throws Exception {
    this.setFileSystemtoWindows();
    this.setSnapshot2();
    this.setFileSystem2();
    this.newSnapshot = (new SnapshotFactory(fs, fs.getPath("root", "newroot").toString(), ".snapshots")).buildSnapshot();
    this.testSnapshots();
  }

}
