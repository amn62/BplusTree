package btree;

import java.util.ArrayList;

public class INode extends Node{

	protected ArrayList<Node> children;
	
	INode(int capacity, Node parent) {
		super(capacity, parent);
		children = new ArrayList<Node>();
	}

	// Setter and Getter of the children list
	public void insertChildAt(int index, Node child) {
		children.add(index, child);
	}

	public void deleteChildAt(int index) {
		children.remove(index);
	}
	
	public Node getChildAt(int index) {
		return children.get(index);
	}
	
	public ArrayList<Node> getChildren() {
		return children;
	}
	
	// Implementation of the abstract methods of Node
	public int getLocation(int key) {
		// TO BE IMPLEMENTED
		int location = -1;
		for (int i = 0; i < this.keys.size(); i++) {
			if (key < this.keys.get(i)) {
				location = i;
				break;
			}
			else {
				location = i+1;
				continue;
			}
		}
		
		return location;
	}
		
	public void insertKey(int key) {
		// TO BE IMPLEMENTED
		boolean currentFull = this.isFull();
		this.addKey(key);
		if (currentFull) { // keys is full --> split
			this.split();
		}
	}
	
	public void addKey(int key) {
	    for (int i = 0; i < this.keys.size(); i++) {
	        if (this.keys.get(i) < key) continue;
	        if (this.keys.get(i) == key) return;
	        this.keys.add(i, key);
	        return;
	    }
	    this.keys.add(key);
	}

	public void split() {
		// TO BE IMPLEMENTED
		INode nextNode = new INode(this.capacity, this.parent);
		for (int i = 0; i < this.keys.size()/2+1; i++) {
			int current = this.keys.get(i+this.capacity/2);
			nextNode.keys.add(current);
		}
		
		for (int i = 0; i < nextNode.keys.size(); i++) {
			nextNode.insertChildAt(i, this.getChildAt(i+this.capacity/2+1));
		}
		this.keys.removeAll(nextNode.keys);
		this.children.removeAll(nextNode.children);
		
		int firstNext = nextNode.keys.get(0);
		nextNode.keys.remove(0);
		
		if (parent != null) {
			INode currentParent = (INode) this.parent;
			this.parent.insertKey(firstNext);
			int index = currentParent.getLocation(firstNext);
			currentParent.insertChildAt(index, nextNode);
			this.parent = currentParent;
		}
		else {
			INode newParent = new INode(this.capacity, null);
			newParent.insertKey(firstNext);
			newParent.insertChildAt(0, this);
			newParent.insertChildAt(1, nextNode);
			this.parent = newParent;
			nextNode.parent = newParent;
		}	
	}
	
	public void deleteKey(int key) {
		assert(BPlusTree.SUPPORT_DELETION);
	}
}
