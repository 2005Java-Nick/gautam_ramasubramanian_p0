package com.revature.projectzero.worker;

import com.revature.projectzero.serializable.CommitHistory;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Serializer {

  public void createDirectory(String imageDir) {
    try {
      Files.createDirectory(Paths.get(imageDir));
    } catch (Exception e) {
      // TODO
    }
  }

  public void writeToFile(String imagePath, CommitHistory history) {
    try {
      FileOutputStream fout = new FileOutputStream(imagePath);
      ObjectOutputStream oos = new ObjectOutputStream(fout);
      oos.writeObject(history);
    } catch (Exception e) {
      // TODO
    }
  }

  public CommitHistory readFromFile(String imagePath) {
    CommitHistory c;
    try {
      FileInputStream fin = new FileInputStream(imagePath);
      ObjectInputStream ois = new ObjectInputStream(fin);
      c = (CommitHistory) ois.readObject();
    } catch (Exception e) {
      // TODO
      return null;
    }
    return c;
  }

}
