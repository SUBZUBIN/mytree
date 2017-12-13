package com.sixdee.tree;

import java.util.Collection;
import java.util.Iterator;

import com.sixdee.tree.exception.NodeNotFoundException;

/**
 * @author subin.soman
 *
 */
class ArrayListTreeHelper {
	public <Node, Node2> boolean isEqual(Tree<Node> testTree, Tree<Node2> thisTree, Node testNode, Node2 thisNode) throws NodeNotFoundException {
		if ((thisNode == null && testNode == null))
			return true;
		else if (thisNode != null && testNode != null && thisNode.equals(testNode)) {
			Collection<Node> testChildren = testTree.children(testNode);
			Collection<Node2> thisChildren = thisTree.children(thisNode);
			if (testChildren.equals(thisChildren)) {
				Iterator<Node> i = testChildren.iterator();
				Iterator<Node2> j = thisChildren.iterator();
				for (; i.hasNext() && j.hasNext();)
					if (!isEqual(testTree, thisTree, i.next(), j.next()))
						return false;
				return true;
			} else
				return false;
		} else
			return false;
	}

	/**
	 * node cannot be false for this implementation it will check the element is
	 * the children of parent
	 */
	public <Node> boolean isAncestor(Tree<Node> tree, Node node, Node child) throws NodeNotFoundException {
		if (tree.contains(child))
			child = tree.parent(child);
		else
			throw new NodeNotFoundException("SuBz Exception @ " + getClass() + "child node not found in the tree");
		if (node != null) {
			if (!node.equals(tree.root())) {
				while (child != null) {
					if (child.equals(node))
						return true;
					else
						child = tree.parent(child);
				}
				return false;
			} else
				return true;
		} else
			return false;
	}

	/**
	 * node cannot be false for this implementation ckeck the parent
	 */
	public <Node> boolean isDescendant(Tree<Node> tree, Node parent, Node node) throws NodeNotFoundException {
		if (tree.contains(parent)) {
			if (node == null)
				return false;
			else
				return isAncestor(tree, parent, node);
		} else
			throw new NodeNotFoundException("parent node not found in the tree");
	}

	/**
	 * it will check parent is common for both nodes
	 * */
	public <Node> Node commonAncestor(Tree<Node> tree, Node node1, Node node2) throws NodeNotFoundException {
		int height1 = 0;
		Node e1 = node1;
		while (e1 != null) {
			height1++;
			e1 = tree.parent(e1);
		}
		int height2 = 0;
		Node e2 = node2;
		while (e2 != null) {
			height2++;
			e2 = tree.parent(e2);
		}
		if (height1 > height2) {
			while (height1 - height2 > 0) {
				node1 = tree.parent(node1);
				height1--;
			}
		} else {
			while (height2 - height1 > 0) {
				node2 = tree.parent(node2);
				height2--;
			}
		}
		while (node1 != null && !node1.equals(node2)) {
			node1 = tree.parent(node1);
			node2 = tree.parent(node2);
		}
		return node1;
	}
}
