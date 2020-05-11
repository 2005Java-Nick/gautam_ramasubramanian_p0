package com.revature.projectzero;

import java.util.ArrayList;

import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.FileVisitResult;
import java.nio.file.attribute.BasicFileAttributes;

public class SnapShotBuilder {
  
  private static Path cwdPath;
  private static ArrayList<SnapShotItem> snapShot;
  
  private static SimpleFileVisitor<Path> snapShotFileVisitor = new SimpleFileVisitor<Path>() {
    @Override
    public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs) 
      throws IOException, OutOfMemoryError, SecurityException {
      byte[] fileContent = Files.readAllBytes(filePath);
      snapShot.add(new SnapShotFile(filePath.toString(), fileContent));
      return FileVisitResult.CONTINUE; 
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dirPath, BasicFileAttributes attrs) {
      if (dirPath.endsWith(".projectzero.snapshots")) {
        return FileVisitResult.SKIP_SUBTREE;
      } else if (dirPath.endsWith(cwdPath.toString())) {
        return FileVisitResult.CONTINUE;
      }
      else {
        snapShot.add(new SnapShotDirectory(dirPath.toString()));
        return FileVisitResult.CONTINUE;
      }
    }

  };

  // Shallow copy - beware
  public static ArrayList<SnapShotItem> build(Path rootPath) throws IOException {
    cwdPath = rootPath;
    snapShot = new ArrayList<SnapShotItem>();
    Files.walkFileTree(rootPath, snapShotFileVisitor);
    return snapShot;
  }

}
