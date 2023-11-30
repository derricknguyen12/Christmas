
/**
 * HashTable.java
 * @author Derrick Nguyen
 */
import java.util.ArrayList;

public class HashTable<T> {

	private int numElements;
	private ArrayList<LinkedList<T>> Table;

	/**
	 * Constructor for the HashTable class. Initializes the Table to be sized
	 * according to value passed in as a parameter Inserts size empty Lists into the
	 * table. Sets numElements to 0
	 * 
	 * @param size the table size
	 * @precondition size > 0
	 * @throws IllegalArgumentException when size <= 0
	 */
	public HashTable(int size) throws IllegalArgumentException {
		if (size <= 0) {
			throw new IllegalArgumentException(
					"HashTable(int size): Size must be greater than 0. Cannot initialize object.");
		}
		Table = new ArrayList<LinkedList<T>>();

		for (int i = 0; i < size; i++) {
			Table.add(new LinkedList<T>());
		}
		numElements = 0;
	}

	/**
	 * Constructor for HashTable class Inserts the contents of the given array into
	 * the Table at the appropriate indices
	 * 
	 * @param array an array of elements to insert
	 * @param size  the size of the Table
	 * @precondition size > 0
	 * @throws IllegalArgumentException when size <= 0
	 */
	public HashTable(T[] array, int size) throws IllegalArgumentException {
		if (size <= 0) {
			throw new IllegalArgumentException(
					"HashTable(T[] array, int size): Size must be greater than 0. Cannot initialize object.");
		}
		Table = new ArrayList<LinkedList<T>>();

		for (int i = 0; i < size; i++) {
			Table.add(new LinkedList<T>());
		}
		numElements = 0;

		if (array == null || array.length == 0) {
			return;
		}
		for (int i = 0; i < array.length; i++) {
			add(array[i]);
		}
	}

	/** Accessors */

	/**
	 * returns the hash value in the Table for a given Object
	 * 
	 * @param t the Object
	 * @return the index in the Table
	 */
	private int hash(T t) {
		int code = t.hashCode();
		return Math.abs(code) % Table.size();
	}

	/**
	 * Counts the number of elements at this index
	 * 
	 * @param index the index in the Table
	 * @precondition !(index < 0 || index >= Table.size())
	 * @return the count of elements at this index
	 * @throws IndexOutOfBoundsException when the precondition is violated
	 */
	public int countBucket(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= Table.size()) {
			throw new IndexOutOfBoundsException("countBucket: Index out of bounds. Cannot count bucket.");
		}
		return Table.get(index).getLength();
	}

	/**
	 * Determines total number of elements in the Table
	 * 
	 * @return total number of elements
	 */
	public int getNumElements() {
		return numElements;
	}

	/**
	 * Get the list contain the value in Table
	 * 
	 * @param hash the hash value
	 * @return the value at that bucket as a LinkedList
	 * @throws IndexOutOfBoundsException when hash value is negative or larger than
	 *                                   Table.size()
	 */

	public LinkedList<T> getList(int hash) throws IndexOutOfBoundsException {
		if (hash < 0 || hash > Table.size()) {
			throw new IndexOutOfBoundsException("getValue: Invalid Hash Value");
		}
		int bucket = hash;
		// set variable equal to the index of node
		return Table.get(bucket);
	}

	/**
	 * Get the value contain in Table
	 * 
	 * @param t
	 * @return value
	 * @throws NullPointerException when t, the data passed in is null
	 */
	public T getValue(T t) throws NullPointerException {
		if (t == null) {
			throw new NullPointerException("get: Element is null. Cannot get element.");
		}

		int bucket = hash(t);

		// set variable equal to the index of node
		int indexOfNode = Table.get(bucket).findIndex(t);
		if (indexOfNode >= 0) {

			// position iterator
			Table.get(bucket).positionIterator();

			// advance the iterator to the index of node
			Table.get(bucket).advanceIteratorToIndex(indexOfNode);

			// return the value of iterator
			return Table.get(bucket).getIterator();
		}

		return null;
	}

	/**
	 * Accesses a specified element in the Table
	 * 
	 * @param t the element to locate
	 * @return the bucket number where the element is located or -1 if it is not
	 *         found
	 * @precondition !t==null
	 * @throws NullPointerException when the precondition is violated
	 */
	public int find(T t) throws NullPointerException {
		if (t == null) {
			throw new NullPointerException("find: Element is null. Cannot locate bucket.");
		}
		if (contains(t)) {
			return hash(t);
		}
		return -1;
	}

	/**
	 * Determines whether a specified element is in the Table
	 * 
	 * @param t the element to locate
	 * @return whether the element is in the Table
	 * @precondition !t==null
	 * @throws NullPointerException when the precondition is violated
	 */
	public boolean contains(T t) throws NullPointerException {
		if (t == null) {
			throw new NullPointerException("contains: Element is null. Cannot locate element.");
		}
		if (Table.get(hash(t)).findIndex(t) >= 0) {
			return true;
		}
		return false;
	}

	/** Mutators */

	/**
	 * Inserts a new element in the Table at the end of the chain of the correct
	 * bucket
	 * 
	 * @param t the element to insert
	 * @precondition !t==null
	 * @throws NullPointerException when the precondition is violated
	 */
	public void add(T t) throws NullPointerException {
		if (t == null) {
			throw new NullPointerException("add: Element is null. Cannot add.");
		}

		Table.get(hash(t)).addLast(t);
		numElements++;
	}

	/**
	 * Removes the given element from the Table
	 * 
	 * @param t the element to remove
	 * @precondition !t==null
	 * @return whether t exists and was removed from the Table
	 * @throws NullPointerException when the precondition is violated
	 */
	public boolean delete(T t) throws NullPointerException {
		if (t == null) {
			throw new NullPointerException("delete: Element is null. Cannot delete.");
		}
		// if the element is found
		if (contains(t)) {
			// set variable equal to the index of node
			int indexOfNode = Table.get(hash(t)).findIndex(t);

			// position iterator
			Table.get(hash(t)).positionIterator();

			// advance the iterator to the index of node
			Table.get(hash(t)).advanceIteratorToIndex(indexOfNode);

			// remove the iterator
			Table.get(hash(t)).removeIterator();

			// decrement numElements
			numElements--;

			return true;
		}
		return false;

		/*
		 * Alternate delete:
		 * 
		 * int bucket = hash(elem);
		 * 
		 * while(!Table.get(bucket).offEnd()){
		 * if(Table.get(bucket).getIterator()).equals(elem)){
		 * Table.get(bucket).removeIterator(); numElements--; return true; }
		 * Table.get(bucket).advanceIterator(); } return false;
		 */
	}

	/**
	 * Resets the hash table back to the empty state, as if the one argument
	 * constructor has just been called.
	 */
	public void clear() {
		for (int i = 0; i < Table.size(); i++) {
			// now we are on linked list "i"

			// set linked list length to fixed variable
			int length = Table.get(i).getLength();

			// inner for loop needs to iterate through linked list "i" and remove each node
			for (int j = 0; j < length; j++) {
				Table.get(i).removeFirst();
			}
		}
		numElements = 0;
	}

	/** Additional Methods */

	/**
	 * Computes the load factor
	 * 
	 * @return the load factor
	 */
	public double getLoadFactor() {
		return (double) numElements / Table.size();
	}

	/**
	 * Creates a String of all elements at a given bucket
	 * 
	 * @param bucket the index in the Table
	 * @return a String of elements, separated by spaces with a new line character
	 *         at the end
	 * @precondition !(index < 0 || index >= Table.size())
	 * @throws IndexOutOfBoundsException when bucket is out of bounds
	 */
	public String bucketToString(int bucket) {
		if (bucket < 0 || bucket >= Table.size()) {
			throw new IndexOutOfBoundsException(
					"countBucket: Bucket out of bounds. Cannot invoke toString() on bucket.");
		}
		return Table.get(bucket).toString();
	}

	/**
	 * Creates a String of the bucket number followed by a colon followed by the
	 * first element at each bucket followed by a new line For empty buckets, add
	 * the bucket number followed by a colon followed by empty
	 * 
	 * @return a String of all first elements at each bucket
	 */
	public String rowToString() {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < Table.size(); i++) {
			if (Table.get(i).isEmpty()) {
				result.append("Bucket " + i + ": empty\n");
			} else {
				result.append("Bucket " + i + ": " + Table.get(i).getFirst().toString() + "\n");
			}
		}
		return result.toString();
	}

	/**
	 * Starting at the 0th bucket, and continuing in order until the last
	 * bucket,concatenates all elements at all buckets into one String, with a new
	 * line between buckets and one more new line at the end of the entire String
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < Table.size(); i++) {
			if (!Table.get(i).isEmpty()) {
				result.append(bucketToString(i));
			}
		}
		return result.toString() + "\n";
	}

	/**
	 * Iterates through the HashTable, adding values into an ArrayList, skipping
	 * empty values
	 * 
	 * @return ArrayList with all the elements in the HashTable
	 */
	public ArrayList<T> getArray() {
		ArrayList<T> array = new ArrayList<T>();
		for (int i = 1; i < Table.size(); i++) {
			if (Table.get(i).isEmpty() == true) {
				continue;
			} else {
				Table.get(i).positionIterator();
				while (Table.get(i).offEnd() == false) {
					array.add(Table.get(i).getIterator());
					Table.get(i).advanceIterator();
				}
			}
		}
		return array;
	}
}