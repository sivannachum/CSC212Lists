package edu.smith.cs.csc212.adtr.real;

import edu.smith.cs.csc212.adtr.ListADT;
import edu.smith.cs.csc212.adtr.errors.BadIndexError;

public class SinglyLinkedList<T> extends ListADT<T> {
	/**
	 * The start of this list.
	 * Node is defined at the bottom of this file.
	 */
	Node<T> start;
	
	@Override
	public T removeFront() {
		checkNotEmpty();
		T value = start.value;
		start = start.next;
		return value;
	}

	@Override
	public T removeBack() {
		checkNotEmpty();
		Node<T> prev = start;
		Node<T> curr = start.next;
		if (curr == null) {
			start = null;
			return prev.value;
		}
		while (curr.next != null) {
			prev = curr;
			curr = curr.next;
		}
		T toReturn = curr.value;
		prev.next = null;
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
			Node<T> curr = start.next;
			int i = 1;
			while (i < index) {
				prev = curr;
				curr = curr.next;
				i++;
			}
			if (curr == null) {
				throw new BadIndexError(index);
			}
			else {
				prev.next = curr.next;
				return curr.value;
			}
		}
	}

	@Override
	public void addFront(T item) {
		this.start = new Node<T>(item, start);
	}

	@Override
	public void addBack(T item) {
		Node<T> curr = start;
		if (curr == null) {
			addFront(item);
		}
		else {
			while (curr.next != null) {
				curr = curr.next;
			}
			curr.next = new Node<T>(item, curr.next);
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
				prev = prev.next;
				if (prev == null) {
					throw new BadIndexError(index);
				}
				i++;
			}
			prev.next = new Node<T>(item, prev.next);
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
		Node<T> curr = start;
		while (curr.next != null) {
			curr = curr.next;
		}
		return curr.value;
	}

	@Override
	public T getIndex(int index) {
		checkNotEmpty();
		int at = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			if (at++ == index) {
				return n.value;
			}
		}
		throw new BadIndexError(index);
	}
	

	@Override
	public void setIndex(int index, T value) {
		if (index < 0) {
			throw new BadIndexError(index);
		}
		checkNotEmpty();
		Node<T> curr = start;
		int i = 0;
		while (i < index) {
			if (curr.next == null) {
				throw new BadIndexError(index);
			}
			curr = curr.next;
			i++;
		}
		curr.value = value;
	}

	@Override
	public int size() {
		int count = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			count++;
		}
		return count;
	}

	@Override
	public boolean isEmpty() {
		return this.start == null;
	}
	
	/**
	 * The node on any linked list should not be exposed.
	 * Static means we don't need a "this" of SinglyLinkedList to make a node.
	 * @param <T> the type of the values stored.
	 */
	private static class Node<T> {
		/**
		 * What node comes after me?
		 */
		public Node<T> next;
		/**
		 * What value is stored in this node?
		 */
		public T value;
		/**
		 * Create a node with no friends.
		 * @param value - the value to put in it.
		 */
		public Node(T value, Node<T> next) {
			this.value = value;
			this.next = next;
		}
	}

}
