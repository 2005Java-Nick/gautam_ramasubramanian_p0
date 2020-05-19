package com.revature.projectzero.worker;

import com.revature.projectzero.serializable.Commit;
import com.revature.projectzero.serializable.CommitHistory;
import java.util.HashMap;
import java.util.ArrayList;

public class Diagram {

  private static HashMap<String, String> lines;
  private static HashMap<String, Boolean> shown; 
  
  // Outline
  public static String getTreeDiagram(CommitHistory history) {
    HashMap<String, ArrayList<Commit>> commitTree = history.getCommitTree();
    for (String key : commitTree.keySet()) {
      for (int i = 0; i < commitTree.get(key).size(); i++) {
        Commit c = commitTree.get(key).get(i);
        if (lines.get(c.getId()) == null) {       
          String s = "";
          int j = 0;
          while (j < i) { 
            s += "|"; 
          }
          s += "- " + "[" + c.getId() + "] " + "(" + c.getMessage() + ") " + key;
          lines.put(c.getId(), s);
          shown.put(c.getId(), false); 
        } else {
          String s = lines.get(c.getId());
          s += "," + key;
          lines.put(c.getId(), s);
        }
      }
    }
    lines.put(history.getHeadCommitId(), lines.get(history.getHeadCommitId()) + "<- HEAD");
    String result = "";
    for (String key : commitTree.keySet()) {
      for (Commit c : commitTree.get(key)) {
        if (!shown.get(c.getId())) {
          result += lines.get(c.getId());
          result += "\n";
          shown.put(c.getId(), true);
        }
      }
    }
    return result;
  }

} 
