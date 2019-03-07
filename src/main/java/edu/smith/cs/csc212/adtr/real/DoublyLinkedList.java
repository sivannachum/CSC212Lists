package edu.smith.cs.csc212.adtr.real;

import edu.smith.cs.csc212.adtr.ListADT;
import edu.smith.cs.csc212.adtr.errors.BadIndexError;
import edu.smith.cs.csc212.adtr.errors.TODOErr;


public class DoublyLinkedList<T> extends ListADT<T> {
	private Node<T> start;
	private Node<T> end;
	
	/**
	 * A doubly-linked list starts empty.
	 */
	public DoublyLinkedList() {
		this.start = null;
		this.end = null;
	}
	

	@Override
	public T removeFront() {
		checkNotEmpty();
		T toReturn = start.value;
		Node<T> newStart = start.after;
		newStart.before = null;
		start = newStart;
		return toReturn;
	}

	@Override
	public T removeBack() {
		checkNotEmpty();
		
		throw new TODOErr();
	}

	@Override
	public T removeIndex(int index) {
		checkNotEmpty();
		throw new TODOErr();
	}

	@Override
	public void addFront(T item) {
		Node<T> newStart = new Node<T>(item);
		newStart.after = start;
		newStart.before = null;
		if (start != null) {
			start.before = newStart;
		}
		start = newStart;
	}

	@Override
	public void addBack(T item) {
		if (end == null) {
			start = end = new Node<T>(item);
		} else {
			Node<T> secondLast = end;
			end = new Node<T>(item);
			end.before = secondLast;
			secondLast.after = end;
		}
	}

	@Override
	public void addIndex(int index, T item) {
		throw new TODOErr();
	}

	@Override
	public T getFront() {
		throw new TODOErr();
	}

	@Override
	public T getBack() {
		throw new TODOErr();
	}
	
	@Override
	public T getIndex(int index) {
		throw new TODOErr();
	}
	
	public void setIndex(int index, T value) {
		throw new TODOErr();
	}

	@Override
	public int size() {
		throw new TODOErr();
	}

	@Override
	public boolean isEmpty() {
		throw new TODOErr();
	}
	
	/**
	 * The node on any linked list should not be exposed.
	 * Static means we don't need a "this" of DoublyLinkedList to make a node.
	 * @param <T> the type of the values stored.
	 */
	private static class Node<T> {
		/**
		 * What node comes before me?
		 */
		public Node<T> before;
		/**
		 * What node comes after me?
		 */
		public Node<T> after;
		/**
		 * What value is stored in this node?
		 */
		public T value;
		/**
		 * Create a node with no friends.
		 * @param value - the value to put in it.
		 */
		public Node(T value) {
			this.value = value;
			this.before = null;
			this.after = null;
		}
	}
}
