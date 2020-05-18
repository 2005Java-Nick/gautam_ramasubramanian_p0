package com.revature.projectzero.serializable;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;

@RunWith(MockitoJUnitRunner.class)
public class CommitTest {
 
  Commit commit;
  Field privateId, privateChanges, privateMessage;
  Method setPrivateId, setPrivateChanges, setPrivateMessage;
  String id = "6df2e4";
  String message = "example commit";
  ArrayList<Change> changes;
  int numChanges = 4;


  @Before
  public void setPrivateMembersAccessible() throws NoSuchFieldException, NoSuchMethodException {
    changes = new ArrayList<Change>();
    for (int i = 0; i < numChanges; i++) {
      changes.add(mock(Change.class));
    }
    
    privateId = Commit.class.getDeclaredField("id");
    privateId.setAccessible(true);
  
    privateChanges = Commit.class.getDeclaredField("changes");
    privateChanges.setAccessible(true);

    privateMessage = Commit.class.getDeclaredField("message");
    privateMessage.setAccessible(true);

    Class[] args = new Class[1];
    args[0] = id.getClass(); 
    setPrivateId = Commit.class.getDeclaredMethod("setId", args);
    setPrivateId.setAccessible(true);
    
    args = new Class[1];
    args[0] = changes.getClass();
    setPrivateChanges = Commit.class.getDeclaredMethod("setChanges", args);
    setPrivateChanges.setAccessible(true);

    args = new Class[1];
    args[0] = message.getClass();
    setPrivateMessage = Commit.class.getDeclaredMethod("setMessage", args);
    setPrivateMessage.setAccessible(true);

    commit = new Commit(id, changes, message);
  }

  @Test
  public void testConstructorSetId() throws IllegalAccessException {
    assertTrue("private field id is equal in reference to what was passed to constructor",
      (String) privateId.get(commit) == id);
    assertTrue("private field id is equal in value to what was passed to constructor",
      ((String) privateId.get(commit)).equals(id)); 
  }

  @Test
  public void testConstructorSetChanges() throws IllegalAccessException {
    assertTrue("private field changes is equal in reference to what was passed to constructor",
      (ArrayList<Change>) privateChanges.get(commit) == changes);
    assertTrue("private field changes is equal in value to what was passed to constructor",
      ((ArrayList<Change>) privateChanges.get(commit)).equals(changes)); 
  }

  @Test
  public void testConstructorSetMessage() throws IllegalAccessException {
    assertTrue("private field message is equal in reference to what was passed to constructor",
      (String) privateMessage.get(commit) == message);
    assertTrue("private field message is equal in value to what was passed to constructor",
      ((String) privateMessage.get(commit)).equals(message)); 
  }

  @Test
  public void testGetId() {
    assertTrue("getId is equal in reference to local variable id", commit.getId() == id);
    assertTrue("getId is equal in value to local variable id", commit.getId().equals(id));
  }

  @Test
  public void testGetChanges() {
    assertTrue("getChanges is equal in reference to local variable changes", commit.getChanges() == changes);
    assertTrue("getChanges is equal in value to local variable changes", commit.getChanges().equals(changes));
  }

  @Test
  public void testGetMessage() {
    assertTrue("getMessage is equal in reference to local variable id", commit.getMessage() == message);
    assertTrue("getMessage is equal in value to local variable id", commit.getMessage().equals(message));
  }

  @Test
  public void testSetId() throws IllegalAccessException, InvocationTargetException {
    String newId = "56782e";
    setPrivateId.invoke(commit, newId);
    assertTrue("setId method changed private field id to local variable newId (reference)", 
        (String) privateId.get(commit) == newId);
    assertTrue("setId method changed private field id to local variable newId (value)",
        ((String) privateId.get(commit)).equals(newId));
  }

  @Test
  public void testSetChanges() throws IllegalAccessException, InvocationTargetException {
    ArrayList<Change> newChanges = new ArrayList<Change>();
    newChanges.add(mock(Change.class));
    setPrivateChanges.invoke(commit, newChanges);
    assertTrue("setChanges method changed private field changes to local variable newChanges (reference)", 
        (ArrayList<Change>) privateChanges.get(commit) == newChanges);
    assertTrue("setChanges method changed private field changes to local variable newChanges (value)", 
        ((ArrayList<Change>) privateChanges.get(commit)).equals(newChanges));
  }

  @Test
  public void testSetMessage() throws IllegalAccessException, InvocationTargetException {
    String newMessage = "test commit 2";
    setPrivateMessage.invoke(commit, newMessage);
    assertTrue("setMessage method changed private field message to local variable newMessage (reference)", 
        (String) privateMessage.get(commit) == newMessage);
    assertTrue("setMessage method changed private field message to local variable newMessage (value)",
        ((String) privateMessage.get(commit)).equals(newMessage));
  }

  @After
  public void setPrivateMembersNotAccessible() {
    privateId.setAccessible(false);
    privateChanges.setAccessible(false);
    privateMessage.setAccessible(false);
    setPrivateId.setAccessible(false);
    setPrivateChanges.setAccessible(false);
    setPrivateMessage.setAccessible(false);
  } 

}
