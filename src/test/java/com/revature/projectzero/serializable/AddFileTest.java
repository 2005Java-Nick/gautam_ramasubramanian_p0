package com.revature.projectzero.serializable;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

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

    //privateSetPathString = AddFile.class.getDeclaredMethod("setPathString");
    //privateSetPathString.setAccessible(true);

    //privateSetContent = AddFile.class.getDeclaredMethod("setContent");
    //privateSetContent.setAccessible(true);
  }

  @Test
  public void testExpectedPathString() throws IllegalAccessException {
    addFile = new AddFile(pathString, content);
    assertTrue("pathString is equal to what was set in constructor", ((String) privatePathString.get(addFile)) == pathString);
    assertTrue("pathString is equal to what was set in constructor", ((String) privatePathString.get(addFile)).equals(pathString));
  } 

  @Test
  public void testExpectedContent() throws IllegalAccessException {
    addFile = new AddFile(pathString, content);
    assertTrue("content is equal to what was set in constructor", ((byte[]) privateContent.get(addFile)) == content);
    assertTrue("content is equal to what was set in constructor", Arrays.equals(((byte[]) privateContent.get(addFile)), content));
  }

  @After
  public void setPrivateMethodsNotAccessible() {
    privatePathString.setAccessible(false);
    privateContent.setAccessible(false);
    //privateSetPathString.setAccessible(false);
    //privateSetContent.setAccessible(false);
  }
}
