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

    args[0] = content.getClass();
    privateSetContent = AddFile.class.getDeclaredMethod("setContent", args);
    privateSetContent.setAccessible(true);
    
    addFile = new AddFile(pathString, content);
  }

  @Test
  public void testExpectedPathString() throws IllegalAccessException {
    assertTrue("pathString is equal to what was set in constructor", ((String) privatePathString.get(addFile)) == pathString);
    assertTrue("pathString is equal to what was set in constructor", ((String) privatePathString.get(addFile)).equals(pathString));
  } 

  @Test
  public void testExpectedContent() throws IllegalAccessException {
    assertTrue("content is equal to what was set in constructor", ((byte[]) privateContent.get(addFile)) == content);
    assertTrue("content is equal to what was set in constructor", Arrays.equals(((byte[]) privateContent.get(addFile)), content));
  }

  @Test
  public void testGetPathString() {
    assertTrue("getPathString returns pathString", addFile.getPathString() == pathString);
    assertTrue("getPathString returns pathString", addFile.getPathString().equals(pathString));
  }

  @Test
  public void testGetContent() {
    assertTrue("getPathString returns pathString", addFile.getContent() == content);
    assertTrue("getPathString returns pathString", Arrays.equals(addFile.getContent(), content));
  }

  @Test
  public void testSetPathString() throws IllegalAccessException, InvocationTargetException {
    String newPathString = "new/path/string";
    privateSetPathString.invoke(addFile, newPathString);
    assertTrue("pathString is changed due to setPathString", ((String) privatePathString.get(addFile)) == newPathString);
    assertTrue("pathString is changed due to setPathString", ((String) privatePathString.get(addFile)).equals(newPathString));

  }

  @Test
  public void testSetContent() throws IllegalAccessException, InvocationTargetException {
    byte[] newContent = {3,4,5,6};
    privateSetContent.invoke(addFile, newContent);
    assertTrue("content is changed due to setContent", ((byte[]) privateContent.get(addFile)) == newContent);
    assertTrue("content is changed due to setContent", Arrays.equals((byte[]) privateContent.get(addFile), newContent));
  }

  @After
  public void setPrivateMethodsNotAccessible() {
    privatePathString.setAccessible(false);
    privateContent.setAccessible(false);
    privateSetPathString.setAccessible(false);
    privateSetContent.setAccessible(false);
  }
}
