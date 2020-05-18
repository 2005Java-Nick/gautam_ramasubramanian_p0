package com.revature.projectzero.serializable;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

public class AddFileTest {
 
  AddFile addFile;
  Field privatePathString;
  Field privateContent;
  Method privateSetPathString;
  Method privateSetContent;
  String pathString = "test/path";
  byte[] content = { 2, 45, 32, 3 };


  @Before
  public void setPrivateMethodsAccessible() throws NoSuchFieldException, NoSuchMethodException {
    privatePathString = AddFile.class.getDeclaredField("pathString");
    privatePathString.setAccessible(true);
    
    privateContent = AddFile.class.getDeclaredField("content");
    privateContent.setAccessible(true);

    Class[] args = new Class[1];
    args[0] = pathString.getClass();
    privateSetPathString = AddFile.class.getDeclaredMethod("setPathString", args);
    privateSetPathString.setAccessible(true);

    args = new Class[1];
    args[0] = content.getClass();
    privateSetContent = AddFile.class.getDeclaredMethod("setContent", args);
    privateSetContent.setAccessible(true);
    
    addFile = new AddFile(pathString, content);
  }

  @Test
  public void testExpectedPathString() throws IllegalAccessException {
    assertTrue("private field pathString is equal in reference to what was set in constructor", 
        ((String) privatePathString.get(addFile)) == pathString);
    assertTrue("private field pathString is equal in value to what was set in constructor", 
        ((String) privatePathString.get(addFile)).equals(pathString));
  } 

  @Test
  public void testExpectedContent() throws IllegalAccessException {
    assertTrue("private field content is equal in reference to what was set in constructor", 
        ((byte[]) privateContent.get(addFile)) == content);
    assertTrue("private field content is equal in value to what was set in constructor", 
        Arrays.equals(((byte[]) privateContent.get(addFile)), content));
  }

  @Test
  public void testGetPathString() {
    assertTrue("getPathString is equal in reference to local variable pathString", 
        addFile.getPathString() == pathString);
    assertTrue("getPathString is equal in value to local variable pathString", 
        addFile.getPathString().equals(pathString));
  }

  @Test
  public void testGetContent() {
    assertTrue("getContent is equal in reference to local array content", 
        addFile.getContent() == content);
    assertTrue("getContent is equal in value to local array content", 
        Arrays.equals(addFile.getContent(), content));
  }

  @Test
  public void testSetPathString() throws IllegalAccessException, InvocationTargetException {
    String newPathString = "new/path/string";
    privateSetPathString.invoke(addFile, newPathString);
    assertTrue("private field pathString is changed (in reference) due to setPathString method", 
        ((String) privatePathString.get(addFile)) == newPathString);
    assertTrue("private field pathString is changed (in value) due to setPathString method", 
        ((String) privatePathString.get(addFile)).equals(newPathString));

  }

  @Test
  public void testSetContent() throws IllegalAccessException, InvocationTargetException {
    byte[] newContent = {3,4,5,6};
    privateSetContent.invoke(addFile, newContent);
    assertTrue("private field content is changed (in reference) due to setContent method", 
        ((byte[]) privateContent.get(addFile)) == newContent);
    assertTrue("private field content is changed (in value) due to setContent method", 
        Arrays.equals((byte[]) privateContent.get(addFile), newContent));
  }

  @After
  public void setPrivateMethodsNotAccessible() {
    privatePathString.setAccessible(false);
    privateContent.setAccessible(false);
    privateSetPathString.setAccessible(false);
    privateSetContent.setAccessible(false);
  }
}
