package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import main.FileEntry;
import main.FileNode;
import main.FileTree;

import org.junit.Before;
import org.junit.Test;

public class FileTreeTest {
	
	FileTree tree;
	
	@Before
    public void setUp() {
		tree = new FileTree();
    }

	@Test
	public void addToRootTest() {
		FileEntry rootChild = new FileEntry("123", "12345", "Folder", true, new Date());
		tree.addToRoot(rootChild);
		
		FileNode rt = tree.getRoot();
		ArrayList<FileNode> l = rt.getChildren();
		assertTrue(l.get(0).getEntry().equalTo(rootChild));
	}
}
