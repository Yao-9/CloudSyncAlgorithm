package main;

import java.util.ArrayList;

public class FileNode {
	private FileEntry entry;
	private ArrayList<FileNode> children;
	
	public ArrayList<FileNode> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<FileNode> children) {
		this.children = children;
	}

	public void setEntry(FileEntry entry) {
		this.entry = entry;
	}
	
	public FileEntry getEntry() {
		return entry;
	}

	public FileNode(FileEntry entry) {
		this.entry = entry;
		if(entry.getFileType().equals("Folder"))
			children = new ArrayList<>();
	}
	
	public void addSubDir(FileNode node) {
		children.add(node);
	}
}
