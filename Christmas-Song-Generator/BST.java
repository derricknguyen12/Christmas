
/**
 * BST.java
 * @author Derrick Nguyen
 */

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class BST<T extends Comparable<T>> {
	private class Node {
		private T data;
		private Node left;
		private Node right;

		public Node(T data) {
			this.data = data;
			left = null;
			right = null;
		}
	}

	private Node root;

	/*** CONSTRUCTORS ***/

	/**
	 * Default constructor for BST sets root to null
	 */
	public BST() {
		root = null;
	}

	/**
	 * Copy constructor for BST
	 * 
	 * @param bst the BST to make a copy of
	 */
	public BST(BST<T> bst) {
		this();
		if (bst == null) {
			return;
		} else {
			copyHelper(bst.root);
		}
	}

	/**
	 * Helper method for copy constructor
	 * 
	 * @param node the node containing data to copy
	 */
	private void copyHelper(Node node) { // implement the tree traversal
		if (node != null) {
			insert(node.data);
			copyHelper(node.left);
			copyHelper(node.right);
		}
	}

	/**
	 * Creates a BST of minimal height from an array of values
	 * 
	 * @param array the list of values to insert
	 * @precondition array must be sorted in ascending order
	 * @throws IllegalArgumentException when the array is unsorted
	 */
	public BST(T[] array) throws IllegalArgumentException {
		this();
		if (array == null) {
			return;
		}
		if (!isSorted(array)) {
			throw new IllegalArgumentException("BST(T[] array): Array is unsorted. Cannot copy array.");
		}
		root = arrayHelper(0, array.length - 1, array);
	}

	/**
	 * Private helper method for array constructor to check for a sorted array
	 * 
	 * @param array the array to check
	 * @return whether the array is sorted
	 */
	private boolean isSorted(T[] array) { // how to do recursive??
		if (array == null) {
			return false;
		} else {
			for (int i = 0; i < array.length - 1; i++) {
				if (array[i].compareTo(array[i + 1]) > 0) {
					return false;
				}
			}
			return true;
		}
	}

	/**
	 * Recursive helper for the array constructor
	 * 
	 * @param begin beginning array index
	 * @param end   ending array index
	 * @param array array to search
	 * @return the newly created Node
	 */
	private Node arrayHelper(int begin, int end, T[] array) {
		if (begin > end) {
			return null;
		} else {
			int mid = (begin + end) / 2;
			Node node = new Node(array[mid]);
			node.left = arrayHelper(begin, mid - 1, array);
			node.right = arrayHelper(mid + 1, end, array);
			return node;
		}
	}

	/*** ACCESSORS ***/

	/**
	 * Returns the data stored in the root
	 * 
	 * @precondition !isEmpty()
	 * @return the data stored in the root
	 * @throws NoSuchElementException when precondition is violated
	 */
	public T getRoot() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("getRoot: Tree is empty. Cannot getRoot.");
		}
		return root.data;
	}

	/**
	 * Determines whether the tree is empty
	 * 
	 * @return whether the tree is empty
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Returns the current size of the tree (number of nodes)
	 * 
	 * @return the size of the tree
	 */
	public int getSize() {
		return getSize(root);
	}

	/**
	 * Helper method for the getSize method
	 * 
	 * @param node the current node to count
	 * @return the size of the tree
	 */
	private int getSize(Node node) {
		if (node == null) {
			return 0;
		} else {
			// keep moving node to left and right, until it reaches null
			// 1: one node
			return 1 + Integer.sum(getSize(node.left), getSize(node.right));
		}
	}

	/**
	 * Returns the height of tree by counting edges.
	 * 
	 * @return the height of the tree
	 */
	public int getHeight() {
		return getHeight(root);
	}

	/**
	 * Helper method for getHeight method
	 * 
	 * @param node the current node whose height to count
	 * @return the height of the tree
	 */
	private int getHeight(Node node) {
		if (node == null) {
			return -1;
		} else {
			// keep moving node to left and right, until it reaches null
			// 1: one node
			return 1 + Math.max(getHeight(node.left), getHeight(node.right));
		}
	}

	/**
	 * Returns the smallest value in the tree
	 * 
	 * @precondition !isEmpty()
	 * @return the smallest value in the tree
	 * @throws NoSuchElementException when the precondition is violated
	 */
	public T findMin() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("findMin: Tree is empty. Cannot findMin.");
		}
		return findMin(root);
	}

	/**
	 * Helper method to findMin method
	 * 
	 * @param node the current node to check if it is the smallest
	 * @return the smallest value in the tree
	 */
	private T findMin(Node node) {
		if (node.left == null) { // base case
			return node.data;
		} else { // non-trivial case
			return findMin(node.left);
		}
	}

	/**
	 * Returns the largest value in the tree
	 * 
	 * @precondition !isEmpty()
	 * @return the largest value in the tree
	 * @throws NoSuchElementException when the precondition is violated
	 */
	public T findMax() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("findMax: Tree is empty. Cannot findMax.");
		}
		return findMax(root);
	}

	/**
	 * Helper method to findMax method
	 * 
	 * @param node the current node to check if it is the largest
	 * @return the largest value in the tree
	 */
	private T findMax(Node node) {
		if (node.right == null) { // base case
			return node.data;
		} else { // non-trivial case
			return findMax(node.right);
		}
	}

	/*** MUTATORS ***/

	/**
	 * Inserts a new node in the tree
	 * 
	 * @param data the data to insert
	 */
	public void insert(T data) {
		if (isEmpty()) {
			root = new Node(data);
		} else {
			insert(data, root);
		}
	}

	/**
	 * Helper method to insert Inserts a new value in the tree
	 * 
	 * @param data the data to insert
	 * @param node the current node in the search for the correct location in which
	 *             to insert
	 */
	private void insert(T data, Node node) {
		if (data.compareTo(node.data) != 0) {
			if (data.compareTo(node.data) < 0) {
				if (node.left == null) { // base case
					node.left = new Node(data);
				} else { // non-trivial case
					insert(data, node.left);
				}
			} else {
				if (node.right == null) { // base case
					node.right = new Node(data);
				} else { // non-trivial case
					insert(data, node.right);
				}
			}
		}
	}

	/**
	 * Removes a value from the BST
	 * 
	 * @param data the value to remove
	 */
	public void remove(T data) {
		root = remove(data, root);
		// root will reference to the root's memory address that could change
		// after call remove(data, root)
	}

	/**
	 * Helper method to the remove method
	 * 
	 * @param data the data to remove
	 * @param node the current node
	 * @return an updated reference variable
	 */
	private Node remove(T data, Node node) {
		// search and reset connection:
		if (node == null) { // base case
			return node; // if the node is not in the tree -> return null;
		} else if (data.compareTo(node.data) < 0) { // non-trivial case
			node.left = remove(data, node.left);
		} else if (data.compareTo(node.data) > 0) { // non-trivial case
			node.right = remove(data, node.right);

		} else {
			// found and remove:
			if (node.left == null && node.right == null) { // leaf node
				node = null;
			} else if (node.right == null) {
				node = node.left;
			} else if (node.left == null) {
				node = node.right;
			} else { // if the node has two child nodes
				node.data = findMin(node.right);
				// b/c the minimum value on the right is smaller than all the node's right
				// subtree values,
				// but bigger than all the left subtree values
				node.right = remove(node.data, node.right); // remove the duplicate values that we just copy in
			}
		}
		return node;
		// return the memory address back to the first two else if, where we reset the
		// connection of right or left subtree if there's any change in it
	}
	// more info: https://youtu.be/gcULXE7ViZw

	/*** ADDITIONAL OPERATIONS ***/

	/**
	 * Get the value contain in BST
	 * 
	 * @param data
	 * @return value
	 * @throws NullPointerException when the data passed in is null
	 */
	public T get(T data) {
		if (isEmpty()) {
			return null;
		} else {
			return get(data, root);
		}
	}

	/**
	 * Helper method for the search method
	 * 
	 * @param data the data to search for
	 * @param node the current node to check
	 * @return the data stored in the tree
	 */
	private T get(T data, Node node) {
		if (data.equals(node.data)) { // base case
			return node.data;
		} else if (data.compareTo(node.data) < 0) { // non-trivial case: data is smaller
			if (node.left != null) {
				return get(data, node.left);
			}
		} else { // non-trivial case: data is bigger
			if (node.right != null) {
				return get(data, node.right);
			}
		}
		return data;
	}

	/**
	 * Searches for a specified value in the tree
	 * 
	 * @param data the value to search for
	 * @return whether the value is stored in the tree
	 */
	public boolean search(T data) {
		if (isEmpty()) {
			return false;
		} else {
			return search(data, root);
		}
	}

	/**
	 * Helper method for the search method
	 * 
	 * @param data the data to search for
	 * @param node the current node to check
	 * @return whether the data is stored in the tree
	 */
	private boolean search(T data, Node node) {
		if (data.equals(node.data)) { // base case
			return true;
		} else if (data.compareTo(node.data) < 0) { // non-trivial case: data is smaller
			if (node.left == null) {
				return false;
			} else {
				return search(data, node.left);
			}
		} else { // non-trivial case: data is bigger
			if (node.right == null) {
				return false;
			} else {
				return search(data, node.right);
			}
		}
	}

	/**
	 * Returns a String containing the data in post order
	 * 
	 * @return a String of data in post order
	 */
	public String preOrderString() {
		StringBuilder preOrder = new StringBuilder();
		preOrderString(root, preOrder);
		return preOrder.toString() + "\n";
	}

	/**
	 * Helper method to preOrderString Inserts the data in pre order into a String
	 * 
	 * @param node     the current Node
	 * @param preOrder a String containing the data
	 */
	private void preOrderString(Node node, StringBuilder preOrder) { // Print - Left - Right
		if (node == null) { // base case
			return;
		} else { // non-trivial case
			preOrder.append(node.data + " ");
			preOrderString(node.left, preOrder);
			preOrderString(node.right, preOrder);
		}
	}

	/**
	 * Returns a String containing the data in order
	 * 
	 * @return a String of data in order
	 */
	public String inOrderString() {
		StringBuilder inOrder = new StringBuilder();
		inOrderString(root, inOrder);
		return inOrder.toString() + "\n";
	}

	/**
	 * Helper method to inOrderString Inserts the data in order into a String
	 * 
	 * @param node    the current Node
	 * @param inOrder a String containing the data
	 */
	private void inOrderString(Node node, StringBuilder inOrder) { // Left - Print - Right
		if (node == null) { // base case
			return;
		} else { // non-trivial case
			inOrderString(node.left, inOrder);
			inOrder.append(node.data + " ");
			inOrderString(node.right, inOrder);
		}
	}

	/**
	 * Returns a String containing the data in post order
	 * 
	 * @return a String of data in post order
	 */
	public String postOrderString() {
		StringBuilder postOrder = new StringBuilder();
		postOrderString(root, postOrder);
		return postOrder.toString() + "\n";
	}

	/**
	 * Helper method to postOrderString Inserts the data in post order into a String
	 * 
	 * @param node      the current Node
	 * @param postOrder a String containing the data
	 */
	private void postOrderString(Node node, StringBuilder postOrder) { // Left - Right - Print
		if (node == null) { // base case
			return;
		} else { // non-trivial case
			postOrderString(node.left, postOrder);
			postOrderString(node.right, postOrder);
			postOrder.append(node.data + " ");
		}
	}

	/**
	 * Returns an ArrayList containing the data in order
	 * 
	 * @return an ArrayList of data in order
	 */
	public ArrayList<T> inOrderObject() {
		ArrayList<T> inOrderObj = new ArrayList<T>();
		inOrderObject(root, inOrderObj);
		return inOrderObj;
	}

	/**
	 * Helper method to inOrderObject Inserts the data in order into a ArrayList
	 * 
	 * @param node       the current Node
	 * @param inOrderObj a ArrayList containing the data
	 */
	private void inOrderObject(Node node, ArrayList<T> inOrderObj) { // Left - Add - Right
		if (node == null) { // base case
			return;
		} else { // non-trivial case
			inOrderObject(node.left, inOrderObj);
			inOrderObj.add(node.data);
			inOrderObject(node.right, inOrderObj);
		}
	}
}