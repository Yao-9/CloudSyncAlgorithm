package main;

import java.util.Date;

public class FileEntry {
	private String name;
	private String id;
	/* Folder Or File */
	private String fileType;
	private String path;
	private boolean isRoot;
	private Date lastModified;
	
	public Date getLastModified() {
		return lastModified;
	}

	public boolean isRoot() {
		return isRoot;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}
	
	public String getFileType() {
		return fileType;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return path;
	}

	public FileEntry(String name, String id, String fileType, boolean isRoot, Date lastModified) {
		this.name = name;
		this.id = id;
		this.fileType = fileType;
		this.isRoot = isRoot;
		this.lastModified = lastModified;
	}
	
	public boolean equalTo(FileEntry fe) {
		return 	(this.name == fe.getName()) &&
				(this.id == fe.getId()) &&
				(this.fileType == fe.getFileType()) &&
				(this.isRoot == fe.isRoot()) &&
				(this.lastModified == fe.getLastModified());
	}
}
