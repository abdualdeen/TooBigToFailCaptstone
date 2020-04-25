package com.tbf;

import java.util.Iterator;


public class NameSortList<T> implements SortList<Portfolio>{
	
	private static Node<Portfolio> head;
	private static int size;
	
	public void add(Portfolio port) {
		boolean isInserted = false;
		if (isEmpty()) {
			Node<Portfolio> newHead = new Node<Portfolio>(port);
			head = newHead;
			size++;
			
		} else {
			Node<Portfolio> newNode = new Node<Portfolio>(port);
			Node<Portfolio> curr = head;
			Node<Portfolio> prev = null;
			while(curr.getNext() != null) {
				String lName = curr.getElement().getOwnerCode().getName().getLastName();
				String newName = newNode.getElement().getOwnerCode().getName().getLastName();
				if (lName.compareToIgnoreCase(newName) < 0) {
					prev = curr;
					curr = curr.getNext();
					
				} else if (lName.compareToIgnoreCase(newName) > 0) {
					prev = newNode;
//					prev.setNext(newNode);
					newNode.setNext(curr);
					size++;
					isInserted = true;
					break;
					
				} else {
					String fName1 = curr.getElement().getOwnerCode().getName().getFirstName();
					String fName2 = newNode.getElement().getOwnerCode().getName().getFirstName();
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
	
	public static void insertAtHead(Portfolio element) {
		Node<Portfolio> newHead = new Node<Portfolio>(element);
		newHead.setNext(head);
		head = newHead;
		size++;
	}
	
	public static void insertAtTail(Portfolio element) {
		if(isEmpty()) {
			insertAtHead(element);
			return;
		}
		Node<Portfolio> curr = head;
		while(curr.getNext() != null) {
			curr = curr.getNext();
		}
		Node<Portfolio> newTail = new Node<Portfolio>(element);
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
	public Iterator<Portfolio> iterator() {
		return (Iterator<Portfolio>) new Iterator<Portfolio>() {
			Node<Portfolio> curr = head;
			@Override
			public boolean hasNext() {
				if(curr == null)
					return false;
				else
					return true;
			}
			@Override
			public Portfolio next() {
				Portfolio item = curr.getElement();
				curr = curr.getNext();
				return item;
			}

			};
	}

	
}

