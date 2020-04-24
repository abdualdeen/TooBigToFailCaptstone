package com.tbf;

import java.util.Iterator;

public class ValueSortList<T> implements SortList<Portfolio>{
	private static Node<Portfolio> head;
	private static int size;

	public void add(Portfolio port) {
		Node<Portfolio> newNode = new Node(port);
		boolean isInserted = false;
		if (isEmpty()) {
			Node<Portfolio> newHead = new Node(port);
			head = newHead;
			size++;

		} else {
			Node<Portfolio> curr = head;
			Node<Portfolio> prev = null;
			while (curr.getNext() != null) {
				double currValue = curr.getElement().getTotal();
				double newValue = newNode.getElement().getTotal();
				if (currValue < newValue) {
					prev = curr;
					curr = curr.getNext();

				} else if (currValue > newValue) {
					prev.setNext(newNode);
					newNode.setNext(curr);
					size++;
					isInserted = true;
					break;

				}
			}

		}
		if (isInserted == false) {
			insertAtTail(newNode);
			isInserted = true;
		}
	}


	public static void insertAtHead(Node<Portfolio> element) {
		Node<Portfolio> newHead = new Node(element);
		newHead.setNext(head);
		head = newHead;
		size++;
	}

	public static void insertAtTail(Node<Portfolio> element) {
		if (isEmpty()) {
			insertAtHead(element);
			return;
		}
		Node<Portfolio> curr = head;
		while (curr.getNext() != null) {
			curr = curr.getNext();
		}
		Node<Portfolio> newTail = new Node(element);
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
