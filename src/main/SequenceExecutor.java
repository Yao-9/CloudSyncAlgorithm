package main;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

public class SequenceExecutor {
	Hashtable<String, FileEntry> srcTable;
	Hashtable<String, FileEntry> tarTable;
	Queue<Operation> seq = new LinkedList<>();
	
	public SequenceExecutor(Hashtable<String, FileEntry> srcTable,
			Hashtable<String, FileEntry> tarTable, Queue<Operation> seq) {
		this.srcTable = srcTable;
		this.tarTable = tarTable;
		this.seq = seq;
	}
	
	public void execute() {
		int l = seq.size();
		for (int i = 0; i < l; i++) {
			Operation op = seq.poll();
			switch (op.getType()) {
			case 1:
				DownloadSrcFile(op);
				break;
			case 2:
				UploadDstFile(op);
			case 3:
				DelLocal(op);
			case 4:
				DelDstRemote(op);
			default:
				break;
			}
		}
	}
	
	/* TODO: Dependent on API */
	private void DelDstRemote(Operation op) {
		
	}
	
	/* TODO: Dependent on API */
	private void DelLocal(Operation op) {
		
	}
	
	/* TODO: Dependent on API */
	private void UploadDstFile(Operation op) {
		
	}
	
	/* TODO: Dependent on API */
	private void DownloadSrcFile(Operation op) {
		
	}
}
