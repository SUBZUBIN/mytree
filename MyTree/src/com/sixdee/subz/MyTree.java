package com.sixdee.subz;

import java.util.List;

/**
 * 
 * This tree allows addition of at most n-children to any parent first elemt to
 * left then right.
 * @author subin.soman
 */
public class MyTree<Node> extends MyArrayListTree<Node> {
	private int	n;

	public MyTree(int k) {
		this.n = n;
	}

	@Override
	public boolean add(Node parent, Node child) throws NodeNotFoundException {
		if (parent == null) // if parent is null
			return super.add(parent, child);// add root
		else if (children(parent).size() < n)
			return super.add(parent, child);
		else
			throw new IndexOutOfBoundsException("Cannot add more than " + n + " children to a parent");
	}

	/**
	 * @param parent
	 *            the parent node
	 * @param index
	 *            index of child requested
	 * @return child present at index among the children of e, if the index is
	 *         less than number of children of e, null otherwise
	 * @throws NodeNotFoundException
	 */
	public Node child(Node parent, int index) throws NodeNotFoundException {
		List<Node> children;
		if (index > n)
			throw new IndexOutOfBoundsException(index + " cannot be more than " + n);
		else if (index > (children = children(parent)).size())
			return null;
		else
			return children.get(index);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object clone() {
		MyTree<Node> v = (MyTree<Node>) super.clone();
		v.n = n;
		return v;
	}
}