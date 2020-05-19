package com.revature.projectzero.worker;

import static org.junit.Assert.assertTrue;

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

  private HashMap<String, byte[]> setUnixSnapshot() {
    HashMap<String, byte[]> snapshot = new HashMap<String, byte[]>();
    snapshot.put("d1", null);
    snapshot.put("d1/f1", new byte[5]);
    snapshot.put("d2", null);
    snapshot.put("d2/f2", new byte[5]);
    return snapshot;
  }
  
  private HashMap<String, byte[]> setWindowsSnapshot() {
    HashMap<String, byte[]> snapshot = new HashMap<String, byte[]>();
    snapshot.put("d1", null);
    snapshot.put("d1\\f1", new byte[5]);
    snapshot.put("d2", null);
    snapshot.put("d2\\f2", new byte[5]);
    return snapshot;
  }

  private FileSystem setFileSystemtoUnix() {
    return Jimfs.newFileSystem(Configuration.unix()); 
  }

  private FileSystem setFileSystemtoWindows() {
    return Jimfs.newFileSystem(Configuration.windows()); 
  }

  private void setFileSystem(FileSystem fs, HashMap<String, byte[]> snapshot) throws Exception {
    Files.createDirectory(fs.getPath("root"));
    Files.createDirectory(fs.getPath("root", "d1"));
    Files.createDirectory(fs.getPath("root", "d2"));
    Files.createFile(fs.getPath("root", "d1", "f1"));
    Files.write(fs.getPath("root", "d1", "f1"), 
        snapshot.get(fs.getPath("d1", "f1").toString()), StandardOpenOption.WRITE);
    Files.createFile(fs.getPath("root", "d2", "f2"));
    Files.write(fs.getPath("root", "d2", "f2"), 
        snapshot.get(fs.getPath("d2", "f2").toString()), StandardOpenOption.WRITE);
  }

  @Test
  public void testBuildSnapshotUnix() throws Exception {
    HashMap<String, byte[]> snapshot = setUnixSnapshot();
    FileSystem fs = setFileSystemtoUnix();
    setFileSystem(fs, snapshot);
    HashMap<String, byte[]> newSnapshot = (new SnapshotFactory(fs, "root", ".snapshots")).buildSnapshot();
    assertTrue("Make snapshot of a root directory configuration. Size is as expected", snapshot.size() == newSnapshot.size());
    assertTrue("Make snapshot of a root directory configuration. Keys are as expected", snapshot.keySet().equals(newSnapshot.keySet()));
    for (String key : snapshot.keySet()) {
      assertTrue("Make snapshot of a root directory configuration. Values are as expected", Arrays.equals(snapshot.get(key), newSnapshot.get(key)));
    }
  }
  
  @Test
  public void testBuildSnapshotWindows() throws Exception {
    HashMap<String, byte[]> snapshot = setWindowsSnapshot();
    FileSystem fs = setFileSystemtoWindows();
    setFileSystem(fs, snapshot);
    HashMap<String, byte[]> newSnapshot = (new SnapshotFactory(fs, "root", ".snapshots")).buildSnapshot();
    assertTrue("Make snapshot of a root directory configuration. Size is as expected", snapshot.size() == newSnapshot.size());
    assertTrue("Make snapshot of a root directory configuration. Keys are as expected", snapshot.keySet().equals(newSnapshot.keySet()));
    for (String key : snapshot.keySet()) {
      assertTrue("Make snapshot of a root directory configuration. Values are as expected", Arrays.equals(snapshot.get(key), newSnapshot.get(key)));
    }
  }

}
