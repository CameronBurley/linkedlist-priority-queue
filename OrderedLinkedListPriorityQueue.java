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

public class OrderedLinkedListPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E>{
	private Node<E> head;
	private int size;
	private int modificationCounter;
	
	public OrderedLinkedListPriorityQueue(){
		head = null;
		size = 0;
		modificationCounter = 0;
	}
	
	class Node<E>{
		E data;
		Node <E> next;
	
		public Node(E obj) {
			data = obj;
			next = null;
		}
	}
	
	/**
	 * Description: Inserts a new object into the priority queue
	 * Parameters: Node for insertion 
	 * Return: True if successfully inserted. False if the PriorityQueue is full
	 * 
	 * List is in descending order. Highest priority in the front.
	 * 
	 * If it is the first item in the list then insert.
	 * else iterate throughout list to find priority location. Place at the end of priority.
	 */
	public boolean insert(E object) {
		Node<E> newNode = new Node<E>(object);
		Node<E> curr = head, prev = head;
		
		if(head== null)
			head = newNode;
		else {
			//If the object has lower or equal priority keep going
			while(curr != null && object.compareTo(curr.data) >= 0) { 
				prev = curr;
				curr = curr.next;
			}
		
			if(curr == head) {										
				newNode.next = head;
				head = newNode;
			}
			else {
			newNode.next = prev.next;
			prev.next = newNode;
			}	
		}
		size++;
		modificationCounter++;
		return true;	
	}
	
	/**
	 * Description: Removes and returns the object with the highest priority that's 
	 * been in the priority queue the longest.
	 * Parameters: No parameters
	 * Return: Returns the object to be removed. Return null if the list is empty
	 * 
	 * The list is ordered in descending order of priority. Objects with the highest priority is in the front.
	 * This group of nodes are also ordered in descending order, with the oldest in the front. 
	 * 
	 */
	public E remove() {		
		if(isEmpty()) {
			return null;
		}
		E data = head.data;
		head = head.next;
		size--;
		modificationCounter++;
		return data;
	}

	/**
	 * Description: Delete all instances of the object and return true.
	 * Parameters: The object to be deleted
	 * Return: Returns true after all objects have been deleted. Return false if no object is found. 
	 * 
	 * Iterate throughout list to find the object. Once it is found, delete it. 
	 */
	public boolean delete(E obj) {
		Node <E>curr = head;
		Node <E>prev = null;
		boolean deleted = false;
				
		if(isEmpty()) {
			head = null;
			return false;
		}	
		while(curr != null)
			//If the object and the current data have the same priority 
			//place next over this object
			//If it is not of the same priority right away, iterate to find it.
			if(obj.compareTo(curr.data)==0) {	
				//If the head is of the same priority, set head to the next node. Last head is garbage collected
				//If it is not the head, but still of the same priority, set the previous node field to the node after current. 
				if(curr == head)				
					head = curr.next;
				else {							
					prev.next = curr.next;
					prev = curr;
				}
				size--;
				modificationCounter++;
				curr = curr.next;
				deleted = true;
			}
			else {								
				prev = curr;
				curr = curr.next;
			}	
			return deleted;	
	}
	
	/**
	 * Description: Return the object of the highest priority.
	 * Parameters: No Parameters
	 * Return: Return the object of the highest priority. If the list is false, return null.
	 * 
	 * Since the list is ordered in descending order the first object is the highest.
	 */
	public E peek() {
		if(isEmpty())
			return null;	
		return head.data;		
	}
	
	/**
	 * Description: See if the list contains an object
	 * Parameters: None
	 * Return: Return true if it contains object. Return false if not. 
	 * 
	 * Iterate throughout list to find the object.
	 * 
	 */
	public boolean contains(E obj) {
		Node <E>temp = head;
		
		while (temp !=null) {
			if(obj.compareTo(temp.data) == 0)		
				return true;
			temp = temp.next;
		}
		return false;
	}

	/**
	 * Description: Get the size of list
	 * Parameters: None
	 * Return: The size of the list
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Description: Clear the list
	 * Parameters: None
	 * Return: Nothing
	 * 
	 * Set the head of the list to null, then the rest of the list is garbage collected.
	 */
	public void clear() {
		head = null;
		size = 0;
		modificationCounter++;		
	}
	
	/**
	 * Description: Return true if the list is empty, otherwise false.
	 * Parameters: None
	 * Return: Boolean statement if whether the list if empty.
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Description: Return true if the list is full, otherwise false.
	 * Parameters: None
	 * Return: False
	 * 
	 * A linked list can never be full.
	 */
	public boolean isFull() {
		return false;
	}
	
	public Iterator<E> iterator() {
		return new SLListIterator();
	}
	
	class SLListIterator implements Iterator<E>{
		Node <E> nodePtr;
		long modCounter;
		
		public SLListIterator() {
			nodePtr = head;
			modCounter = modificationCounter;
		}
		
		public boolean hasNext() {
			return nodePtr != null;
		}
		
		public E next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			if(modCounter != modificationCounter) {
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
