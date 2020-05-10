package com.revature.projectzero;
/*
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.BeforeClass;

import java.io.File;
import java.util.Arrays;

public class FileIODriverTest {

  // These tests relate to each other. They are not independent.

  private static String currentWorkingDirectory;
  private static String[] fileList;
  private static String[] directoryList;
  private static String newFile;
  private static String newDirectory;
  private static byte[] newFileContent;

  @BeforeClass
  public void setup() {
    currentWorkingDirectory = System.getProperty("user.dir") + File.separator + "test-dir";
    fileList = {"test-1/1.txt", "test-2/2.txt", "multi-level/test-1/1.txt", "multi-level/test-2/2.txt"};
    directoryList = {"test-1", "test-2", "multi-level/test-1", "multi-level/test-2"};
    newFile = "new-file.txt";
    newFileContent = { 104, 100, -128, -3, 89, 35, 82, -93, -2, 93, -49, 84, -123 };
    newDirectory = "new-dir"
  }

  @Test
  public void testGetCurrentWorkingDirectory() {
    FileIODriver.setCurrentWorkingDirectory("./test-dir/");
    assertTrue("Test that getCurrentWorkingDirectory returns the file path to CWD", 
        FileIODriver.getCurrentWorkingDirectory().equals(currentWorkingDirectory)); 
  }

  @Test
  public void testGetFiles() {
    assertTrue("Test that getFiles returns an array of paths of files (not directories)", 
        Arrays.equals(FileIODriver.getFiles(), fileList));
  }

  @Test
  public void testGetDirectories() {
    assertTrue("Test that getDirectories returns an array of paths of directories (not files)", 
        Arrays.equals(FileIODriver.getDirectories(), directoryList))
  }

  @Test
  public void testCreateFile() {
    FileIODriver.createFile(newFile);
    File file = new File(currentWorkingDirectory + File.separator + newFile);
    assertTrue("Test createFile('new-file.txt') if it created the file (cwd)/new-file.txt", file.exists());
    assertTrue("Test createFile('new-file.txt') if it created the a file", file.isFile());
  }

  @Test
  public void testSetFileContent() {
    FileIODriver.setFileContent(newFile, newFileContent);
    File file = new File(currentWorkingDirectory + File.separator + newFile);
    assertTrue("Test setFileContents if it filled the contents of file", file.length() == newFileContent.length);
  }

  @Test
  public void testGetFileContent() {
    byte[] fileContents = FileIODriver.getFileContent(newFile);
    File file = new File(currentWorkingDirectory + File.separator + newFile);
    assertTrue("Test getFileContents if it gets the contents of file", Arrays.equal(fileContents, newFileContents));
  }

  @Test
  public void testDeleteFile() {
    FileIODriver.deleteFile(newFile);
    File file = new File(currentWorkingDirectory + File.separator + newFile;
    assertFalse("Test deleteFile('new-file.txt') if it deleted the file (cwd)/new-file.txt", file.exists());
  }

  @Test
  public void testCreateDirectory() {
    FileIODriver.createDirectory(newDirectory);
    File file = new File(currentWorkingDirectory + File.separator + newFile);
    assertTrue("Test createDirectory if the file it created exists", file.exists());
    assertTrue("Test createDirectory if the file it created is a directory", file.isDirectory());  
  }

  @Test
  public void testDeleteDirectory() {
    FileIODriver.deleteDirectory(newDirectory);
    File file = new File(currentWorkingDirectory + File.separator + newFile);
    assertFalse("Test deleteDirectory('new-dir') if it deleted the file (cwd)/new-dir", file.exists());
  }
}
*/
