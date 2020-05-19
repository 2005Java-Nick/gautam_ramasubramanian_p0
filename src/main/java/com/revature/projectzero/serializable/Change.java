package com.revature.projectzero.serializable;

/* Represents a change of file/directory -> Adding or Deleting 
 *  Changing the contents of a file is equivalent to deleting the old file and adding the new one 
 */


public interface Change {
  
  /* FilePath of file that you are changing */
  public abstract String getPathString();

  /* Content of the file you are changing */
  public abstract byte[] getContent();

}


