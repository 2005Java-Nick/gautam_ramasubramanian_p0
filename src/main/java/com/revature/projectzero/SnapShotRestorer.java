package com.revature.projectzero;

import java.util.ArrayList;
import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.FileVisitResult;
import java.nio.file.attribute.BasicFileAttributes;

public class SnapShotRestorer {

    private static Path cwdPath;
    private static SimpleFileVisitor<Path> deletionFileVisitor = new SimpleFileVisitor<Path>() {
    
    @Override
    public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs) throws IOException {
      Files.delete(filePath);
      return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dirPath, BasicFileAttributes attrs) {
      if (dirPath.endsWith(".projectzero.snapshots")) {
        return FileVisitResult.SKIP_SUBTREE;
      }
      else {
        return FileVisitResult.CONTINUE;
      }
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dirPath, IOException e) throws IOException {
      if (!dirPath.endsWith(cwdPath.toString()) && !dirPath.endsWith(".projectzero.snapshots")) {
        Files.delete(dirPath);
      }
      return FileVisitResult.CONTINUE;
    }

  };

  public static void restore(Path rootPath, ArrayList<SnapShotItem> snapShot) throws IOException {
    cwdPath = rootPath;
    Files.walkFileTree(rootPath, deletionFileVisitor);
    for (SnapShotItem item: snapShot) {
      if (item.getType().equals("file")) {
        Files.createFile(Paths.get(item.getPath()));
        Files.write(Paths.get(item.getPath()), item.getContent(), StandardOpenOption.WRITE);
      }
      if (item.getType().equals("directory")) {
        Files.createDirectory(Paths.get(item.getPath()));
      }
    }   
  }  
}
