package btree;

import java.io.*;
import java.util.ArrayList;

public abstract class Node {
	protected int capacity;
	protected Node parent;

	protected ArrayList<Integer> keys;
	
	Node(int capacity, Node parent) {
//		System.out.println("New Node with capacity " + capacity);
		this.capacity = capacity;
		this.keys = new ArrayList<Integer>();
		this.parent = parent;
	}

	public boolean isFull() {
		return keys.size() >= capacity;
	}

	public boolean isHalfFull() {
		return keys.size() <= capacity / 2;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public int getSize() {
		return keys.size();
	}
	
	public ArrayList<Integer> getKeys() {
		return keys;
	}

	public Node getParent() {
		return parent;
	}
	
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	// getLocation returns the index in the key list that matches the key
	// For LNodes, it returns i, the index of the key such that keys[i] == key, and -1 if the key cannot be found
	// For INodes, it returns i, the index of the key such that keys[i-1] <= key < keys[i]; note the boundary conditions
	abstract public int getLocation(int key);

	// insertKey inserts the key into the key list
	abstract public void insertKey(int key);
	
	// split is called when a key list overflows (i.e., it exceeds its capacity)
	// split splits a node into two nodes and pushes up a key to the parent node
	abstract public void split();
	
	// OPTIONAL:
	//
	// deleteKey deletes the key from the key list
	// deleteKey return 0 if the deletion succeeds; or -1 when it fails
	abstract public void deleteKey(int key);
}