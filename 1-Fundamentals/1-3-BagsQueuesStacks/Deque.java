import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by sealde on 1/7/18.
 */
public class Deque<Item> implements Iterable<Item> {

    private Node<Item> first;
    private Node<Item> last;
    private int count;

    public Deque() {                      // construct an empty deque
        this.count = 0;
    }

    public boolean isEmpty() {                // is the deque empty?
        return count == 0;
    }

    public int size() {                       // return the number of items on the deque
        return count;
    }

    public void addFirst(Item item) {         // add the item to the front
        if (item == null) throw new IllegalArgumentException("item should not be null.");
        Node<Item> oldFirst = first;
        first = new Node<>();
        first.item = item;
        first.next = oldFirst;
        if (isEmpty()) last = first;
        else oldFirst.pre = first;
        count++;
    }

    public void addLast(Item item) {          // add the item to the end
        if (item == null) throw new IllegalArgumentException("item should not be null.");
        Node<Item> oldLast = last;
        last = new Node<>();
        last.pre = oldLast;
        last.item = item;
        last.next = null;
        if (isEmpty()) first = last;
        else oldLast.next = last;
        count++;
    }

    public Item removeFirst() {               // remove and return the item from the front
        if (isEmpty()) throw new NoSuchElementException("there are no more items to remove.");
        Node<Item> oldFirst = first;
        Item item = oldFirst.item;
        first = oldFirst.next;
        oldFirst.item = null;
        oldFirst.next = null;
        oldFirst.pre = null;
        count--;
        if (isEmpty()) last = null;
        return item;
    }

    public Item removeLast() {                // remove and return the item from the end
        if (isEmpty()) throw new NoSuchElementException("there are no more items to remove.");
        Node<Item> oldLast = last;
        Item item = oldLast.item;
        last = oldLast.pre;
        oldLast.item = null;
        oldLast.next = null;
        oldLast.pre = null;
        count--;
        if (isEmpty()) first = null;
        else last.next = null;
        return item;
    }

    @Override
    public Iterator<Item> iterator() {        // return an iterator over items in order from front to end
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator {
        private Node<Item> current;

        public DequeIterator() {
            current = first;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("there are no more items to return.");
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("this iterator unsupported remove operation.");
        }
    }

    private class Node<Item> {
        private Item item;
        private Node<Item> pre;
        private Node<Item> next;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Deque{");
        Iterator<Item> it = iterator();
        while (it.hasNext()) {
            Item item = it.next();
            sb.append(item + ", ");
        }
        if (sb.indexOf(", ") != -1) sb.delete(sb.length()-2, sb.length());
        sb.append("}");
        return sb.toString();
    }

    public static void main(String[] args) {  // unit testing (optional)
        Deque<String> deque = new Deque<>();
        deque.addFirst("a");
        deque.removeLast();
        deque.addLast("ccc");
        StdOut.print(deque);
    }
}
