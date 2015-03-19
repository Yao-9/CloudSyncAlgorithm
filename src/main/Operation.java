package main;

/* Operation have  types:
 * Type 1: Download from source remote
 * Type 2: Update to Target from local temporary file
 * Type 3: Delete temporary file on local
 * Type 4: Delete Target file on remote
 */
public class Operation {
	private int type;
	private FileEntry targetEntry;
	
	public Operation(int type, FileEntry targetEntry) {
		this.type = type;
		this.targetEntry = targetEntry;
	}

	public int getType() {
		return type;
	}

	public FileEntry getTargetEntry() {
		return targetEntry;
	}
}
