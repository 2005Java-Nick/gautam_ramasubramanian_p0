# Project 0

A simple version control program.
It takes a snapshot of a directory and stores it to file.
It can also restore the directory from that snapshot image

## How to run this program

If you are using the Maven Build tool, just run the command `mvn package`.
That will produce an executable JAR file located in the target/ directory named gautam\_ramasubramanian\_p0-1.0-SNAPSHOT.jar
Then 'cd' to the directory you want to version control and simply run `java -jar gautam_ramasubramanian_p0-1.0-SNAPSHOT.jar (command)`.

The command can be `save` or `restore`.
Save will save a snapshot of the current working directory and all files inside it.
Restore will restore the directory based on the snapshot.

Snapshot will be stored in ./.projectzero-snapshots/.projectzero-snapshot

If you are using STS, then import this repo as a Maven project.
Right click on the project in the project explorer, go to Run As, and then click Maven Project.
There will be a text field that says 'goals'. Type in the word 'package'.
Then Run, and it will generate the jar file above.

## Notes

There are three tasks that the program needs to do
  - Initialize, Snapshot, Restore Snapshot, Delete
  - Only works for text files.
  - Initialize
    - Make a (hidden) folder to store snapshots
  - Snapshot
    - Checks for signs of initialization
    - Goes to each file in the directory, and turns contents into string
      - Use Depth First Search
      - Capture directories also
    - Store all the information in a Map\<String, String\>, where the key
        is the filepath and value is the contents
    - Serialize the Map and store it as an image to the hidden folder.
  - Restore
    - Check for signs of initialization
    - Check for snapshots with particular name
    - Remove all text files (leave binary files intact).
    - Unserialize the map
    - For every key in the map
      - If it is directory, make the directory
      - If it is a file, make the file and fill it with appropriate contents
    - Update Last Restore Image
  - Delete
    - Delete folder of snapshots
  - Classes 
    - (Singleton or Static) FileModifier Class
      - All static
      - Property: Current Working Directory
      - Property: List of all subfile paths
      - Property: List of all directories
      - Getter Method: Get Current Working Directory
      - Getter Method: List all files (stored in snapshot) (going recursively down cwd)
      - Getter Method: List all directories (stored in snapshot) (going recursively down cwd)
      - Method: Get File Content
      - Method: Delete File
      - Method: Create File
      - Method: Set File Content
      - Method: Create Directory
      - Method: Delete Directory
    - (immutable) SnapshotFile
      - Path
      - Contents (byte array)
      - Type
    - (immutable) SnapshotDirectory extends SnapshotFile
      - Path
      - Type
    - (immutable) Snapshot
      - id
      - date+time
      - ArrayList(SnapShotFiles)
    - (Singleton or Static) SnapshotFactory Class
      - Pass in a FileHandler
      - Method: Make a snapshot 
    - (Singleton or Static) SnapshotRestore Class
      - Pass in a FileHandler and Snapshot
      - Method: Restore a snapshot 
    - SnapShotTree Class
      - Property: Tree<SnapShots>
      - Property: Snapshot reference which is the parent
      - Method: AddnewSnapshot 
      - Getter Method: Get Snapshot which is the parent
      - Method: Get snapshot by id
    - Driver Class


