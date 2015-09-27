import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private class Node {
        Item item;
        Node next;
    }

    private class RandomizedIterator implements Iterator<Item> {

        private Item[] items;
        private int index;

        public RandomizedIterator() {
            items = toArray();
            index = 0;
        }

        public boolean hasNext() {
            return index < items.length;
        }

        public Item next() {
            if (index == items.length) {
                throw new NoSuchElementException();
            }
            return items[index++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    private Node first;
    private int size;
    private int randomSize;

    private static final int RANDOM_SIZE = 2;

    public RandomizedQueue() {
        size = 0;
        randomSize = RANDOM_SIZE;
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

        if (first == null) {
            first = new Node();
            first.item = item;
        } else {
            Node oldFirst = first;
            first = new Node();
            first.item = item;
            first.next = oldFirst;
        }

        size++;

        randomize();
    }

    public Item dequeue() {
        if (first == null) {
             throw new NoSuchElementException();
        }

        Node oldFirst = first;
        first = oldFirst.next;
        oldFirst.next = null;

        size--;

        randomize();

        return oldFirst.item;
    }

    public Item sample() {
        if (first == null) {
             throw new NoSuchElementException();
        }

        return first.item;
    }

    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    public Item[] toArray() {
        @SuppressWarnings("unchecked")
        Item[] items = (Item[]) new Object[size];
        Node node = first;
        for (int i = 0; i < items.length; i++) {
            items[i] = node.item;
            node = node.next;
        }
        return items;
    }

    private void randomize() {
        if (size <= randomSize / 4) {
            randomSize /= 2;
        } else if (size >= randomSize) {
            randomSize *= 2;
        } else {
            return;
        }

        Item[] items = toArray();
        StdRandom.shuffle(items);
        Node node = first;
        for (int i = 0; i < items.length; i++) {
            node.item = items[i];
            node = node.next;
        }
    }
}
