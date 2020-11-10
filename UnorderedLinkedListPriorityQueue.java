/*Program #2
 * Implementing a PriorityQueue interface using a LinkedList. 
 * CS310
 * March 12,2020
 * @author Cameron Burley cssc1208
 */
package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class UnorderedLinkedListPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {
	private Node<E> head;
	private int size;
	private int modificationCounter;

	public UnorderedLinkedListPriorityQueue() {
		head = null;
		size = 0;
		modificationCounter = 0;
	}

	class Node<E> {
		E data;
		Node<E> next;

		public Node(E obj) {
			data = obj;
			next = null;
		}
	}

	// Insert object into list and 
	public boolean insert(E object) {
		// Create new Node with object for insertion
		Node<E> newNode = new Node<E>(object);

		if (isEmpty()) {
			head = newNode;
		} else {
			// Set field of the new node to the CURRENT head of the list
			newNode.next = head;
			// Set headPointer to the new head(newNode)
			head = newNode;
		}
		size++;
		modificationCounter++;
		return true;
	}

	//Remove and return element of the highest priority
	public E remove() {
		Node<E> curr = head;
		Node<E> prev = null;
		Node<E> high = head;
		Node<E> prevtoHigh = null;

		if (isEmpty())
			return null;

		// Run to the end of the list
		while (curr != null) {
			if (curr.data.compareTo(high.data) <= 0) { // Find the highest priority, if the current is of higher priority than the last then set high.
				high = curr;
				prevtoHigh = prev;
			}
			prev = curr;
			curr = curr.next;
		}

		// If the first element the highest, simply set head to the next element.
		// Otherwise set previoustoHigh to the node after.
		if (high == head)
			head = high.next;
		else
			prevtoHigh.next = high.next;
		size--;
		modificationCounter++;
		return high.data;

	}
	
	//Delete all instances of object
	public boolean delete(E obj) {
		Node<E> curr = head;
		Node<E> prev = null;
		boolean deleted = false;

		if (isEmpty()) {
			head = null;
			return false;
		}

		while (curr != null) {
			if (obj.compareTo(curr.data) == 0) { // If the object and the current data have the same priority
				if (curr == head) // If the head is of the same priority, set head to the next node. Last head is
									// garbage collected
					head = curr.next;
				else { // If it is not the head, but still of the same priority, set the previous node
						// field to the node after current.
					prev.next = curr.next;
					prev = curr;
				}
				size--;
				modificationCounter++;
				curr = curr.next;
				deleted = true;
			} else { // If it is not of the same priority right away, iterate to find it.
				prev = curr;
				curr = curr.next;
			}
		}
		return deleted;
	}

	//Return object of highest priority
	public E peek() {
		if (isEmpty())
			return null;
		Node<E> curr = head;
		Node<E> temp = head;
		while (curr != null) {
			if (curr.data.compareTo(temp.data) <= 0) // If current has higher priority, then set temp to current.
				temp = curr;
			curr = curr.next; // Iterate if else.
		}
		return temp.data;
	}

	//See if object is in the list. Return true if it's in the list
	//Iterative approach
	public boolean contains(E obj) {
		Node<E> temp = head;
		while (temp != null) {
			if (obj.compareTo(temp.data) == 0) // If the object is found, return true.
				return true;
			temp = temp.next;
		}
		return false;
	}

	public int size() {
		return size;
	}

	public void clear() {
		head = null;
		size = 0;
		modificationCounter++;

	}

	public boolean isEmpty() {
		return size == 0;
	}

	// A linked list can never be full
	public boolean isFull() {
		return false;
	}

	public Iterator<E> iterator() {
		return new SLListIterator();
	}

	class SLListIterator implements Iterator<E> {
		Node<E> nodePtr;
		long modCounter;

		public SLListIterator() {
			nodePtr = head;
			modCounter = modificationCounter;
		}

		public boolean hasNext() {
			return nodePtr != null;
		}

		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			if (modCounter != modificationCounter) {
				throw new ConcurrentModificationException();
			}
			E tmp = nodePtr.data;
			nodePtr = nodePtr.next;
			return tmp;
		}

		public void remove() {
			throw new UnsupportedOperationException("Invalid operation");
		}

	}

}
