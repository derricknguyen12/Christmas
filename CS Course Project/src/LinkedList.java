
/**
 * LinkedList.java
 * @author Derrick Nguyen
 */

import java.util.NoSuchElementException;

public class LinkedList<T> {
	private class Node {
		private T data;
		private Node next;
		private Node prev;

		public Node(T data) {
			this.data = data;
			this.next = null;
			this.prev = null;
		}
	}

	private int length;
	private Node first;
	private Node last;
	private Node iterator;

	/**** CONSTRUCTORS ****/

	/**
	 * Instantiates a new LinkedList with default values
	 * 
	 * @postcondition an empty LinkedList list is created
	 */
	public LinkedList() {
//		first = null;
//		last = null;
//		iterator = null;
//		length = 0;
		first = null;
		last = null;
		iterator = null;
		length = 0;
	}

	/**
	 * Converts the given array into a LinkedList
	 * 
	 * @param array the array of values to insert into this LinkedList
	 * @postcondition a LinkedList is created with all elements from the pass in
	 *                array
	 */
	public LinkedList(T[] array) {
		this();
		if (array == null) {
			return;
		}
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				addLast(array[i]);
			}
		}
	}

	/**
	 * Instantiates a new LinkedList by copying another List
	 * 
	 * @param original the LinkedList to copy
	 * @postcondition a new List object, which is an identical, but separate, copy
	 *                of the LinkedList original
	 */
	public LinkedList(LinkedList<T> original) {
		this();
		if (original == null) { // edge case 1
			return;
			/*
			 * this return has a same effect as calling a default constructor in this case,
			 * which just make a empty list for the original LinkedList, or calling a
			 * default constructor for this original LinkedList.
			 */
		}
		/*
		 * the above if statement will prevent NullPointerException, b/c if we try to
		 * access the length of original LinkedList like how we did below w/o the above
		 * code then we saying null.length -> error
		 */
// the original.length == 0 is comment out 
//		b/c if the original is empty, and Node temp = null; then the loop won't run
//		if (original.length == 0) { // edge case 2: list is instantiated but empty i.e. all fields are default
////			length = 0;
////			first = null;
////			last = null;
////			iterator = null;
//			return;
//		} else { //general case

		Node temp = original.first; // create a temp node that start off at first node in the original LinkedList
		while (temp != null) { // going down the List
			this.addLast(temp.data); // adding and copying down each elements from the original LinkedList to the new
			// List
			temp = temp.next; // move temp node down along the original LinkedList until reach the end
		}
		iterator = null; // in all constructors start out by setting iterator to null
//		}
	}

	/**** ACCESSORS ****/

	/**
	 * Returns the value stored in the first node
	 * 
	 * @precondition length != 0
	 * @return the value stored at node first
	 * @throws NoSuchElementException when the list is empty
	 */
	public T getFirst() throws NoSuchElementException {
		if (length == 0) {
			throw new NoSuchElementException("getFirst: LinkedList is empty");
		}
		return first.data;
	}

	/**
	 * Returns the value stored in the last node
	 * 
	 * @precondition length != 0
	 * @return the value stored in the node last
	 * @throws NoSuchElementException when the list is empty
	 */
	public T getLast() throws NoSuchElementException {
		if (length == 0) {
			throw new NoSuchElementException("getLast: LinkedList is empty");
		}
		return last.data;
	}

	/**
	 * Returns the data stored in the iterator node
	 * 
	 * @precondition when offEnd() is false
	 * @throw NullPointerException when iterator has no data, which it has advanced
	 *        off the end of the LinkedList, or LinkedList is empty
	 */
	public T getIterator() throws NullPointerException {
		if (offEnd()) {
			throw new NullPointerException("getIterator: iterator is null");
		} // edge case
		return iterator.data; // general case
	}

	/**
	 * Returns the current length of the LinkedList
	 * 
	 * @return the length of the LinkedList from 0 to n
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Returns whether the LinkedList is currently empty
	 * 
	 * @return whether the LinkedList is empty
	 */
	public boolean isEmpty() {
		return length == 0;
	}

	/**
	 * Returns whether the iterator is offEnd, i.e. null
	 * 
	 * @return whether the iterator is null
	 */
	public boolean offEnd() {
		// continue week 2
		return iterator == null; // also check length == 0,
	}

	/**** MUTATORS ****/

	/**
	 * Creates a new first element
	 * 
	 * @param data the data to insert at the front of the LinkedList
	 * @postcondition a new first node is created
	 */
	public void addFirst(T data) {
		if (length == 0) { // edge case
			first = last = new Node(data);
		} else {
			Node n = new Node(data);
			n.next = first;
			first.prev = n;
			first = n;
		}
		length++;
	}

	/**
	 * Creates a new last element
	 * 
	 * @param data the data to insert at the end of the LinkedList
	 * @postcondition a new last node is created
	 */
	public void addLast(T data) {
		if (length == 0) { // edge case
			first = last = new Node(data);
		} else {
			Node n = new Node(data);
			last.next = n;
			n.prev = last;
			last = n;
		}
		length++;
	}

	/**
	 * Inserts a new element after the iterator
	 * 
	 * @param data the data to insert
	 * @precondition iterator != null
	 * @throws NullPointerException when iterator has no data, which it has advanced
	 *                              off the end of the LinkedList, or LinkedList is
	 *                              empty
	 */
	public void addIterator(T data) throws NullPointerException {
		if (offEnd()) { // precondition
			throw new NullPointerException("addIterator: iterator is offEnd. Cannot add");
		} else if (iterator == last) {// edge case
			addLast(data); // b/c the iterator is now like add the last node
		} else { // general case
			Node n = new Node(data);
			n.prev = iterator;
			n.next = iterator.next;
			iterator.next.prev = n;
			iterator.next = n;
			length++;
		}
	}

	/**
	 * removes the element at the front of the LinkedList
	 * 
	 * @precondition length != 0
	 * @postcondition a current first node is removed, and the next node assign to
	 *                be the first
	 * @throws NoSuchElementException when a LinkedList is empty
	 */
	public void removeFirst() throws NoSuchElementException {
		if (length == 0) { // precondition
			throw new NoSuchElementException("removeFirst(): Cannot remove from an empty List!");
		} else if (length == 1) { // edge case #1
			first = last = iterator = null;
		} else {
			if (iterator == first) { // edge case #2
				iterator = null;
			}
			// general case:
			first = first.next;
			first.prev = null;
		}
		length--;
	}

	/**
	 * removes the element at the end of the LinkedList
	 * 
	 * @precondition length != 0
	 * @postcondition a current last node is removed, and the previous node assign
	 *                to be the last
	 * @throws NoSuchElementException when a LinkedList is empty
	 */
	public void removeLast() throws NoSuchElementException {
		if (length == 0) { // precondition
			throw new NoSuchElementException("removeLast(): Cannot remove from an empty List!");
		} else if (length == 1) { // edge case 1
			first = last = iterator = null;
		} else {
			if (iterator == last) { // edge case 2
				iterator = null;
			}
			// general case
			last = last.prev;
			last.next = null;
		}
		length--;
	}

	/**
	 * removes the element referenced by the iterator
	 * 
	 * @precondition iterator != null
	 * @postcondition the node after iterator is removed from the LinkedList
	 * @throws NullPointerException when iterator has no data, which it has advanced
	 *                              off the end of the LinkedList, or LinkedList is
	 *                              empty
	 */
	public void removeIterator() throws NullPointerException {
		if (offEnd()) { // precondition
			throw new NullPointerException("removeIterator: iterator is null. Cannot remove");
		} else if (iterator == first) { // edge case 1
			removeFirst(); // it's gonna call the iterator.prev.next = iterator.next;, which is null.next
		} else if (iterator == last) { // edge case 2
			removeLast(); //// it's could go through iterator.prev.next = iterator.next; but then it gonna
							//// call the iterator.next.prev = iterator.prev;, which is null.prev
		} else { // general case
			iterator.prev.next = iterator.next;
			iterator.next.prev = iterator.prev;
			iterator = null;
			length--;
		}
	}

	/**
	 * places the iterator at the first node
	 * 
	 * @postcondition iterator is positioned at the first node
	 */
	public void positionIterator() {
		iterator = first;
	}

	/**
	 * Moves the iterator one node towards the last
	 * 
	 * @precondition when offEnd() is true
	 * @postcondition iterator is moved to the next node in the LinkedList
	 * @throw NullPointerException when iterator has no data, which it has advanced
	 *        off the end of the LinkedList, or LinkedList is empty
	 */
	public void advanceIterator() throws NullPointerException {
		if (offEnd()) { // precondition
			throw new NullPointerException("advanceIterator: iterator is null. Cannot advance.");
		} else {
			iterator = iterator.next; // general case
		}
	}

	/**
	 * Moves the iterator one node towards the first
	 * 
	 * @precondition iterator != null
	 * @postcondition iterator is moved to the previous node in the LinkedList
	 * @throws NullPointerException when iterator has no data, which it has advanced
	 *                              off the end of the LinkedList, or LinkedList is
	 *                              empty
	 */
	public void reverseIterator() throws NullPointerException {
		if (offEnd()) {
			throw new NullPointerException("reverseIterator: iterator is null. Cannot reverse.");
		} else {
			iterator = iterator.prev;
		}
	}

	/**** ADDITIONAL OPERATIONS ****/

	/**
	 * Converts the LinkedList to a String, with each value separated by a blank
	 * line At the end of the String, place a new line character
	 * 
	 * @return the LinkedList as a String
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		Node temp = first;
		while (temp != null) {
			result.append(temp.data + " ");
			temp = temp.next;
		}
		return result.toString() + "\n";
	}

	/**
	 * Determines whether the given Object is another LinkedList, containing the
	 * same data in the same order
	 * 
	 * @param o another Object
	 * @return whether there is equality
	 */
	@SuppressWarnings("unchecked") // good practice to remove warning here
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (!(o instanceof LinkedList)) {
			return false;
		} else {
			LinkedList<T> L = (LinkedList<T>) o;
			/*
			 * o is instance of LinkedList but it was pass in as type Object (polymorphism),
			 * so we have to cast it for it to have access of other operations in LinkedList
			 */

			if (this.length != L.length) {
				return false;
			} else {
				/*
				 * we not using iterator in this method, b/c iterator is used for the people
				 * using our class (user), so they not expect the iterator to be involved
				 * comparing two LinkedList which could change the position of the iterator.
				 */
				Node temp1 = this.first;
				Node temp2 = L.first;
				while (temp1 != null) {
					/*
					 * it doesn't matter if temp1/temp2 is being used in while-loop test, b/c they
					 * both have the same length
					 */
					if (!temp1.data.equals(temp2.data)) {
						return false;
					}
					temp1 = temp1.next;
					temp2 = temp2.next;
				}
				return true;
			}
		}
	}

	/** CHALLENGE METHODS */

	/**
	 * Moves all nodes in the list towards the end of the list the number of times
	 * specified Any node that falls off the end of the list as it moves forward
	 * will be placed the front of the list For example: [1, 2, 3, 4, 5], numMoves =
	 * 2 -> [4, 5, 1, 2 ,3] For example: [1, 2, 3, 4, 5], numMoves = 4 -> [2, 3, 4,
	 * 5, 1] For example: [1, 2, 3, 4, 5], numMoves = 7 -> [4, 5, 1, 2 ,3]
	 * 
	 * @param numMoves the number of times to move each node.
	 * @precondition numMoves >= 0
	 * @postcondition iterator position unchanged (i.e. still referencing the same
	 *                node in the list, regardless of new location of Node)
	 * @throws IllegalArgumentException when numMoves < 0
	 */
	public void revolvingList(int numMoves) throws IllegalArgumentException {
		if (numMoves < 0) {// precondition
			throw new IllegalArgumentException("revolvingList: numMoves must be greater than 0");
		} else {// general case
			if (length != 0) {
				for (int i = 0; i < numMoves; i++) {
					addFirst(last.data);
					if (iterator == last) {
						iterator = first;
					}
					removeLast();
				}
			}
		}
	}

	/**
	 * Splices together two LinkedLists to create a third List which contains
	 * alternating values from this list and the given parameter For example:
	 * [1,2,3] and [4,5,6] -> [1,4,2,5,3,6] For example: [1, 2, 3, 4] and [5, 6] ->
	 * [1, 5, 2, 6, 3, 4] For example: [1, 2] and [3, 4, 5, 6] -> [1, 3, 2, 4, 5, 6]
	 * 
	 * @param list the second LinkedList
	 * @return a new LinkedList, which is the result of interlocking this and list
	 * @postcondition this and list are unchanged
	 */
	public LinkedList<T> interlockLists(LinkedList<T> list) {

		LinkedList<T> L3 = new LinkedList<>();

		if (list == null) { // edge case 1
			list = new LinkedList<>();
		}

		if (this.isEmpty()) { // edge case 2
			L3 = list;
		} else if (list.isEmpty()) { // edge case 3
			L3 = this;
		} else { // general case
			int longerList = Math.max(this.length, list.length);

			Node temp = this.first;
			Node temp2 = list.first;

			for (int i = 0; i < longerList; i++) {
				if (temp != null) {
					L3.addLast(temp.data);
					temp = temp.next;
				}
				if (temp2 != null) {
					L3.addLast(temp2.data);
					temp2 = temp2.next;
				}
			}
		}
		return L3;
	}

	/** HASH TABLE METHODS */

	/**
	 * Determines at which index the iterator is located Indexed from 0 to length -
	 * 1
	 * 
	 * @return the index number of the iterator
	 * @precondition !offEnd()
	 * @throws NullPointerException when precondition is violated
	 */
	public int getIteratorIndex() throws NullPointerException {
		if (offEnd()) {
			throw new NullPointerException("getIteratorIndex: Iterator is null. Cannot return index.");
		}

		Node temp = first;
		int index = 0;

		while (temp != iterator) {
			temp = temp.next;
			index++;
		}
		return index;
	}

	/**
	 * Searches the LinkedList for a given element's index
	 * 
	 * @param data the data whose index to locate
	 * @return the index of the data or -1 if the data is not contained in the
	 *         LinkedList
	 */
	public int findIndex(T data) { // passed in instance WordID (String, 0)
		Node temp = first;

		for (int i = 0; i < length; i++) {
			if (temp.data.equals(data)) { // (String, 0)
				return i;
			}
			temp = temp.next;
		}
		return -1;
	}

	/**
	 * Advances the iterator to location within the LinkedList specified by the
	 * given index
	 * 
	 * @param index the index at which to place the iterator.
	 * @precondition !offEnd()
	 * @throws NullPointerException when the precondition is violated
	 */
	public void advanceIteratorToIndex(int index) throws NullPointerException {
		if (offEnd()) {
			throw new NullPointerException("advanceIteratorToIndex: Iterator is null. Cannot advance to index.");
		}
		for (int i = 0; i < index; i++) {
			advanceIterator();
		}
	}
}