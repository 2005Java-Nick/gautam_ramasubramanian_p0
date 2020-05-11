package com.revature.projectzero;

import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App {
    
    private static Logger logger = Logger.getLogger(App.class);
    
    private static String getHelpMessage() {
      return "Help Message";
    }

    public static void main( String[] args ) throws Exception {
      
      if (args.length != 1) {
        System.out.println(getHelpMessage());
        return;
      }
 
      Path rootDirPath = Paths.get(System.getProperty("user.dir"));
      Path saveImagePath = Paths.get(rootDirPath.toString(), ".projectzero.snapshots", ".projectzero.snapshot");

      if (args[0].equals("save")) {
        ArrayList<SnapShotItem> snapShot = SnapShotBuilder.build(rootDirPath);
        FileOutputStream fout = new FileOutputStream(saveImagePath.toString());
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        oos.writeObject(snapShot);
      }
      else if (args[0].equals("restore")) {
        FileInputStream fin = new FileInputStream(saveImagePath.toString());
        ObjectInputStream ois = new ObjectInputStream(fin);
        ArrayList<SnapShotItem> snapShot = (ArrayList<SnapShotItem>) ois.readObject();
        SnapShotRestorer.restore(rootDirPath, snapShot);
      } else {
        System.out.println(getHelpMessage()); 
      }
    
    }

}
