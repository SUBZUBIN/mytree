package com.sixdee.subz;

import java.util.Collection;

/**
 * Null elements are not allowed in the tree.
 * @author SuBiN SoMaN
 */
public interface LevelTree<Node> extends Collection<Node> {

	public boolean add(Node parent, Node child) throws NodeNotFoundException;

	public boolean addAll(Node parent, Collection<? extends Node> c) throws NodeNotFoundException;

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
}
