package com.tbf;

public class Node<T> {
	private T element;
	private Node<T> next;
//	private Node<T> previous;
	
	public Node(T item) {
//		this.previous = (Node<T>) this.element;
		this.element = item;
		this.next = null;
	}
	
	public T getElement() {
		return element;
	}

	public void setElement(T item) {
		this.element = item;
	}

	public Node<T> getNext() {
		return next;
	}

	public void setNext(Node<T> next) {
		this.next = next;
	}
	
	public boolean hasNext() {
		return this.next != null;
	}
	
//	public Node<T> getPrevious() {
//		return previous;
//	}
//
//	public void setPrevious(Node<T> previous) {
//		this.previous = previous;
//	}
//	
//	public boolean hasPrevious() {
//		return this.previous != null;
//	}
}
