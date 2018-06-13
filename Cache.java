import java.util.NoSuchElementException;

/**
 * cache implemented with linked list
 * 
 * @author Jeffrey Moore
 *
 * @param <T>
 *            type of element
 */
public class Cache<T> {

	private Node<T> head, tail;
	private int size;

	/** Constructor creates an empty cache */
	public Cache() {
		head = tail = null;
		size = 0;
	}

	/**
	 * gets the size of the cache
	 * 
	 * @return number of elements in Cache
	 */
	public int size() {
		return size;
	}

	/**
	 * checks if cache is empty
	 * 
	 * @return true if empty
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * searches cache for desired element
	 * 
	 * @param target
	 *            desired element
	 * @return
	 */
	public boolean cacheContains(T target) {
		Node<T> current = head;
		while (current != null) {
			if (current.getElement().equals(target)) {
				return true;
			}
			current = current.getNext();
		}
		return false;
	}

	/**
	 * returns the specified element
	 * 
	 * @param target
	 *            element to get
	 * @return targeted element
	 */
	public T getObject(T target) {

		T retVal = target;

		return retVal;
	}

	/**
	 * clears the cache
	 */
	public void clearCache() {
		head = tail = null;
	}

	/**
	 * adds specified object to front of Cache
	 * 
	 * @param element
	 *            to be added to front of list
	 */
	public void addObject(T element) {
		// check size
		Node<T> newNode = new Node<>(element);
		if (head == null) {
			head = tail = newNode;
		} else {
			newNode.setNext(head);
			head = newNode;
		}
		size++;
	}

	/**
	 * moves specified element to front of the cache, removes prior occurrence
	 * 
	 * @param element
	 *            to move to front
	 * @return element moved to front
	 */
	public T movetoFront(T element) {
		removeObject(element);
		addObject(element);
		return element;
	}

	/**
	 * removes the specified element from the cache
	 * 
	 * @param element
	 *            to be removed
	 */
	public void removeObject(T element) {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}

		boolean found = false;
		Node<T> previous = null;
		Node<T> current = head;

		while (current != null && !found) {
			if (element.equals(current.getElement())) {
				found = true;
			} else {
				previous = current;
				current = current.getNext();
			}
		}

		if (!found) {
			throw new NoSuchElementException();
		}

		if (size() == 1) { // only node
			head = tail = null;
		} else if (current == head) { // first node
			head = current.getNext();
		} else if (current == tail) { // last node
			tail = previous;
			tail.setNext(null);
		} else { // somewhere in the middle
			previous.setNext(current.getNext());
		}
		size--;
	}

	/**
	 * removes last element in cache
	 */
	public void removeLast() {

		Node<T> last = head;
		Node<T> beforeLast = null;

		if (isEmpty()) {
			throw new NoSuchElementException();
		}

		if (size == 1) {
			head = tail = null;
			size--;
		} else {
			while (last.getNext() != null) {
				beforeLast = last;
				last = last.getNext();
			}
			tail = beforeLast;
			tail.setNext(null);
			size--;
		}
	}

	/**
	 * Inner class for a node in a linked list
	 * 
	 * @param <E>
	 *            type
	 */
	private class Node<E> {
		private Node<E> next;
		private E element;

		/**
		 * Creates a node storing the specified element.
		 *
		 * @param elem
		 *            the element to be stored within the new node
		 */
		public Node(E elem) {
			next = null;
			element = elem;
		}

		/**
		 * Returns the node that follows this one.
		 *
		 * @return the node that follows the current one
		 */
		public Node<E> getNext() {
			return next;
		}

		/**
		 * Sets the node that follows this one.
		 *
		 * @param node
		 *            the node to be set to follow the current one
		 */
		public void setNext(Node<E> node) {
			next = node;
		}

		/**
		 * Returns the element stored in this node.
		 *
		 * @return the element stored in this node
		 */
		public E getElement() {
			return element;
		}
	}
}
