package edu.smith.cs.csc212.adtr.real;

import edu.smith.cs.csc212.adtr.ListADT;
import edu.smith.cs.csc212.adtr.errors.BadIndexError;
import edu.smith.cs.csc212.adtr.errors.EmptyListError;

/**
 * This is a data structure that has an array inside each node of an ArrayList.
 * Therefore, we only make new nodes when they are full. Some remove operations
 * may be easier if you allow "chunks" to be partially filled.
 * 
 * @author jfoley
 * @param <T> - the type of item stored in the list.
 */
public class ChunkyArrayList<T> extends ListADT<T> {
	private int chunkSize;
	private GrowableList<FixedSizeList<T>> chunks;

	public ChunkyArrayList(int chunkSize) {
		this.chunkSize = chunkSize;
		chunks = new GrowableList<>();
	}
	
	private FixedSizeList<T> makeChunk() {
		return new FixedSizeList<>(chunkSize);
	}

	@Override
	public T removeFront() {
		checkNotEmpty();
		return removeIndex(0);
	}

	@Override
	public T removeBack() {
		checkNotEmpty();
		while (chunks.getBack().isEmpty()) {
			chunks.removeBack();
			checkNotEmpty();
		}
		return chunks.getBack().removeBack();
	}

	@Override
	public T removeIndex(int index) {
		if (this.isEmpty()) {
			throw new EmptyListError();
		}
		int start = 0;
		for (FixedSizeList<T> chunk : this.chunks) {
			// calculate bounds of this chunk.
			int end = start + chunk.size();
			
			// Check whether the index should be in this chunk:
			if (start <= index && index < end) {
				return chunk.removeIndex(index - start);
			}
			
			// update bounds of next chunk.
			start = end;
		}
		throw new BadIndexError(index);
	}

	@Override
	public void addFront(T item) {
		if (chunks.isEmpty()) {
			FixedSizeList<T> front = makeChunk();
			front.addFront(item);
			chunks.addFront(front);
		}
		else if (chunks.getFront().isFull()) {
			FixedSizeList<T> newFront = makeChunk();
			newFront.addFront(item);
			chunks.addFront(newFront);
		}
		else {
			chunks.getFront().addFront(item);
		}
	}

	@Override
	public void addBack(T item) {
		if (chunks.isEmpty()) {
			FixedSizeList<T> back = makeChunk();
			back.addBack(item);
			chunks.addBack(back);
		}
		else if (chunks.getBack().isFull()) {
			FixedSizeList<T> newBack = makeChunk();
			newBack.addBack(item);
			chunks.addBack(newBack);
		}
		else {
			chunks.getBack().addBack(item);
		}
	}

	@Override
	public void addIndex(int index, T item) {
		// THIS IS THE HARDEST METHOD IN CHUNKY-ARRAY-LIST.
		// DO IT LAST.
		
		int chunkIndex = 0;
		int start = 0;
		for (FixedSizeList<T> chunk : this.chunks) {
			// calculate bounds of this chunk.
			int end = start + chunk.size();
			
			// Check whether the index should be in this chunk:
			if (start <= index && index <= end) {
				if (chunk.isFull()) {
					// check can roll to next
					// or need a new chunk
					FixedSizeList<T> nextChunk;
					if (chunkIndex < chunks.size() - 1) {
						nextChunk = chunks.getIndex(chunkIndex + 1);
						if (nextChunk.isFull()) {
							nextChunk = makeChunk();
							chunks.addIndex(chunkIndex + 1, nextChunk);
						}
					}
					else {
						nextChunk = makeChunk();
						chunks.addBack(nextChunk);
					}
					nextChunk.addFront(chunk.removeBack());
					chunk.addIndex(index - start, item);
				} else {
					// put right in this chunk, there's space.
					chunk.addIndex(index - start, item);
				}	
				// upon adding, return.
				return;
			}
			
			// update bounds of next chunk.
			start = end;
			chunkIndex++;
		}
		throw new BadIndexError(index);
	}
	
	@Override
	public T getFront() {
		checkNotEmpty();
		return this.chunks.getFront().getFront();
	}

	@Override
	public T getBack() {
		checkNotEmpty();
		return this.chunks.getBack().getBack();
	}


	@Override
	public T getIndex(int index) {
		if (this.isEmpty()) {
			throw new EmptyListError();
		}
		int start = 0;
		for (FixedSizeList<T> chunk : this.chunks) {
			// calculate bounds of this chunk.
			int end = start + chunk.size();
			
			// Check whether the index should be in this chunk:
			if (start <= index && index < end) {
				return chunk.getIndex(index - start);
			}
			
			// update bounds of next chunk.
			start = end;
		}
		throw new BadIndexError(index);
	}
	
	@Override
	public void setIndex(int index, T value) {
		boolean set = false;
		if (this.isEmpty()) {
			throw new EmptyListError();
		}
		int start = 0;
		for (FixedSizeList<T> chunk : this.chunks) {
			// calculate bounds of this chunk.
			int end = start + chunk.size();
			
			// Check whether the index should be in this chunk
			// If it is, set it to the new value
			if (start <= index && index < end) {
				chunk.setIndex(index - start, value);
				set = true;
				break;
			}
			
			// update bounds of next chunk.
			start = end;
		}
		if (!set) {
			throw new BadIndexError(index);
		}
	}

	@Override
	public int size() {
		int total = 0;
		for (FixedSizeList<T> chunk : this.chunks) {
			total += chunk.size();
		}
		return total;
	}

	@Override
	public boolean isEmpty() {
		return this.chunks.isEmpty();
	}
}