package btree;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

public class TestBPlusTree {

	@Test
	public void InsertIntoEmptyLeafWithNoParent() {
		// leaf node to insert into
		LNode testNode = new LNode(6, null);
		// test array to compare with leaf.keys
		ArrayList<Integer> testKeys = new ArrayList<Integer>();
		
		testNode.insertKey(1);
		testKeys.add(1);
		
		assertEquals(testNode.keys, testKeys);
	}
	
	@Test
	public void FillLeafWithNoParent() {
		// leaf node to insert into
		LNode testNode = new LNode(6, null);
		// test array to compare with leaf.keys
		ArrayList<Integer> testKeys = new ArrayList<Integer>();
		
		// fill leaf node and array with [1,2,3,4,5,6]
		for (int i = 1; i < testNode.capacity+1; i++) {
			testNode.insertKey(i);
		}
		testKeys.addAll(Arrays.asList(1,2,3,4,5,6));
		
		assertEquals(testNode.keys, testKeys);
	}
	
	@Test
	public void InsertIntoFullLeafWithNoParent() {
		// leaf node to insert into
		LNode testNode = new LNode(6, null);
		// test arrays to compare with leaf.keys and parent.keys
		ArrayList<Integer> leftLeafKeys = new ArrayList<Integer>();
		ArrayList<Integer> rightLeafKeys = new ArrayList<Integer>();
		ArrayList<Integer> parentKeys = new ArrayList<Integer>();
		
		// fill leaf node with [1,2,3,4,5,6,7]
		for (int i = 1; i < testNode.capacity+2; i++) {
			testNode.insertKey(i);
		}
		
		// set appropriate values for left, right and parent arrays
		leftLeafKeys.addAll(Arrays.asList(1, 2, 3));
		rightLeafKeys.addAll(Arrays.asList(4, 5, 6, 7));
		parentKeys.add(4);
		
		// compare new parent, left child, right child to test arrays
		assertEquals(testNode.keys, leftLeafKeys);
		assertEquals(testNode.next.keys, rightLeafKeys);
		assertEquals(testNode.parent.keys, parentKeys);
	}
	
	@Test
	public void InsertIntoFullLeafWithFullParent() {
		// test tree to insert into
		BPlusTree testTree = new BPlusTree(6);
		// test arrays to compare inner.keys and parent.keys
		ArrayList<Integer> rootBefore = new ArrayList<Integer>();
		ArrayList<Integer> leftInnerKeys = new ArrayList<Integer>();
		ArrayList<Integer> rightInnerKeys = new ArrayList<Integer>();
		ArrayList<Integer> parentKeys = new ArrayList<Integer>();
		
		// fill tree with values from 1-24
		for (int i = 1; i < 25; i++) {
			testTree.insert(i);
		}
		
		// fill test root
		rootBefore.addAll(Arrays.asList(4,7,10,13,16,19));
		
		// compare tree root to test root
		assertEquals(testTree.getRoot().getKeys(), rootBefore);
		
		// insert 25
		testTree.insert(25);
		
		leftInnerKeys.addAll(Arrays.asList(4,7,10));
		rightInnerKeys.addAll(Arrays.asList(16,19,22));
		parentKeys.add(13);
		
		// set Inner Nodes to test tree root, left inner, right inner
		INode parentNode = (INode) testTree.getRoot();
		INode leftInnerNode = (INode) parentNode.getChildAt(0);
		INode rightInnerNode = (INode) parentNode.getChildAt(1);
		
		// compare root, left inner, right inner to test arrays
		assertEquals(leftInnerNode.getKeys(), leftInnerKeys);
		assertEquals(rightInnerNode.getKeys(), rightInnerKeys);
		assertEquals(parentNode.getKeys(), parentKeys);
	}

}
