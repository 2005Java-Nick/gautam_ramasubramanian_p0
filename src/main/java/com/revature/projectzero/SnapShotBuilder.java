package com.revature.projectzero;

import java.util.ArrayList;

import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.FileVisitResult;
import java.nio.file.attribute.BasicFileAttributes;

public class SnapShotBuilder {
/*
  private byte[] getContent(String filePath) {
    try (File f = new File(filePath), FileInputStream fin = new FileInputStream(filePath)) {
      byte[] content = new byte[f.length()];
      return fin.read(content);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  
  
  private Path cwdPath;
  private ArrayList<SnapShotItem> snapShot;

  private SimpleFileVisitor<Path> snapShotFileVisitor = new SimpleFileVisitor<Path>() {
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

  private boolean addSnapShotItem(SnapShotItem snapShotItem) {
    snapShot.add(snapShotItem);
  } 
  

  // Shallow copy - beware
  public ArrayList<SnapShotItem> build(Path rootPath) throws IOException {
    cwdPath = rootPath;
    snapShot = new ArrayList<SnapShotItem>();
    Files.walkFileTree(rootPath, snapShotFileVisitor);
    return snapShot;
  }
*/
}
