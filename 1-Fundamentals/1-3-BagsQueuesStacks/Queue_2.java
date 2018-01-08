import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by sealde on 12/3/17.
 */
public class Queue_2<Item> implements Iterable<Item> {
    private int N;
    private Node first;
    private Node last;

    private class Node {
        private Item item;
        private Node next;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public Item peek() {
        if (isEmpty()) throw new RuntimeException("Queue underflow");
        return first.item;
    }

    public void enqueue(Item item) {
        Node oldLast = this.last;
        this.last = new Node();
        this.last.item = item;
        this.last.next = null;
        if (isEmpty()) this.first = this.last;
        else oldLast.next = this.last;
        N++;
    }

    public Item dequeue() {
        if (isEmpty()) throw new RuntimeException("队列为空，不能dequeue");
        Item item = this.first.item;
        first = first.next;
        if (isEmpty()) last = null;
        N--;
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}
