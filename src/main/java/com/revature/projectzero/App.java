package com.revature.projectzero;

import org.apache.log4j.Logger;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import com.revature.projectzero.serializable.*;

import com.revature.projectzero.worker.Serializer;
import com.revature.projectzero.worker.Diagram;
import com.revature.projectzero.worker.Commiter;
import com.revature.projectzero.worker.Restorer;

public class App {
    
    private static Logger logger = Logger.getLogger(App.class);
    
    private static String getHelpMessage() {
      return "Help Message";
    }

    public static void main( String[] args ) throws Exception {
      
      if (args.length != 1) {
        logger.warn("Program is used without arguments or with too many arguments"); 
        System.out.println(getHelpMessage());
        return;
      }
     
      Scanner scan = new Scanner(System.in);
      Serializer serializer = new Serializer();
      FileSystem fs = FileSystems.getDefault(); 
      String rootDirPath = System.getProperty("user.dir");
      String saveImageDir = ".projectzero.snapshots"; 
      String saveImagePath = fs.getPath(".projectzero.snapshots", ".projectzero.snapshot").toString();
      CommitHistory history;

      if (args[0].equals("init")) {
        Commit commit = new Commit(Commiter.getRandomId(), new HashSet<Change>(), "initial commit");
        history = new CommitHistory(rootDirPath, "master", commit);
        serializer.createDirectory(saveImageDir);
        serializer.writeToFile(saveImagePath, history);
      }
      if (args[0].equals("commit")) {
        
        history = serializer.readFromFile(saveImagePath);
        Diagram.getTreeDiagram(history); 
        System.out.print("Commit Message: ");
        String message = scan.nextLine();
        (new Commiter()).commit(fs, rootDirPath, saveImageDir, history, message);
        Diagram.getTreeDiagram(history); 
        serializer.writeToFile(saveImagePath, history);

      }
      else if (args[0].equals("restore")) {

        history = serializer.readFromFile(saveImagePath); 
        Diagram.getTreeDiagram(history); 
        System.out.print("Restore to which commit (Type Id): ");
        String id = scan.nextLine();
        (new Restorer()).restoreToCommit(fs, rootDirPath, history, id);
        Diagram.getTreeDiagram(history); 
        serializer.writeToFile(saveImagePath, history);
         
      } 
    
    }

}
