import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by sealde on 1/10/18.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Node<Item> first;
    private Node<Item> last;
    private int count = 0;

    public RandomizedQueue() {                // construct an empty randomized queue
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
            Node<Item> preNode = get(delNum - 1);  //deleted node pre
            delNode = preNode.next;
            preNode.next = delNode.next;
        }
        Item item = delNode.item;
        delNode.item = null;
        delNode.next = null;
        if (isEmpty()) last = null;
        count--;
        return item;
    }

    public Item sample() {                    // return a random item (but do not remove it)
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
        private Node next;
    }

    @Override
    public Iterator<Item> iterator() {        // return an independent iterator over items in random order
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        public RandomizedQueue<Item> queue;

        public RandomizedQueueIterator() {
            queue = new RandomizedQueue<>();
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
            return queue.size() > 0;
        }

        @Override
        public Item next() {
            return queue.dequeue();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("RandomizedQueue{");
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
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        queue.enqueue("a");
        queue.enqueue("b");
        queue.enqueue("c");
        queue.enqueue("d");
//        System.out.println(queue.dequeue());
//        System.out.println(queue.dequeue());
        Iterator<String> it = queue.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}
