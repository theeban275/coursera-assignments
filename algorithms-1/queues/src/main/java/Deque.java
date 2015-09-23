import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        public Item item;
        public Node next;
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
            if (oldLast != null) {
                oldLast.next = last;
            }
        }

        size++;
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
        return null;
    }
}
