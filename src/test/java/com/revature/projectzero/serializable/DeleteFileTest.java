package com.revature.projectzero.serializable;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

public class DeleteFileTest {
 
  DeleteFile deleteFile;
  Field privatePathString;
  Field privateContent;
  Method privateSetPathString;
  Method privateSetContent;
  String pathString = "test/path";
  byte[] content = { 2, 45, 32, 3 };


  @Before
  public void setPrivateMethodsAccessible() throws NoSuchFieldException, NoSuchMethodException {
    privatePathString = DeleteFile.class.getDeclaredField("pathString");
    privatePathString.setAccessible(true);
    
    privateContent = DeleteFile.class.getDeclaredField("content");
    privateContent.setAccessible(true);

    Class[] args = new Class[1];
    args[0] = pathString.getClass();
    privateSetPathString = DeleteFile.class.getDeclaredMethod("setPathString", args);
    privateSetPathString.setAccessible(true);

    args = new Class[1];
    args[0] = content.getClass();
    privateSetContent = DeleteFile.class.getDeclaredMethod("setContent", args);
    privateSetContent.setAccessible(true);
    
    deleteFile = new DeleteFile(pathString, content);
  }

  @Test
  public void testExpectedPathString() throws IllegalAccessException {
    assertTrue("private field pathString is equal in reference to what was set in constructor", 
        ((String) privatePathString.get(deleteFile)) == pathString);
    assertTrue("private field pathString is equal in value to what was set in constructor", 
        ((String) privatePathString.get(deleteFile)).equals(pathString));
  } 

  @Test
  public void testExpectedContent() throws IllegalAccessException {
    assertTrue("private field content is equal in reference to what was set in constructor", 
        ((byte[]) privateContent.get(deleteFile)) == content);
    assertTrue("private field content is equal in value to what was set in constructor", 
        Arrays.equals(((byte[]) privateContent.get(deleteFile)), content));
  }

  @Test
  public void testGetPathString() {
    assertTrue("getPathString is equal in reference to local variable pathString", 
        deleteFile.getPathString() == pathString);
    assertTrue("getPathString is equal in value to local variable pathString", 
        deleteFile.getPathString().equals(pathString));
  }

  @Test
  public void testGetContent() {
    assertTrue("getContent is equal in reference to local array content", 
        deleteFile.getContent() == content);
    assertTrue("getContent is equal in value to local array content", 
        Arrays.equals(deleteFile.getContent(), content));
  }

  @Test
  public void testSetPathString() throws IllegalAccessException, InvocationTargetException {
    String newPathString = "new/path/string";
    privateSetPathString.invoke(deleteFile, newPathString);
    assertTrue("private field pathString is changed (in reference) due to setPathString method", 
        ((String) privatePathString.get(deleteFile)) == newPathString);
    assertTrue("private field pathString is changed (in value) due to setPathString method", 
        ((String) privatePathString.get(deleteFile)).equals(newPathString));

  }

  @Test
  public void testSetContent() throws IllegalAccessException, InvocationTargetException {
    byte[] newContent = {3,4,5,6};
    privateSetContent.invoke(deleteFile, newContent);
    assertTrue("private field content is changed (in reference) due to setContent method", 
        ((byte[]) privateContent.get(deleteFile)) == newContent);
    assertTrue("private field content is changed (in value) due to setContent method", 
        Arrays.equals((byte[]) privateContent.get(deleteFile), newContent));
  }

  @After
  public void setPrivateMethodsNotAccessible() {
    privatePathString.setAccessible(false);
    privateContent.setAccessible(false);
    privateSetPathString.setAccessible(false);
    privateSetContent.setAccessible(false);
  }
}
