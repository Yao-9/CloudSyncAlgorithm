package main;

import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

public class FileTree {
	FileNode root;
	private Hashtable<String, FileEntry> filesTable;
	
	public FileNode getRoot() {
		return root;
	}

	public void setRoot(FileNode root) {
		this.root = root;
	}

	public void setFilesTable(Hashtable<String, FileEntry> filesTable) {
		this.filesTable = filesTable;
	}

	public Hashtable<String, FileEntry> getFilesTable() {
		return filesTable;
	}

	public FileTree() {
		FileEntry fakeRootEntry = new FileEntry("/", "1", "Folder", true, new Date());
		root = new FileNode(fakeRootEntry);
	}

	public void addToRoot(FileEntry file) {
		file.setPath("/" + file.getName());
		FileNode newNode = new FileNode(file);
		root.addSubDir(newNode);
	}
	
	public void AppendingNode(Hashtable<String, FileEntry> filesTable) {
		Queue<FileNode> queue = new LinkedList<FileNode>();
		
		for(FileNode e : root.getChildren()) {
			queue.add(e);
		}
		
		while (queue.isEmpty()) {
			FileNode node = queue.poll();
			FileEntry curEntry = node.getEntry();
			if(curEntry.getFileType().equals("Folder")) {
				LinkedList<String> childrenID = new LinkedList<>();
				/* TODO: List all the children 
				 * childrenList = GoogleAPI.listChildren(node)
				 * */
				for(String id: childrenID) {
					FileEntry entry = filesTable.get(id);
					entry.setPath(entry.getPath() + "/" + entry.getName());
					FileNode newNode = new FileNode(entry);
					node.addSubDir(newNode);
					queue.add(newNode);
					filesTable.put(entry.getPath(), entry);
				}
			}
		}
	}

	public Queue<Operation> compareDiff(FileTree targetTree) {
		Queue<Operation> seq = new LinkedList<>();
		
		Queue<FileNode> BSTQueue = new LinkedList<FileNode>();
		
		Hashtable<String, FileEntry> targetFileTable = targetTree.getFilesTable();
		
		for(FileNode e : root.getChildren()) {
			BSTQueue.add(e);
		}
		
		while (BSTQueue.isEmpty()) {
			FileNode node = BSTQueue.poll();
			FileEntry srcEntry = node.getEntry();
			if(!targetFileTable.contains(srcEntry.getPath())) {
				appendSeq(seq, srcEntry);
			} else {
				FileEntry targetEntry = targetFileTable.get(srcEntry.getPath());
				Date srcEntryLMT = srcEntry.getLastModified();
				Date tarEntryLMT = targetEntry.getLastModified();
				if (srcEntryLMT.compareTo(tarEntryLMT) > 0) {
					replaceSeq(seq, srcEntry, targetEntry);
				}
			}
		}
		return seq;
	}

	private void replaceSeq(Queue<Operation> seq, FileEntry srcEntry,
			FileEntry targetEntry) {
		Operation downloadOp = new Operation(1, srcEntry);
		Operation delRemoteOp = new Operation(4, targetEntry);
		Operation uploadOp = new Operation(2, srcEntry);
		Operation delLocalOp = new Operation(3, srcEntry);
		seq.add(downloadOp);
		seq.add(delRemoteOp);
		seq.add(uploadOp);
		seq.add(delLocalOp);
	}

	private void appendSeq(Queue<Operation> seq, FileEntry srcEntry) {
		Operation downloadOp = new Operation(1, srcEntry);
		Operation uploadOp = new Operation(2, srcEntry);
		Operation delLocalOp = new Operation(3, srcEntry);
		seq.add(downloadOp);
		seq.add(uploadOp);
		seq.add(delLocalOp);
	}
	
}
