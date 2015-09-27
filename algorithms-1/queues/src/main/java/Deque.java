import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        public Item item;
        public Node next;
        public Node prev;
    }

    private class DequeIterator implements Iterator<Item> {

        private Node current;

        public DequeIterator() {
            this.current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (current == null) {
                throw new NoSuchElementException();
            }

            Item item = current.item;
            current = current.next;

            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    private Node first;
    private Node last;
    private int size;

    public Deque() {
        this.size = 0;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        if (first == null) {
            first = new Node();
            first.item = item;
            last = first;
        } else {
            Node oldFirst = first;
            first = new Node();
            first.item = item;
            first.next = oldFirst;
            oldFirst.prev = first;
        }

        size++;
    }

    public void addLast(Item item) {
        if (item == null) {
             throw new NullPointerException();
        }

        if (last == null) {
            last = new Node();
            last.item = item;
            first = last;
        } else {
            Node oldLast = last;
            last = new Node();
            last.item = item;
            oldLast.next = last;
            last.prev = oldLast;
        }

        size++;
    }

    public Item removeFirst() {
        if (first == null) {
            throw new NoSuchElementException();
        }

        Node oldFirst = first;
        first = oldFirst.next;
        if (first != null) {
            first.prev = null;
        }
        oldFirst.next = null;

        size--;

        if (first == null) {
            last = null;
        }

        return oldFirst.item;
    }

    public Item removeLast() {
        if (last == null) {
            throw new NoSuchElementException();
        }

        Node oldLast = last;
        last = oldLast.prev;
        if (last != null) {
            last.next = null;
        }
        oldLast.prev = null;

        size--;

        if (last == null) {
            first = null;
        }

        return oldLast.item;
    }

    public boolean isEmpty() {
         return size == 0;
    }

    public int size() {
        return size;
    }

    public Item[] toArray() {
        @SuppressWarnings("unchecked")
        Item[] items = (Item[]) new Object[size];
        Node n = first;
        for (int i = 0; i < items.length; i++) {
            items[i] = n.item;
            n = n.next;
        }
        return items;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
}
