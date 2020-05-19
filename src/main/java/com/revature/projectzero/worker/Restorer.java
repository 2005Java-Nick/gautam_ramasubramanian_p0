package com.revature.projectzero.worker;

import com.revature.projectzero.serializable.CommitHistory;
import java.nio.file.FileSystem;

public class Restorer {

  public boolean restoreToCommit(FileSystem fs, String rootDir, CommitHistory history, String commitId) {
    // TODO: Implement this function
    CommitApplier ca = new CommitApplier(fs);
    for (int i = history.getHeadCommitIndex(); i >= 0; i--) {
        ca.reverse(rootDir, history.getCurrentBranch().get(i));
    }
    history.setHead(commitId);
    for (int i = 0; i <= history.getHeadCommitIndex(); i++) {
        ca.apply(rootDir, history.getCurrentBranch().get(i));
    }
    return true;
  }

}
