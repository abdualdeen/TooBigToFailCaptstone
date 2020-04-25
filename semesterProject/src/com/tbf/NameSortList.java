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
				insertAtTail(port);
				isInserted = true;
			}
		}
	}
	
	public void insertAtHead(T element) {
		Node<T> newHead = new Node<T>(element);
		newHead.setNext(head);
		head = newHead;
		size++;
	}
	
	public void insertAtTail(T element) {
		if(isEmpty()) {
			insertAtHead(element);
			return;
		}
		Node<T> curr = head;
		while(curr.getNext() != null) {
			curr = curr.getNext();
		}
		Node<T> newTail = new Node<T>(element);
		curr.setNext(newTail);
		size++;
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
				Portfolio item = (Portfolio) curr.getElement();
				curr = curr.getNext();
				return (T) item;
			}

			};
	}

	
}

