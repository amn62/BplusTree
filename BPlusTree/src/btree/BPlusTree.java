package btree;

import java.io.*;
import java.util.*;

public class BPlusTree {
	public static final boolean SUPPORT_DELETION = false;
	private Node root;
	
	BPlusTree(int capacity) {
		root = new LNode(capacity, null);
	}
	
	// For unit testing
	// Return root
	public Node getRoot() {
		return root;
	}
	
	// Return the leaf node that contains the key
	// Return null if the key is not in the tree
	public Node find(int key) {
		Node result = root;
//		// TO BE IMPLEMENTED
		if (result instanceof LNode) {
			return result;
		}
		
		while (result instanceof INode) {
			INode inner = (INode) result;
			int index = inner.getLocation(key);
			result = inner.children.get(index);
		}
		
		return result;
	}
	
	public void insert(int key) {
		// TO BE IMPLEMENTED
		LNode toInsert = (LNode) this.find(key);
		toInsert.insertKey(key);
		if (root.parent != null) {
			INode newRoot = (INode) root.parent;
			root = newRoot;
		}
	}
	
	public void delete(int key) {
		assert(BPlusTree.SUPPORT_DELETION);
	}
	
	public static void main(String[] Args) {
		BPlusTree bPlusTree = new BPlusTree(6);
		// READ FROM INPUT FILE input.txt
		// EACH LINE OF THE TEXT FILE CONTAINTS A NUMBER TO BE INSERTED INTO THE TREE
		String fileName = "/Users/alexnino/Desktop/amn62bplus/data.txt";
		
		String line = null;
		int key = -1;
		
		try {
			Scanner scanner = new Scanner(new File(fileName));
			while(scanner.hasNextInt()){
			   key = scanner.nextInt();
			   bPlusTree.insert(key);
			}
			bPlusTree.print();
		}
		catch(FileNotFoundException ex) {
			System.out.println("Unable to open file \"" + fileName + "\"");
		}
		
	}
	
	public void print(){
		System.out.println("printing B+ tree contents:");
		printRecursive(root, 0);
	}
	
	private void printRecursive(Node root, int lvl){
		for(int i = 0; i < lvl; i++){
			System.out.print("\t");
		}
		for(int key: root.getKeys()){
			System.out.print(key + " ");
		}
		System.out.println();
		if(root instanceof INode){
			for(Node child: ((INode)root).getChildren()){
				printRecursive(child, lvl + 1);
			}
		}
	}
}