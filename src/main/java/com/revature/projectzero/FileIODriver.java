package com.revature.projectzero;

/*
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.nio.file.FileVisitor;
import java.nio.file.LinkOption;

public class FileIODriver {

  private static String currentWorkingDirectory;
  private static ArrayList<String> files;
  private static ArrayList<String> directories;

  private static class MyFileVisitor<String> implements FileVisitor<String> {
    
    @Override
    public FileVisitResult preVisitDirectory(String dirPath, BasicFileAttributes attrs) {
      directories.add(dirPath);
    }

    @Override
    public FileVisitResult visitFile(String filePath, BasicFileAttributes attrs) {
      files.add(filePath);
    }

  }
  
  public static void setCurrentWorkingDirectory(String cwd) {
    // Code courtesy of Stack Overflow
    // We get a Filesystem object by FileSystems.getDefault
    // We get a path object corresponding to cwd
    // We normalize the path objects, removing ../ from the path
    // We get the absolute path, removing ./ from the path
    // Then we get the string version and set that as current working directory
    currentWorkingDirectory = FileSystems.getDefault().getPath(cwd).normalize().toAbsolutePath().toString();  
 
    if (!Files.exists(currentWorkingDirectory, LinkOption.NOFOLLOW_LINKS)) {
      throw IllegalArgumentException("CWD filepath does not exist");
    } 

    if (Files.isDirectory(currentWorkingDirectory, LinkOption.NOFOLLOW_LINKS)) {
      throw IllegalArgumentException("CWD filepath is not a directory"); 
    }

    Files.walkFileTree(currentWorkingDirectory, new MyFileVisitor<String>());
  
  }

  public static void getFiles() {
    return files.toArray();
  }

  public static void getDirectories() {
    return directories.toArray();
  }

  public static void createFile(String filePath) throws IOException, IllegalArgumentException {
    if (!filePath.startsWith(currentWorkingDirectory)) {
      throw IllegalArgumentException("The file you are creating must be in the CWD. The absolute path must be given.");
    }
    if (Files.exists(filePath, LinkOption.NOFOLLOW_LINKS)) {
      throw IllegalArgumentException("The file you are creating already exists.");
    }
    Files.createFile(filePath);
    files.add(filePath);
  }

  public static void createDirectory(String dirPath) throws IOException, IllegalArgumentException {
    if (!dirPath.startsWith(currentWorkingDirectory)) {
      throw IllegalArgumentException("The file you are creating must be in the CWD. The absolute path must be given.");
    }
    if (Files.exists(dirPath, LinkOption.NOFOLLOW_LINKS)) {
      throw IllegalArgumentException("The file you are creating already exists.");
    }
    Files.createDirectory(dirPath);
    directories.add(dirPath);
  }

  public static byte[] getFileContent(String filePath) throws IOException, IllegalArgumentException {
    if (!Files.exists(filePath)) {
      throw IllegalArgumentException("The file you want to access does not exist.");
    }
    if (!Files.isRegularFile(filePath)) {
      throw IllegalArgumentException("The file you want to access is a directory.");
    }
    return Files.readAllBytes(filePath);
  } 

  public static void setFileContent(String filePath, byte[] content) 
      throws IOException, IllegalArgumentException, SecurityException, UnsupportedOperationException {
    if (!Files.exists(filePath)) {
      throw IllegalArgumentException("The file you want to access does not exist.");
    }
    if (!Files.isRegularFile(filePath)) {
      throw IllegalArgumentException("The file you want to access is a directory.");
    }
    Files.write(filePath, content, StandardOpenOption.WRITE);
  }

  public static void deleteFile(String filePath) {
    if (!Files.exists(filePath)) {
      throw IllegalArgumentException("The file you want to delete does not exist.");
    }
    if (!Files.isRegularFile(filePath)) {
      throw IllegalArgumentException("The file you want to delete is a directory.");
    }
    Files.delete(filePath);
  }

  public static void deleteDirectory(String dirPath) {
    if (!Files.exists(dirPath)) {
      throw IllegalArgumentException("The directory you want to delete does not exist.");
    }
    if (!Files.isDirectory(dirPath)) {
      throw IllegalArgumentException("The directory you want to delete is not a directory.");
    }
    Files.delete(dirPath);

  }

}
  

 Code Recycle Bin

  private static void recursivelyFindSubfiles(String prefix, String[] directChildren) {
    for (String fileName: directChildren) {
      String pathToFile = prefix + File.separator + fileName;
      if (Files.isRegularFile(pathToFile)) {
        files.add(pathToFile);
      } 
      if (Files.isDirectory()) {
        directories.add(pathToFile);
        recursivelyFindSubfiles(pathToFile, file.list());
      }
    }
  }
  */
