import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by sealde on 1/10/18.
 */
public class RandomizedLinkQueue<Item> implements Iterable<Item> {
    private Node<Item> first;
    private Node<Item> last;
    private int count = 0;

    public RandomizedLinkQueue() {                // construct an empty randomized queue
    }

    public boolean isEmpty() {                // is the randomized queue empty?
        return first == null;
    }

    public int size() {                       // return the number of items on the randomized queue
        return count;
    }

    public void enqueue(Item item) {          // add the item
        if (item == null) throw new IllegalArgumentException("item should not be null.");
        Node<Item> oldLast = last;
        last = new Node<>();
        last.item = item;
        last.next = null;
        if (isEmpty()) first = last;
        else oldLast.next = last;
        count++;
    }

    public Item dequeue() {                   // remove and return a random item
        if (isEmpty()) throw new NoSuchElementException("there are no more items to remove.");
        int delNum = getRandomNum();
        Node<Item> delNode = null;
        if (delNum == 0) {
            delNode = first;
            first = delNode.next;
        } else {
            Node<Item> preNode = get(delNum - 1);  // deleted node pre
            delNode = preNode.next;
            preNode.next = delNode.next;
            if (size() == delNum+1) last = preNode;// if delete the last node
        }
        Item item = delNode.item;
        delNode.item = null;
        delNode.next = null;
        count--;
        if (isEmpty()) last = null;
        return item;
    }

    public Item sample() {                    // return a random item (but do not remove it)
        if (isEmpty()) throw new NoSuchElementException("there are no more items to sample.");
        return get(getRandomNum()).item;
    }

    private Node<Item> get(int n) {
        Node<Item> node = first;
        for (int i = 0; i < n; i++)
            node = node.next;
        return node;
    }

    private int getRandomNum() {
        return StdRandom.uniform(count);
    }

    private class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    @Override
    public Iterator<Item> iterator() {        // return an independent iterator over items in random order
        return new RandomizedLinkQueueIterator();
    }

    private class RandomizedLinkQueueIterator implements Iterator<Item> {
        public RandomizedLinkQueue<Item> queue;

        public RandomizedLinkQueueIterator() {
            queue = new RandomizedLinkQueue<>();
            Node<Item> node = first;
            if (first != null) {
                queue.enqueue(first.item);
                while (node.next != null) {
                    node = node.next;
                    queue.enqueue(node.item);
                }
            }
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        @Override
        public Item next() {
            if (queue.isEmpty()) throw new NoSuchElementException("there are no more items to return.");
            return queue.dequeue();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("this iterator unsupported remove operation.");
        }
    }

/*    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("RandomizedLinkQueue{");
//        Node<Item> current = first;
//        while (current != null) {
//            sb.append(current.item + ", ");
//            current = current.next;
//        }
        Iterator<Item> it = iterator();
        while (it.hasNext()) {
            Item item = it.next();
            sb.append(item + ", ");
        }
        if (sb.indexOf(", ") != -1) sb.delete(sb.length()-2, sb.length());
        sb.append("}");
        return sb.toString();
    }*/

    public static void main(String[] args) {  // unit testing (optional)
/*        RandomizedLinkQueue<String> queue = new RandomizedLinkQueue<>();
        for (int i = 0; i < 1000; i++) {
            System.out.println(queue);
            if (StdRandom.bernoulli() && !queue.isEmpty()) {
                System.out.println("dequeue:" + queue.dequeue());
            } else {
                System.out.println("enqueue:a" + i);
                queue.enqueue("a" + i);
            }
        }*/
    }
}
