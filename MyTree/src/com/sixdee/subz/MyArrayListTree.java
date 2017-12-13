package com.sixdee.subz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MyArrayListTree<Node> implements LevelTree<Node>, Cloneable {
	private ArrayList<Node>					nodeList		= new ArrayList<Node>();
	private ArrayList<Integer>				parentList		= new ArrayList<Integer>();
	private ArrayList<ArrayList<Integer>>	childrenList	= new ArrayList<ArrayList<Integer>>();
	private int								size			= 0;
	private int								depth			= 0;
	private int								rootIndex		= -1;

	/**
	 * If tree is empty, it adds a root. In case tree is not empty, it will
	 * attempt to add parameter as a child of the root
	 * 
	 * @author subin.soman
	 */
	@Override
	public boolean add(Node e) {
		try {
			if (isEmpty())
				return add(null, e);
			else
				return add(nodeList.get(rootIndex), e);
		} catch (NodeNotFoundException ex) {
			throw new IllegalArgumentException(ex);

		}
	}

	@Override
	public boolean add(Node parent, Node child) throws NodeNotFoundException {
		checkNode(child);
		if (isRootElementBeingAdded(parent, child))
			return true;
		int parentIndex = nodeList.indexOf(parent);
		if (parentIndex > -1) {
			int childIndex = nodeList.indexOf(child);
			if (childIndex == -1) {
				nodeList.add(child);
				parentList.add(parentIndex);
				childrenList.get(parentIndex).add(nodeList.size() - 1);
				childrenList.add(new ArrayList<Integer>());
				size++;
				int currentDepth = 2;
				while (parentIndex > 0) {
					currentDepth++;
					parentIndex = parentList.get(parentIndex);
				}
				depth = Math.max(currentDepth, depth);
				return true;
			} else {
				nodeList.set(childIndex, child);
				return false;
			}
		} else
			throw new NodeNotFoundException("SuBz Exception @ " + getClass() + "No such parent node found ");
	}

	private boolean isRootElementBeingAdded(Node parent, Node child) {
		if (parent == null) {
			if (isEmpty()) {
				addRoot(child);
				return true;
			} else
				throw new IllegalArgumentException("parent cannot be null except only for root element. Here already has a root.");
		} else
			return false;
	}

	private void addRoot(Node child) {
		nodeList.add(child);
		rootIndex = nodeList.size() - 1;
		parentList.add(-1);
		childrenList.add(new ArrayList<Integer>());
		size++;
		depth++;
	}

	/**
	 * This method lets the sub-classes define the position at which new child
	 * may be added
	 * 
	 * @param children
	 * @param newChild
	 * @return index at which new child will be added
	 */
	protected int getChildAddPosition(List<Node> children, Node newChild) {
		return children.size();
	}

	@Override
	public boolean addAll(Collection<? extends Node> c) {
		boolean retVal = false;
		for (Iterator<? extends Node> iterator = c.iterator(); iterator.hasNext();)
			retVal |= add(iterator.next());
		return retVal;
	}

	public boolean addAll(Node parent, Collection<? extends Node> c) {
		try {
			for (Iterator<? extends Node> iterator = c.iterator(); iterator.hasNext();)
				add(parent, iterator.next());
			return true;
		} catch (NodeNotFoundException ex) {
			return false;
		}
	}

	@Override
	public List<Node> children(Node e) throws NodeNotFoundException {
		checkNode(e);
		int index = nodeList.indexOf(e);
		if (index > -1) {
			ArrayList<Integer> childrenIndexList = childrenList.get(index);
			ArrayList<Node> children = new ArrayList<Node>(childrenIndexList.size());
			for (Integer i : childrenIndexList) {
				children.add(nodeList.get(i));
			}
			return children;
		} else
			throw new NodeNotFoundException("SuBz Exception @ " + getClass() + "No node was found for object");
	}

	@Override
	public void clear() {
		nodeList.clear();
		parentList.clear();
		childrenList.clear();
		size = 0;
		depth = 0;
		rootIndex = -1;
	}

	// overided clonable interface
	@SuppressWarnings("unchecked")
	@Override
	public Object clone() {
		MyArrayListTree<Node> v = null;
		try {
			v = (MyArrayListTree<Node>) super.clone();
			v.nodeList = (ArrayList<Node>) nodeList.clone();
			v.parentList = (ArrayList<Integer>) parentList.clone();
			v.childrenList = new ArrayList<ArrayList<Integer>>();
			v.size = this.size;
			v.depth = this.depth;
			for (int i = 0; i < childrenList.size(); i++)
				v.childrenList.add((ArrayList<Integer>) childrenList.get(i).clone());
		} catch (CloneNotSupportedException e) {
			// This should't happen because cloneable
		}
		return v;
	}

	@Override
	public Node commonAncestor(Node node1, Node node2) throws NodeNotFoundException {
		int height1 = 0;
		Node e1 = node1;
		while (e1 != null) {
			height1++;
			e1 = parent(e1);
		}
		int height2 = 0;
		Node e2 = node2;
		while (e2 != null) {
			height2++;
			e2 = parent(e2);
		}
		if (height1 > height2) {
			while (height1 - height2 > 0) {
				node1 = parent(node1);
				height1--;
			}
		} else {
			while (height2 - height1 > 0) {
				node2 = parent(node2);
				height2--;
			}
		}
		while (node1 != null && !node1.equals(node2)) {
			node1 = parent(node1);
			node2 = parent(node2);
		}
		return node1;
	}

	@Override
	public boolean contains(Object o) {
		if (o == null)
			return false;
		else
			return nodeList.indexOf(o) > -1;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return nodeList.containsAll(c);
	}

	@Override
	public int depth() {
		return depth;
	}

	@Override
	public List<Node> inorderOrderTraversal() {
		return inorderOrderTraversal(0, new ArrayList<Node>());
	}

	@Override
	public List<Node> inOrderTraversal() {
		if (isEmpty())
			return new ArrayList<Node>();
		else
			return inorderOrderTraversal(rootIndex, new ArrayList<Node>());
	}

	@Override
	public boolean isAncestor(Node node, Node child) throws NodeNotFoundException {
		checkNode(child);
		return new LevelTreeHelper().isAncestor(this, node, child);
	}

	@Override
	public boolean isDescendant(Node parent, Node node) throws NodeNotFoundException {
		checkNode(parent);
		return new LevelTreeHelper().isDescendant(this, parent, node);
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Iterator returns nodes as expected from inOrderTraversal
	 * 
	 */
	@Override
	public Iterator<Node> iterator() {
		return getCurrentList().iterator();
	}

	@Override
	public List<Node> leaves() {
		if (isEmpty())
			return new ArrayList<Node>();
		else
			return leaves(rootIndex, new ArrayList<Node>());
	}

	private List<Node> leaves(int nodeIndex, ArrayList<Node> list) {
		ArrayList<Integer> children = childrenList.get(nodeIndex);
		if (children.size() > 0) {
			int i = 0;
			for (; i < (int) Math.ceil((double) children.size() / 2); i++)
				leaves(children.get(i), list);
			if (childrenList.get(nodeIndex).isEmpty())
				list.add(nodeList.get(nodeIndex));
			for (; i < children.size(); i++)
				leaves(children.get(i), list);
		} else if (childrenList.get(nodeIndex).isEmpty())
			list.add(nodeList.get(nodeIndex));

		return list;
	}

	@Override
	public List<Node> levelOrderTraversal() {
		if (isEmpty())
			return new ArrayList<Node>();
		else {
			LinkedList<Integer> queue = new LinkedList<Integer>();
			queue.add(0);

			return levelOrderTraversal(new ArrayList<Node>(), queue);

		}
	}

	@Override
	public Node parent(Node e) throws NodeNotFoundException {
		checkNode(e);
		int index = nodeList.indexOf(e);
		if (index == 0)
			return null;
		else if (index > 0)
			return nodeList.get(parentList.get(index));
		else
			throw new NodeNotFoundException("No node was found for object");
	}

	@Override
	public List<Node> postOrderTraversal() {
		if (isEmpty())
			return new ArrayList<Node>();
		else
			return postOrderTraversal(rootIndex, new ArrayList<Node>());
	}

	@Override
	public List<Node> preOrderTraversal() {
		if (isEmpty())
			return new ArrayList<Node>();
		else
			return preOrderTraversal(rootIndex, new ArrayList<Node>());
	}

	/**
	 * Removes the sub-tree rooted at the node passed
	 * 
	 */
	@Override
	public boolean remove(Object o) {
		checkNode(o);
		int i = nodeList.indexOf(o);
		if (i > -1) {
			boolean wasRemoved;
			if (i != rootIndex) {
				wasRemoved = remove(i);
				depth = recalculateDepth(rootIndex, 0);
			} else {
				wasRemoved = remove(i);
				depth = 0;
			}
			return wasRemoved;
		} else
			return false;
	}

	/**
	 * Removes the sub-tree rooted at the nodes in the collection passed
	 * 
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		boolean retVal = false;
		for (Iterator<?> iterator = c.iterator(); iterator.hasNext();)
			retVal |= remove(iterator.next());
		return retVal;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException("Tree interface doesn't support retainAll");
	}

	@Override
	public Node root() {
		if (isEmpty())
			return null;
		else
			return nodeList.get(rootIndex);
	}

	@Override
	public List<Node> siblings(Node e) throws NodeNotFoundException {
		checkNode(e);
		Node parent = parent(e);
		if (parent != null) {
			List<Node> children = children(parent);
			children.remove(e);
			return children;
		} else
			return new ArrayList<Node>();
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Object[] toArray() {
		return getCurrentList().toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return getCurrentList().toArray(a);
	}

	private void checkNode(Object child) {
		if (child == null)
			throw new IllegalArgumentException("null nodes are not allowed");
	}

	private List<Node> getCurrentList() {
		return inOrderTraversal();
	}

	private List<Node> inorderOrderTraversal(int nodeIndex, ArrayList<Node> list) {
		ArrayList<Integer> children = childrenList.get(nodeIndex);
		if (children.size() > 0) {
			int i = 0;
			for (; i < (int) Math.ceil((double) children.size() / 2); i++)
				inorderOrderTraversal(children.get(i), list);
			list.add(nodeList.get(nodeIndex));
			for (; i < children.size(); i++)
				inorderOrderTraversal(children.get(i), list);
		} else
			list.add(nodeList.get(nodeIndex));
		return list;
	}

	private List<Node> levelOrderTraversal(ArrayList<Node> list, LinkedList<Integer> queue) {
		while (!queue.isEmpty()) {
			list.add(nodeList.get(queue.getFirst()));
			ArrayList<Integer> children = childrenList.get(queue.getFirst());
			for (int i = 0; i < children.size(); i++) {
				queue.add(children.get(i));
			}
			queue.remove();
		}
		return list;
	}

	private List<Node> postOrderTraversal(int nodeIndex, ArrayList<Node> list) {
		ArrayList<Integer> children = childrenList.get(nodeIndex);
		for (int i = 0; i < children.size(); i++)
			postOrderTraversal(children.get(i), list);
		if (nodeList.get(nodeIndex) != null)
			list.add(nodeList.get(nodeIndex));
		return list;
	}

	private List<Node> preOrderTraversal(int nodeIndex, ArrayList<Node> list) {
		if (nodeList.get(nodeIndex) != null)
			list.add(nodeList.get(nodeIndex));
		ArrayList<Integer> children = childrenList.get(nodeIndex);
		for (int i = 0; i < children.size(); i++)
			preOrderTraversal(children.get(i), list);
		return list;
	}

	private boolean remove(int index) {
		if (index > -1) {
			if (index == rootIndex) {
				rootIndex = -1;
				size = 0;
				nodeList.clear();
				parentList.clear();
				childrenList.clear();
				return true;
			} else {
				Integer parentIndex = parentList.set(index, -1);
				if (parentIndex > -1)// if node is not root
					childrenList.get(parentIndex).remove(Integer.valueOf(index));
				nodeList.set(index, null);
				size--;
				ArrayList<Integer> children = childrenList.get(index);
				for (int j = 0; j < children.size();)
					remove(children.get(0).intValue());
				childrenList.get(index).clear();
				return true;
			}
		} else
			return false;
	}

	private int recalculateDepth(int index, int depth) {
		int childDepth = depth + 1;
		if (childrenList.get(index).isEmpty())
			return childDepth;
		else
			for (Integer i : childrenList.get(index))
				depth = Math.max(depth, recalculateDepth(i, childDepth));
		return depth;
	}

	@Override
	public String toString() {
		return getCurrentList().toString();
	}

	@Override
	public int hashCode() {
		return getCurrentList().hashCode();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof MyArrayListTree) {
			try {
				return new LevelTreeHelper().isEqual((MyArrayListTree<Node>) o, this, ((MyArrayListTree<Node>) o).root(), root());
			} catch (NodeNotFoundException e) {
				e.printStackTrace();
				return false;
			}
		} else
			return false;
	}
}