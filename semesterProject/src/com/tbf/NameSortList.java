package com.tbf;

import java.util.Iterator;


public class NameSortList<T> implements SortList<T>{
	
	private Node<T> head;
	private static int size;
	
	public void add(T port) {
		boolean isInserted = false;
		if (isEmpty()) {
			Node<T> newHead = new Node<T>(port);
			head = newHead;
			size++;
			
		} else {
			Node<T> newNode = new Node<T>(port);
			Node<T> curr = head;
			Node<T> prev = null;
			while(curr.getNext() != null) {
				String lName = ((Portfolio) curr.getElement()).getOwnerCode().getName().getLastName();
				String newName = ((Portfolio) newNode.getElement()).getOwnerCode().getName().getLastName();
				if (lName.compareToIgnoreCase(newName) < 0) {
					prev = curr;
					curr = curr.getNext();
					
				} else if (lName.compareToIgnoreCase(newName) > 0) {
					prev = newNode;
					newNode.setNext(curr);
					size++;
					isInserted = true;
					break;
					
				} else {
					String fName1 = ((Portfolio) curr.getElement()).getOwnerCode().getName().getFirstName();
					String fName2 = ((Portfolio) newNode.getElement()).getOwnerCode().getName().getFirstName();
					if (fName1.compareToIgnoreCase(fName2) < 0) {
						prev = curr;
						curr = curr.getNext();
						
					} else if (fName1.compareToIgnoreCase(fName2) > 0) {
						if(prev == null) {
							newNode.setNext(head);
							head = newNode;
						}
						else {
							prev.setNext(newNode);
							newNode.setNext(curr);
						}
						size++;
						isInserted = true;
						break;
						
					} else {

					}
				}				
			}
			if (isInserted == false) {
				addAtTail(port);
				isInserted = true;
			}
		}
	}
	
	private void addAtHead(T element) {
		Node<T> newHead = new Node<T>(element);
		newHead.setNext(head);
		head = newHead;
		size++;
	}

	private void addAtTail(T element) {
		if (isEmpty()) {
			addAtHead(element);
			return;
		}
		Node<T> curr = head;
		while (curr.getNext() != null) {
			curr = curr.getNext();
		}
		Node<T> newTail = new Node<T>(element);
		curr.setNext(newTail);
		size++;
	}
	
	public T removeFromHead() {
		if(isEmpty()) {
			throw new IllegalStateException("The list is empty. Action cannot be done.");
		}
		T item = this.head.getElement();
		this.head = this.head.getNext();
		size--;		
		return item;
	}
	
	public void addAtIndex(T item, int index) {
		if(index < 0 || index > size) {
			throw new IllegalArgumentException("Index given does not exist.");
		}
		if(index == 0) {
			this.addAtHead(item);
		} else if(index == size) {
			this.addAtTail(item);
		} else {
			Node<T> newNode = new Node<T>(item);
			Node<T> prevNode = this.getNodeAtIndex(index-1);
			Node<T> currNode = prevNode.getNext();
			newNode.setNext(currNode);
			prevNode.setNext(newNode);
			size++;
		}
	}
	
	public T getElementAtIndex(int index) {
		return getNodeAtIndex(index).getElement();
	}
	
	private Node<T> getNodeAtIndex(int index) {
		if(index < 0 || index >= size) {
			throw new IllegalArgumentException("Index given does not exist.");
		}
		
		Node<T> curr = this.head;
		for(int i=0; i<index; i++) {
			curr = curr.getNext();
		}
		return curr;
	}
	
	public int getSize() {
		return size;
	}

	public static boolean isEmpty() {
		return (size == 0);
	}

	@Override
	public Iterator<T> iterator() {
		return (Iterator<T>) new Iterator<T>() {
			Node<T> curr = head;
			@Override
			public boolean hasNext() {
				if(curr == null)
					return false;
				else
					return true;
			}
			@Override
			public T next() {
<<<<<<< HEAD
				T item = curr.getElement();
				curr = curr.getNext();
				return item;
=======
				T element = curr.getElement();
				curr = curr.getNext();
				return element;
>>>>>>> 99ec9cc7f1376645b1ddd043abb0af4e73bdb702
			}
			};
	}

	
}

