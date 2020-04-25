package com.tbf;

import java.util.Iterator;

/**
 * The ValueSortList has the items ordered by their value.
 */
public class ValueSortList<T> implements SortList<T> {
	private Node<T> head;
	private static int size;

	/**
	 * The @add method takes in a Type T (this will usually be a Portfolio). The
	 * method first checks if the list is empty and if so it inserts the given item
	 * at the head of the list. If that is not the case, the method goes into a
	 * while loop that compares based on value.
	 */
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
					if (prev == null) {
						newNode.setNext(head);
						head = newNode;

					} else {
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
			addAtTail(port);
			isInserted = true;
		}
	}

	// Adding an element at the head of the list.
	private void addAtHead(T element) {
		Node<T> newHead = new Node<T>(element);
		newHead.setNext(head);
		head = newHead;
		size++;
	}

	// Adding an element at the end or tail of the list.
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

	// Removing the element at the head.
	public T removeFromHead() {
		if (isEmpty()) {
			throw new IllegalStateException("The list is empty. Action cannot be done.");
		}
		T item = this.head.getElement();
		this.head = this.head.getNext();
		size--;
		return item;
	}

	// Adding an element at a specific index on the list.
	public void addAtIndex(T element, int index) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException("Index given does not exist.");
		}
		if (index == 0) {
			this.addAtHead(element);
		} else if (index == size) {
			this.addAtTail(element);
		} else {
			Node<T> newNode = new Node<T>(element);
			Node<T> prevNode = this.getNodeAtIndex(index - 1);
			Node<T> currNode = prevNode.getNext();
			newNode.setNext(currNode);
			prevNode.setNext(newNode);
			size++;
		}
	}

	// Retrieving the element wanted for a given index.
	public T getElementAtIndex(int index) {
		return getNodeAtIndex(index).getElement();
	}

	// Retrieving the node wanted a given index.
	private Node<T> getNodeAtIndex(int index) {
		if (index < 0 || index >= size) {
			throw new IllegalArgumentException("Index given does not exist.");
		}

		Node<T> curr = this.head;
		for (int i = 0; i < index; i++) {
			curr = curr.getNext();
		}
		return curr;
	}

	// returns the size of the size of the list.
	public int getSize() {
		return size;
	}

	// returns whether the list is empty or not.
	public static boolean isEmpty() {
		return (size == 0);
	}

	// The Iterator defines how the list is iterated through.
	@Override
	public Iterator<T> iterator() {
		return (Iterator<T>) new Iterator<T>() {
			Node<T> curr = head;

			@Override
			public boolean hasNext() {
				if (curr == null)
					return false;
				else
					return true;
			}

			@Override
			public T next() {
				T element = curr.getElement();
				curr = curr.getNext();
				return element;
			}

		};
	}
}
