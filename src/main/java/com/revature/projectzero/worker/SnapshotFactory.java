package com.revature.projectzero.worker;

import java.util.HashMap;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.FileVisitResult;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.FileSystem;
import java.io.IOException;

// TODO: Add Getters and Setters for private instance variables
public class SnapshotFactory {

  private static SnapshotFactory snapshotFactory;
  private FileSystem fs;
  private Path rootDir;
  private Path imageDir;
  private HashMap<String, byte[]> snapshot;

  private SnapshotFactory () { 
  
  }

  public SnapshotFactory(FileSystem fs, String rootDirString, String imageDirString) {
      this.fs = fs;
      this.rootDir = fs.getPath(rootDirString);
      this.imageDir = fs.getPath(rootDirString, imageDirString); 
  }

  private SimpleFileVisitor<Path> snapshotfv = new SimpleFileVisitor<Path>() {
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attr) throws IOException {
      snapshot.put(rootDir.relativize(file).toString(), Files.readAllBytes(file));
      return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attr) throws IOException {
      if (Files.isSameFile(dir, imageDir)) {
        return FileVisitResult.SKIP_SUBTREE;
      } else if (Files.isSameFile(dir, rootDir)) {
        return FileVisitResult.CONTINUE;
      } else {
        snapshot.put(rootDir.relativize(dir).toString(), null);
        return FileVisitResult.CONTINUE;
      }
    }
  };

  public HashMap<String, byte[]> buildSnapshot() {
    // TODO: Fill this out
    this.snapshot = new HashMap<String, byte[]>();
    try {
      Files.walkFileTree(this.rootDir, this.snapshotfv); 
    } catch (IOException e) {
      // TODO
      return null;
    }
    return this.snapshot;
  }

}
