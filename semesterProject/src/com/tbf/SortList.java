package com.tbf;

/**
 * This class is implemented by the NameSortList, ValueSortList and
 * ManagerSortList classes. It allows for each of those classes to have their
 * own implementation of the Iterator.
 */
public interface SortList<T> extends Iterable<T> {
	public void add(T port);
}
