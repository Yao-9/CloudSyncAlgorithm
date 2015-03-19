package main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Queue;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

public class AccountFiles {
	/* Hashtable storing the Files only 
	 * Key: Path
	 * Value: FileEntry    */
	private FileTree fileTree;
	
	public FileTree getFileTree() {
		return fileTree;
	}

	public AccountFiles(JSONObject jsono) {
		parseToDirTree(jsono);
	}

	/* 1. Init a HashTable<id, FileEntry>, for each items, get id, name, filetype, checksum. Then Store it to the list 
	 * 2. add child of root to the tree. 
	 * 3. Iterate the tree using BFS, then add all the files to the HashTable*/
	private void parseToDirTree(JSONObject jsono) {
		/* Step 1 
		 * Probably gonna immigrate this function to GoogleAPI Class */
		Hashtable<String, FileEntry> filesTable = parseToHashTable(jsono);
		
		/* Step 2 */
		AddRootToTree(filesTable);
		
		/* Step 3 */
		fileTree.AppendingNode(filesTable);
	}
	
	private void AddRootToTree(Hashtable<String, FileEntry> filesTable) {
		Set<String> keys = filesTable.keySet();
		for(String e: keys) {
			FileEntry file = filesTable.get(e);
			if(file.isRoot()) {
				this.fileTree.addToRoot(file);
			}
		}
	}
	
	private Hashtable<String, FileEntry> parseToHashTable(JSONObject jsono) {
		Hashtable<String, FileEntry> filesTable = new Hashtable<>();
		JSONArray items_JSON = jsono.getJSONArray("items");
		for (int i = 0; i < items_JSON.length(); i++) {
			JSONObject item_JSON = items_JSON.getJSONObject(i);
			String name = item_JSON.getString("title");
			String id = item_JSON.getString("id");
			String fileType = (item_JSON.getString("mimeType").
					equals("application/vnd.google-apps.folder")) ? "Folder" : "File";
			boolean isRoot = getRootInfo(item_JSON);
			String dateStr = item_JSON.getString("modifiedDate");
			Date lmdate = new Date();
			SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			try {
				lmdate = parser.parse(dateStr);
			} catch (ParseException e) {
				System.err.println("Err: parseErr when parse Last Modified Time");
				e.printStackTrace();
			}
			FileEntry newEntry = new FileEntry(name, id, fileType, isRoot, lmdate);
			filesTable.put(id, newEntry);
		}
		return filesTable;
	}

	private boolean getRootInfo(JSONObject item_JSON) {
		JSONArray parents_arr = item_JSON.getJSONArray("parents");
		JSONObject parents = parents_arr.getJSONObject(0);
		return parents.getBoolean("isRoot");
	}
	
	public Queue<Operation> compareDiff(AccountFiles another) {
		Queue<Operation> seq;
		FileTree srcTree = this.fileTree;
		FileTree targetTree = another.getFileTree();
		
		seq = srcTree.compareDiff(targetTree);
		
		return seq;
	}
}