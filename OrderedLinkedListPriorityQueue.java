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
	
	//Inserting in the beginning. 
	public boolean insert(E object) {
		Node<E> newNode = new Node<E>(object);
		Node<E> curr = head, prev = head;
		
		if(head== null)
			head = newNode;
		else {
			while(curr != null && object.compareTo(curr.data) >= 0) { //If the object has lower or equal priority keep going
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
	
	//Remove and return node with the highest priority
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

	//How to implement the modificationCounter?
	public boolean delete(E obj) {
		Node <E>curr = head;
		Node <E>prev = null;
		boolean deleted = false;
				
		if(isEmpty()) {
			head = null;
			return false;
		}	
		while(curr != null)
			if(obj.compareTo(curr.data)==0) {	//If the object and the current data have the same priority 
				if(curr == head)				//If the head is of the same priority, set head to the next node. Last head is garbage collected
					head = curr.next;
				else {							//If it is not the head, but still of the same priority, set the previous node field to the node after current. 
					prev.next = curr.next;
					prev = curr;
				}
				size--;
				modificationCounter++;
				curr = curr.next;
				deleted = true;
			}
			else {								//If it is not of the same priority right away, iterate to find it.
				prev = curr;
				curr = curr.next;
			}	
			return deleted;	
	}
	
	public E peek() {
		if(isEmpty())
			return null;	
		return head.data;		
	}
	
	public boolean contains(E obj) {
		Node <E>temp = head;
		
		while (temp !=null) {
			if(obj.compareTo(temp.data) == 0)		//If the object is found, return true.
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
	
	//A linked-list can never be full.
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
