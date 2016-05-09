package btree;

public class LNode extends Node {

	// next maintains the next leaf node that the current one points to
	protected Node next;
	
	LNode(int capacity, Node parent) {
		super(capacity, parent);
		next = null;
	}
	
	// Setter and Getter of the next node
	public void setNext(Node next) {
		assert(next instanceof LNode);
		this.next = next;
	}
	
	public Node getNext() {
		return next;
	}

	// Implementation of the abstract methods of Node
	public int getLocation(int key) {
		// TO BE IMPLEMENTED
		int location = -1;
		for (int i = 0; i < this.keys.size(); i++) {
	        if (this.keys.get(i) < key) continue;
	        if (this.keys.get(i) == key) location = i;
	        location = i;
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
		LNode nextNode = new LNode(this.capacity, this.parent);
		for (int i = this.capacity/2; i < this.capacity+1; i++) {
			int current = this.keys.get(i);
			nextNode.keys.add(current);
		}
		this.keys.removeAll(nextNode.keys);
		this.setNext(nextNode);
		
		int firstNext = this.next.keys.get(0);
		if (parent != null) {
			INode currentParent = (INode) this.parent;
			int index = currentParent.getLocation(firstNext);
			currentParent.insertChildAt(index+1, this.next);
			currentParent.insertKey(firstNext);
			this.parent = currentParent;
			this.next.parent = currentParent;
		}
		else {
			INode newParent = new INode(this.capacity, null);
			newParent.insertKey(firstNext);
			newParent.insertChildAt(0, this);
			newParent.insertChildAt(1, this.next);
			this.parent = newParent;
			this.next.parent = newParent;
		}
	}
	
	public void deleteKey(int key) {
		assert(BPlusTree.SUPPORT_DELETION);
	}
}
