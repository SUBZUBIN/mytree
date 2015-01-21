
package com.sixdee.subz;

import java.util.Iterator;

/**
 * @author SuBiN SoMaN
 * 
 */
public class LevelTest  {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws NodeNotFoundException {
		LevelTree<String> tree = new MyArrayListTree<String>();
		tree.add("Level-1");
		tree.add("Level-1", "Level-11");
		tree.add("Level-1", "Level-12");
		tree.add("Level-1", "Level-13");
		tree.add("Level-12", "Level-121");
		tree.add("Level-12", "Level-122");
		tree.add("Level-122", "Level-1221");
		tree.add("Level-13", "Level-131");
		tree.add("Level-13", "Level-132");
		tree.add("Level-13", "Level-133");
		tree.add("Level-11", "Level-111");
		tree.add("Level-11", "Level-112");
		
		Iterator<String> iterator = tree.iterator();
		System.out.println("Tree set data: ");

		// Displaying the Tree set data
		/*
		 * while (iterator.hasNext()) { System.out.print(iterator.next() + " ");
		 * }
		 */
		System.out.println(tree.levelOrderTraversal());
		/*System.out.println(tree.preOrderTraversal());
		System.out.println(tree.postOrderTraversal());
		System.out.println(tree.inorderOrderTraversal());
		LinkedList<String> list = new LinkedList<String>();
		list.add("Level-13");
		System.out.println(tree.removeAll(list));
		System.out.println(tree.levelOrderTraversal());
		System.out.println("a" + tree.parent("Level-112"));*/
		System.out.println("subin" + tree.parent("Level-1"));
		System.out.println(tree.isAncestor("Level-1", "Level-1221"));
		System.out.println("siblings"+tree.siblings("Level-112"));
		System.out.println(tree.children("Level-12"));
		System.out.println("depth:  "+tree.depth());
		System.out.println(tree.leaves());
		for(int i=0;i<tree.depth();i++){
			if(i==0){
				
			}
		}
	}
}