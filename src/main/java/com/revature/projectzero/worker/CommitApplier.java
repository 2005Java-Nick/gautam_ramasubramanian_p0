package com.revature.projectzero.worker;

import com.revature.projectzero.serializable.Change;
import com.revature.projectzero.serializable.AddDirectory;
import com.revature.projectzero.serializable.AddFile;
import com.revature.projectzero.serializable.DeleteDirectory;
import com.revature.projectzero.serializable.DeleteFile;
import com.revature.projectzero.serializable.Commit;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.FileSystem;
import java.nio.file.StandardOpenOption;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NoSuchFileException;
import java.nio.file.DirectoryNotEmptyException;
import java.io.IOException;

public class CommitApplier {

  private FileSystem fs;
 

  private CommitApplier() {

  }

  public CommitApplier(FileSystem fs) {
    this.setFs(fs);
  }

  private void setFs(FileSystem fs) {
    this.fs = fs;
  }

  public FileSystem getFs(FileSystem fs) {
    return this.fs;
  }

  public boolean apply(String rootDir, Commit commit) {
    boolean result = true;
    for (Change change : commit.getChanges()) {
      if (change.getClass() == DeleteFile.class) {
        boolean changeBool = exceptionHandledDelete(rootDir, change.getPathString());
        result &= changeBool;   
      }
    }
    for (Change change : commit.getChanges()) {
      if (change.getClass() == DeleteDirectory.class) {
        boolean changeBool = exceptionHandledDelete(rootDir, change.getPathString());
        result &= changeBool;   
      }
    }
    for (Change change : commit.getChanges()) {
      if (change.getClass() == AddDirectory.class) {
        boolean changeBool = exceptionHandledAddDirectory(rootDir, change.getPathString());
        result &= changeBool;
      }
    }
    for (Change change : commit.getChanges()) {
      if (change.getClass() == AddFile.class) {
        boolean changeBool = exceptionHandledAddFile(rootDir, change.getPathString(), change.getContent());
        result &= changeBool;
      }
    }
    return result;
  }

  public boolean reverse(String rootDir, Commit commit) {
    boolean result = true;
    for (Change change : commit.getChanges()) {
      if (change.getClass() == AddFile.class) {
        boolean changeBool = exceptionHandledDelete(rootDir, change.getPathString());
        result &= changeBool;
      }
    }
    for (Change change : commit.getChanges()) {
      if (change.getClass() == AddDirectory.class) {
        boolean changeBool = exceptionHandledDelete(rootDir, change.getPathString());
        result &= changeBool;
      }
    }
    for (Change change : commit.getChanges()) {
      if (change.getClass() == DeleteDirectory.class) {
        boolean changeBool = exceptionHandledAddDirectory(rootDir, change.getPathString());
        result &= changeBool;   
      }
    }
    for (Change change : commit.getChanges()) {
      if (change.getClass() == DeleteFile.class) {
        boolean changeBool = exceptionHandledAddFile(rootDir, change.getPathString(), change.getContent());
        result &= changeBool;   
      }
    }
    return result;
  }

  private boolean exceptionHandledAddDirectory(String rootDir, String pathString) {
    try {
      Files.createDirectory(fs.getPath(rootDir, pathString));
      return true;
    } catch (UnsupportedOperationException e) {
      // TODO
      return false;
    } catch (FileAlreadyExistsException e) {
      // TODO
      return false;
    } catch (IOException e) {
      // TODO
      return false;
    } catch (SecurityException e) {
      // TODO
      return false;
    }
  }

  private boolean exceptionHandledAddFile(String rootDir, String pathString, byte[] content) {
    try {
      Path path = fs.getPath(rootDir, pathString);
      Files.createFile(path);
      Files.write(path, content, StandardOpenOption.WRITE); 
      return true;
    } catch (UnsupportedOperationException e) {
      // TODO
      return false;
    } catch (FileAlreadyExistsException e) {
      // TODO
      return false;
    } catch (IOException e) {
      // TODO
      return false;
    } catch (SecurityException e) {
      // TODO
      return false;
    }
  }

  private boolean exceptionHandledDelete(String rootDir, String pathString) {
    try {
      Files.delete(fs.getPath(rootDir, pathString));
      return true;
    } catch (NoSuchFileException e) {
      // TODO
      return false;
    } catch (DirectoryNotEmptyException e) {
      // TODO
      return false;
    } catch (IOException o) {
      // TODO
      return false;
    } catch (SecurityException o) {
      // TODO
      return false;
    }
  } 

}
