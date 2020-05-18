package com.revature.projectzero.serializable;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

public class DeleteDirectoryTest {
 
  DeleteDirectory deleteDirectory;
  Field privatePathString;
  Method privateSetPathString;
  String pathString = "test/path";

  @Before
  public void setPrivateMethodsAccessible() throws NoSuchFieldException, NoSuchMethodException {
    privatePathString = DeleteDirectory.class.getDeclaredField("pathString");
    privatePathString.setAccessible(true);
    
    Class[] args = new Class[1];
    args[0] = pathString.getClass();
    privateSetPathString = DeleteDirectory.class.getDeclaredMethod("setPathString", args);
    privateSetPathString.setAccessible(true);

    deleteDirectory = new DeleteDirectory(pathString);
  }

  @Test
  public void testExpectedPathString() throws IllegalAccessException {
    assertTrue("private field pathString is equal in reference to what was set in constructor", 
        ((String) privatePathString.get(deleteDirectory)) == pathString);
    assertTrue("private field pathString is equal in value to what was set in constructor", 
        ((String) privatePathString.get(deleteDirectory)).equals(pathString));
  } 

  @Test
  public void testGetPathString() {
    assertTrue("getPathString is equal in reference to local variable pathString", 
        deleteDirectory.getPathString() == pathString);
    assertTrue("getPathString is equal in value to local variable pathString", 
        deleteDirectory.getPathString().equals(pathString));
  }

  @Test
  public void testGetContent() {
    assertTrue("getContent is null", deleteDirectory.getContent() == null);
  }

  @Test
  public void testSetPathString() throws IllegalAccessException, InvocationTargetException {
    String newPathString = "new/path/string";
    privateSetPathString.invoke(deleteDirectory, newPathString);
    assertTrue("private field pathString is changed (in reference) due to setPathString method", 
        ((String) privatePathString.get(deleteDirectory)) == newPathString);
    assertTrue("private field pathString is changed (in value) due to setPathString method", 
        ((String) privatePathString.get(deleteDirectory)).equals(newPathString));

  }

  @After
  public void setPrivateMethodsNotAccessible() {
    privatePathString.setAccessible(false);
    privateSetPathString.setAccessible(false);
  }
}
