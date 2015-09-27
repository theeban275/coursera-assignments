import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Arrays;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private class RandomizedIterator implements Iterator<Item> {

        private Item[] is;
        private int index;

        public RandomizedIterator() {
            is = Arrays.copyOf(items, size);
            StdRandom.shuffle(is);
            index = 0;
        }

        public boolean hasNext() {
            return index < is.length;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return is[index++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    private Item[] items;
    private int size;

    private static final int BUFFER_SIZE = 2;

    @SuppressWarnings("unchecked")
    public RandomizedQueue() {
        items = (Item[]) new Object[BUFFER_SIZE];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        resize();
        items[size++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) {
             throw new NoSuchElementException();
        }

        resize();
        randomize();
        Item item = items[--size];
        items[size] = null;
        return item;
    }

    private void randomize() {
        swap(StdRandom.uniform(size), size - 1);
    }

    private void swap(int i, int j) {
        Item temp = items[i];
        items[i] = items[j];
        items[j] = temp;
    }

    public Item sample() {
        if (isEmpty()) {
             throw new NoSuchElementException();
        }

        randomize();
        return items[size - 1];
    }

    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private Item[] toArray() {
        return Arrays.copyOf(items, size);
    }

    private void resize() {
        if (size < items.length / 4) {
            items = Arrays.copyOf(items, items.length / 2);
        } else if (size >= items.length) {
            items = Arrays.copyOf(items, items.length * 2);
        }
    }
}
