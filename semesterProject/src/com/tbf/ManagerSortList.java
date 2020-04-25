package com.tbf;

import java.util.Iterator;

public class ManagerSortList<T> implements SortList<T>{
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
				String currBroker = ((Portfolio) curr.getElement()).getManagCode().getBrokerStatus();
				String currName = ((Portfolio) curr.getElement()).getManagCode().getName().getLastName();
				String newBroker = ((Portfolio) newNode.getElement()).getManagCode().getBrokerStatus();
				String newName = ((Portfolio) newNode.getElement()).getManagCode().getName().getLastName();
				if (currBroker.compareToIgnoreCase(newBroker) > 0) {
					if (currName.compareToIgnoreCase(newName) < 0) {
						prev = curr;
						curr = curr.getNext();
						
					} else if (currName.compareToIgnoreCase(newName) > 0) {
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
						String fName1 = ((Portfolio) curr.getElement()).getManagCode().getName().getFirstName();
						String fName2 = ((Portfolio) newNode.getElement()).getManagCode().getName().getFirstName();
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
					prev = curr;
					curr = curr.getNext();
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
		return this.size;
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
				T item = curr.getElement();
				curr = curr.getNext();
				return item;
			}

			};
	}

}
