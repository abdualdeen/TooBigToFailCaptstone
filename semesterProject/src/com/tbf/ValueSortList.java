package com.tbf;

import java.util.Iterator;

public class ValueSortList<T> implements SortList<T>{
	private Node<T> head;
	private static int size;

	public void add(T port) {
		Node<T> newNode = new Node<T>(port);
		boolean isInserted = false;
		if (isEmpty()) {
			Node<T> newHead = new Node<T>(port);
			head = newHead;
			size++;

		} else {
			Node<T> curr = head;
			Node<T> prev = null;
			while (curr.getNext() != null) {
				double currValue = ((Portfolio) curr.getElement()).getTotal();
				double newValue = ((Portfolio) newNode.getElement()).getTotal();
				if (currValue < newValue) {
					prev = curr;
					curr = curr.getNext();

				} else if (currValue > newValue) {
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

				}
			}

		}
		if (isInserted == false) {
			insertAtTail(port);
			isInserted = true;
		}
	}


	private void insertAtHead(T element) {
		Node<T> newHead = new Node<T>(element);
		newHead.setNext(head);
		head = newHead;
		size++;
	}

	private void insertAtTail(T element) {
		if (isEmpty()) {
			insertAtHead(element);
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
