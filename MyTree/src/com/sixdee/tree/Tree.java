package com.sixdee.tree;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.redisson.api.RMapCache;

import com.google.gson.Gson;
import com.sixdee.tree.exception.NodeNotFoundException;

/**
 * Null elements are not allowed in the tree.
 * 
 * @author subin.soman
 */
public interface Tree<Node> {

	public boolean add(Node e);

	public boolean add(Node parent, Node child) throws NodeNotFoundException;

	public boolean addAll(Node parent, Collection<? extends Node> c) throws NodeNotFoundException;

	public boolean addAll(Collection<? extends Node> c);

	public Collection<Node> children(Node e) throws NodeNotFoundException;

	public Node commonAncestor(Node node1, Node node2) throws NodeNotFoundException;

	public int depth();

	public Collection<Node> inorderOrderTraversal();

	public Collection<Node> inOrderTraversal();

	public boolean isAncestor(Node node, Node child) throws NodeNotFoundException;

	public boolean isDescendant(Node parent, Node node) throws NodeNotFoundException;

	public Collection<Node> leaves();

	public Collection<Node> levelOrderTraversal();

	public Node parent(Node e) throws NodeNotFoundException;

	public Collection<Node> postOrderTraversal();

	public Collection<Node> preOrderTraversal();

	public Node root();

	public Collection<Node> siblings(Node e) throws NodeNotFoundException;

	public void clear();

	public boolean contains(Object o);

	public boolean containsAll(Collection<?> c);

	public boolean isEmpty();

	public Iterator<Node> iterator();

	public boolean remove(Object o);

	public boolean removeAll(Collection<?> c);

	public boolean retainAll(Collection<?> c);

	public int size();

	public Object[] toArray();

	public <T> T[] toArray(T[] a);

	public <K, V> Collection<Node> getParents(List<Node> list, Node node, RMapCache<K, V> mapCache, K key,Gson gson)
			throws NodeNotFoundException;
}
