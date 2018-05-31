/***********************************************************
 * The MyLinkedList class implements a simple linked list.
 * 
 * @author Sean Crowley
 * @version November 2014
 ***********************************************************/
public class MyLinkedList implements ILinkedList {

	private Node front;
	private Node rear;
	private int size;

	/***********************************************************
	 * Initializes a newly constructed MyLinkedList object.
	 ***********************************************************/
	public MyLinkedList() {
		front = null;
		rear = null;
		size = 0;
	}

	/***********************************************************
	 * Adds the specified element to the end of the list.
	 * 
	 * @param element
	 *            the element to add to the list
	 * @throws NullPointerException
	 *             if the specified element is null
	 ***********************************************************/
	@Override
	public void add(String element) {
		if (element == null) {
			throw new NullPointerException();
		} else if (size == 0) {
			rear = front = new Node(element);
			size++;
		} else {
			rear.setNext(new Node(element));
			rear = rear.getNext();
			size++;
		}
	}

	/***********************************************************
	 * Inserts the given element at the specified position in the list.
	 * 
	 * @param index
	 *            index at which the specified element is to be inserted
	 * @param element
	 *            element to be inserted
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of range (index < 0 || index >
	 *             size())
	 * @throws NullPointerException
	 *             if the specified element is null
	 ***********************************************************/
	@Override
	public void add(int index, String element) {
		if (index < 1 || index > size + 1) {
			throw new IndexOutOfBoundsException();
		} else if (element == null) {
			throw new NullPointerException();
		} else if (index == 1) {
			Node temp = front;
			front = new Node(element);
			front.setNext(temp);
			size++;
		} else if (index == size + 1) {
			rear.setNext(new Node(element));
			rear = rear.getNext();
			size++;
		} else {
			Node temp = front;

			while (index > 2) {
				temp = temp.getNext();
				index--;
			}

			Node temp2 = temp.getNext();
			temp.setNext(new Node(element));
			temp.getNext().setNext(temp2);
			size++;
		}
	}

	/***********************************************************
	 * Removes the element at the specified position in this list.
	 * 
	 * @param index
	 *            the index of the element to be removed
	 * @return the element previously at the specified position
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of range (index < 0 || index >=
	 *             size())
	 ***********************************************************/
	@Override
	public String remove(int index) {
		Node returnNode = null;
		if (index < 1 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		if (index == 1) {
			returnNode = front;
			front = front.getNext();
			size--;

			return returnNode.getData();
		} else if (index == size) {
			Node temp = front;

			while (temp.getNext().getNext() != null) {
				temp = temp.getNext();
			}

			returnNode = temp.getNext();
			temp.setNext(null);
			rear = temp;
			size--;

			return returnNode.getData();
		} else {
			Node temp = front;

			while (index > 2) {
				temp = temp.getNext();
				index--;
			}

			returnNode = temp.getNext();
			temp.setNext(temp.getNext().getNext());
			size--;

			return returnNode.getData();
		}
	}

	/***********************************************************
	 * Returns the element at the specified position in this list.
	 * Return null if the index is out of range (index < 0 || index >=
	 * size())
	 * 
	 * @param index
	 *            index of the element to return
	 * @return the element at the specified position in this list
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of range (index < 0 || index >=
	 *             size())
	 ***********************************************************/
	@Override
	public String get(int index) {
		if (index < 1 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		if (index == 1) {
			return front.getData();
		}
		Node temp = front;

		for (int i = 1; i < index; i++) {
			temp = temp.getNext();
		}

		return temp.getData();
	}

	/***********************************************************
	 * Returns true if this list contains no elements.
	 * 
	 * @return true if this list contains no elements and false
	 *         otherwise
	 ***********************************************************/
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/***********************************************************
	 * Returns the number of elements in this list.
	 * 
	 * @return the number of elements in this list
	 ***********************************************************/
	@Override
	public int size() {
		return size;
	}

	/***********************************************************
	 * Removes all of the elements from this list. The list will be
	 * empty after this call returns.
	 ***********************************************************/
	@Override
	public void clear() {
		front = null;
		rear = null;
		size = 0;
	}

}
