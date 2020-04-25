package com.tbf;

import java.util.Iterator;
/**
 * The ManagerSortList uses a LinkedList implementation with Nodes. Each list
 * has a defined "head" and size. The class aims to sort Managers by their
 * broker status then by their last/first name.
 */
public class ManagerSortList<T> implements SortList<T> {
	private Node<T> head;
	private static int size;
	/**
	 * The @add method takes in a Type T (this will usually be a Portfolio). The
	 * method first checks if the list is empty and if so it inserts the given item
	 * at the head of the list. If that is not the case then the method goes into a
	 * while loop to determine the correct "spot" that the addition should be in.
	 */
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
			while (curr.getNext() != null) {
				String currBroker = ((Portfolio) curr.getElement()).getManagCode().getBrokerStatus();
				String currName = ((Portfolio) curr.getElement()).getManagCode().getName().getLastName();
				String newBroker = ((Portfolio) newNode.getElement()).getManagCode().getBrokerStatus();
				String newName = ((Portfolio) newNode.getElement()).getManagCode().getName().getLastName();
				// If the current broker status is greater than the new broker then we check for
				// names of the two managers to determine which one goes where.
				if (currBroker.compareToIgnoreCase(newBroker) == 0) {
					if (currName.compareToIgnoreCase(newName) < 0) {
						prev = curr;
						curr = curr.getNext();

					} else if (currName.compareToIgnoreCase(newName) > 0) {
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

					} else {
						String fName1 = ((Portfolio) curr.getElement()).getManagCode().getName().getFirstName();
						String fName2 = ((Portfolio) newNode.getElement()).getManagCode().getName().getFirstName();
						if (fName1.compareToIgnoreCase(fName2) < 0) {
							prev = curr;
							curr = curr.getNext();

						} else if (fName1.compareToIgnoreCase(fName2) > 0) {
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

						} else {

						}
					}
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

				} else if (currBroker.compareToIgnoreCase(newBroker) < 0) {
					prev = curr;
					curr = curr.getNext();
				} else if (currBroker.compareToIgnoreCase(newBroker) > 0) {
					if (prev == null) {
						newNode.setNext(head);
						head = newNode;
					} else {
						prev.setNext(newNode);
						newNode.setNext(curr);
					}
				}
				if (isInserted == false) {
					addAtTail(port);
					isInserted = true;
				}
			}
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
	public void addAtIndex(T item, int index) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException("Index given does not exist.");
		}
		if (index == 0) {
			this.addAtHead(item);
		} else if (index == size) {
			this.addAtTail(item);
		} else {
			Node<T> newNode = new Node<T>(item);
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
