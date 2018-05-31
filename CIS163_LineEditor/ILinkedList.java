/**
 * The ILinkedList interface defines operations for a linked list data
 * structure.
 * 
 * @author Sean Crowley
 * @version November 2014
 */
public interface ILinkedList {

	/**
	 * Adds the specified element to the end of the list.
	 * 
	 * @param element
	 *            the element to add to the list
	 * 
	 * @throws NullPointerException
	 *             if the specified element is null
	 */
	void add(String element);

	/**
	 * Inserts the given element at the specified position in the list.
	 * 
	 * @param index
	 *            index at which the specified element is to be inserted
	 * @param element
	 *            element to be inserted
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of range (index < 0 || index >
	 *             size())
	 * @throws NullPointerException
	 *             if the specified element is null
	 */
	void add(int index, String element);

	/**
	 * Removes the element at the specified position in this list.
	 * 
	 * @param index
	 *            the index of the element to be removed
	 * 
	 * @return the element previously at the specified position
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of range (index < 0 || index >=
	 *             size())
	 */
	String remove(int index);

	/**
	 * Returns the element at the specified position in this list.
	 * Return null if the index is out of range (index < 0 || index >=
	 * size())
	 * 
	 * @param index
	 *            index of the element to return
	 * 
	 * @return the element at the specified position in this list
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of range (index < 0 || index >=
	 *             size())
	 */
	String get(int index);

	/**
	 * Returns true if this list contains no elements.
	 * 
	 * @return true if this list contains no elements and false
	 *         otherwise
	 */
	boolean isEmpty();

	/**
	 * Returns the number of elements in this list.
	 * 
	 * @return the number of elements in this list
	 */
	int size();

	/**
	 * Removes all of the elements from this list. The list will be
	 * empty after this call returns.
	 */
	void clear();
}
