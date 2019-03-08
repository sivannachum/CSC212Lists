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
		T toReturn = end.value;
		Node<T> newEnd = end.before;
		newEnd.after = null;
		end = newEnd;
		return toReturn;
	}

	@Override
	public T removeIndex(int index) {
		checkNotEmpty();
		if (index == 0) {
			return removeFront();
		}
		else {
			Node<T> prev = start;
			Node<T> curr = start.after;
			int i = 1;
			while (i < index) {
				prev = curr;
				curr = curr.after;
				i++;
			}
			if (curr == null) {
				throw new BadIndexError(index);
			}
			else {
				Node<T> afterCurr = curr.after;
				prev.after = afterCurr;
				afterCurr.before = prev;
				return curr.value;
			}
		}
	}

	@Override
	public void addFront(T item) {
		if (start == null) {
			start = end = new Node<T>(item);
		} else {
			Node<T> second = start;
			start = new Node<T>(item);
			start.after = second;
			second.before = start;
		}
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
		if (index < 0) {
			throw new BadIndexError(index);
		}
		if (index == 0) {
			addFront(item);
		}
		else {
			int i = 0;
			Node<T> prev = start;
			while (i < index - 1) {
				prev = prev.after;
				if (prev == null) {
					throw new BadIndexError(index);
				}
				i++;
			}
			Node<T> next = prev.after;
			Node<T> beingAdded = new Node<T>(item);
			prev.after = beingAdded;
			beingAdded.after = next;
			beingAdded.before = prev;
			
		}
	}

	@Override
	public T getFront() {
		checkNotEmpty();
		return start.value;
	}

	@Override
	public T getBack() {
		checkNotEmpty();
		return end.value;
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
		int count = 0;
		for (Node<T> n = this.start; n != null; n = n.after) {
			count++;
		}
		return count;
	}

	@Override
	public boolean isEmpty() {
		return start == null;
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
