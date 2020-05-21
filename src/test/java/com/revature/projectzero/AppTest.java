package com.revature.projectzero;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.revature.projectzero.serializable.*;
import com.revature.projectzero.database.*;
import com.revature.projectzero.worker.*;

import org.junit.Test;

import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(MockitoJUnitRunner.class)
public class AppTest 
{
    
    HashMap<String, ArrayList<Commit>> tree;
    byte[] content = {1, 2};

    private void mockObjects() {
      App.serializer = mock(Serializer.class);
      App.consoleDriver = mock(ConsoleDriver.class);
    }

    private void initializeObjects() {
      App.dao = new MainDAO();
      App.dao.createTables();
      App.fs = Jimfs.newFileSystem(Configuration.unix());
      App.rootDirPath = "root";
      App.saveImageDir = "p0";
      App.saveImagePath = App.fs.getPath("p0", "p0").toString();
    }
   
    private void initializeFileSystem() throws IOException {
      Files.createDirectory(App.fs.getPath("root"));
    }
    
    private void runInitAction() {
      App.initAction();
    }

    private void checkInitAction() {
      tree = App.history.getCommitTree();
      assertTrue(tree.containsKey("master"));
      assertTrue(!tree.containsKey("branch_0"));
      assertEquals(tree.keySet().size(), 1);
      assertEquals(tree.get("master").size(), 1);
      assertEquals(tree.get("master").get(0).getMessage(), "initial commit");
      assertEquals(tree.get("master").get(0).getChanges().size(), 0);
      assertEquals(App.dao.getRootDirs().size(), 1);
      assertEquals(App.dao.getRootDirs().get(0), "root");
    }

    private void changeFileSystem1() throws IOException {
      Files.createDirectory(App.fs.getPath("root", "d1"));
    }

    private void changeFileSystem2() throws IOException {
      Files.createFile(App.fs.getPath("root", "d1", "f1"));
      Files.write(App.fs.getPath("root", "d1", "f1"), content, StandardOpenOption.WRITE); 
    }

    private void changeFileSystem3() throws IOException {
      Files.createDirectory(App.fs.getPath("root", "p0"));
      Files.createFile(App.fs.getPath("root", "p0", "f0"));
      Files.write(App.fs.getPath("root", "p0", "f0"), content, StandardOpenOption.WRITE); 
      Files.createDirectory(App.fs.getPath("root", "d1", "d2"));
    }

    private void runCommitAction() {
      App.commitAction(); 
    }

    private void checkCommitAction() {
      tree = App.history.getCommitTree();
      assertTrue(tree.containsKey("master"));
      assertTrue(!tree.containsKey("branch_0"));
      assertEquals(tree.keySet().size(), 1);
      assertEquals(tree.get("master").size(), 4);
      assertEquals(tree.get("master").get(1).getMessage(), "c1");
      assertEquals(tree.get("master").get(1).getChanges().size(), 1);
      assertTrue(tree.get("master").get(1).getChanges().iterator().next() instanceof AddDirectory);
      assertEquals(tree.get("master").get(2).getMessage(), "c2");
      assertEquals(tree.get("master").get(2).getChanges().size(), 1);
      assertTrue(tree.get("master").get(2).getChanges().iterator().next() instanceof AddFile);
      assertEquals(tree.get("master").get(2).getChanges().iterator().next().getContent().length, 2);
      assertEquals(tree.get("master").get(3).getMessage(), "c3");
      assertEquals(tree.get("master").get(3).getChanges().size(), 1);
      assertTrue(tree.get("master").get(3).getChanges().iterator().next() instanceof AddDirectory);
    }

    private void runPushAction() {
      App.pushAction();
    }

    private void checkPushAction() {
      ArrayList<String> res = App.dao.getRootDirs();
      assertEquals(res.size(), 1);
      assertEquals(res.get(0), "root");
      int rootId = App.dao.getRootId("root");
      assertEquals(rootId, App.history.getId());
      res = App.dao.getBranches(rootId);
      assertEquals(res.size(), 1);
      assertEquals(res.get(0), "master");
      int branchId = App.dao.getBranchId("master", rootId);
      res = App.dao.getCommitsOfBranch(branchId);
      assertEquals(res.size(), 4);
      for (int i = 0; i < 4; i++) {
        assertEquals(res.get(i), App.history.getCommitTree().get("master").get(i).getId()); 
        String msg = App.dao.getMessage(res.get(i));
        assertEquals(msg, App.history.getCommitTree().get("master").get(i).getMessage()); 
        ArrayList<Integer> changeids = App.dao.getChangeIds(res.get(i), rootId);
        if (i == 0) {
          assertEquals(changeids.size(), 0);
        } else {
          assertEquals(changeids.size(), 1);
        } 
      }
    }

    private void changeFileSystem() throws IOException {
      Files.createDirectory(App.fs.getPath("root2"));
    }

    private void changeObjects() {
      App.history = null;
      App.rootDirPath = "root2";
      App.saveImageDir = "p0";
      App.saveImagePath = App.fs.getPath("p0", "p0").toString();
    }

    private void runCloneAction() {
      App.cloneAction();
    }

    private void checkCloneAction() {
      assertNotNull(App.history);
      tree = App.history.getCommitTree();
      int rootId = App.dao.getRootId("root");
      assertEquals(App.history.getId(), rootId);
      assertTrue(tree.containsKey("master"));
      assertTrue(!tree.containsKey("branch_0"));
      assertEquals(tree.keySet().size(), 1);
      assertEquals(tree.get("master").size(), 1);
      assertEquals(tree.get("master").get(0).getMessage(), "initial commit");
    }

    private void runPullAction() {
      App.pullAction();
    }

    private void checkPullAction() {
      tree = App.history.getCommitTree();
      assertTrue(tree.containsKey("master"));
      assertEquals(tree.keySet().size(), 1);
      assertEquals(tree.get("master").size(), 4);
      int branchId = App.dao.getBranchId("master", App.history.getId());
      ArrayList<String> res = App.dao.getCommitsOfBranch(branchId);
      for (int i = 0; i < 4; i++) {
        assertEquals(tree.get("master").get(i).getId(), res.get(i));
        if (i == 0) {
          assertEquals(tree.get("master").get(i).getChanges().size(), 0);
        } else {
          assertEquals(tree.get("master").get(i).getChanges().size(), 1);
        }
      }
    }

    private void runRestoreAction() {
      App.restoreAction();
    }

    private void checkRestoreAction() throws IOException {
      assertTrue(Files.exists(App.fs.getPath("root2", "d1"))); 
      assertTrue(Files.exists(App.fs.getPath("root2", "d1", "d2"))); 
      assertTrue(Files.exists(App.fs.getPath("root2", "d1", "f1"))); 
      assertTrue(!Files.exists(App.fs.getPath("root2", "p0")));
      byte[] newContent = Files.readAllBytes(App.fs.getPath("root2", "d1", "f1"));
      assertEquals(newContent[0], content[0]); 
      assertEquals(newContent[1], content[1]);  
    }

    @Test
    public void testAInitAction() throws IOException
    {
      mockObjects();
      when(App.consoleDriver.newRootInput()).thenReturn("root");      
      initializeObjects();
      initializeFileSystem();
      runInitAction();
      checkInitAction();
    }

    @Test
    public void testBCommitAction() throws IOException
    {
      when(App.consoleDriver.commitMessageInput()).thenReturn("c1").thenReturn("c2").thenReturn("c3");
      when(App.serializer.readFromFile(anyString())).thenReturn(App.history);      
      changeFileSystem1();
      runCommitAction();
      changeFileSystem2();
      runCommitAction();
      changeFileSystem3();
      runCommitAction();
      checkCommitAction();
    }

    @Test
    public void testCPushAction() throws IOException {
      when(App.consoleDriver.branchNameInput()).thenReturn("master");
      when(App.serializer.readFromFile(anyString())).thenReturn(App.history);      
      runPushAction();
      checkPushAction();
    }

    @Test
    public void testDCloneAction() throws IOException {
      when(App.consoleDriver.cloneRootInput()).thenReturn("root");
      changeFileSystem();
      changeObjects();
      runCloneAction();
      checkCloneAction(); 
    }

    @Test
    public void testEPullAction() throws IOException {
      when(App.consoleDriver.branchNameInput()).thenReturn("master");
      when(App.serializer.readFromFile(anyString())).thenReturn(App.history);      
      runPullAction();
      checkPullAction(); 
    }

    @Test
    public void testFRestoreAction() throws IOException {
      when(App.serializer.readFromFile(anyString())).thenReturn(App.history);      
      String commitId = App.history.getCommitTree().get("master").get(3).getId();
      when(App.consoleDriver.commitIdInput()).thenReturn(commitId);
      runRestoreAction();
      checkRestoreAction();
    }

    @AfterClass
    public static void after() {
      App.dao.dropTables();
    }
}
