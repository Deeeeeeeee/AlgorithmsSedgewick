import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by sealde on 1/22/18.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int count = 0;

    public RandomizedQueue() {
        items = (Item[]) new Object[1];
    }

    private RandomizedQueue(int capacity) {
        items = (Item[]) new Object[capacity];
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("item should not be null.");
        if (count == items.length) resize(2 * items.length);
        items[count++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("there are no more items to remove.");
        int randomNum = getRandomNum();
        Item item = items[randomNum];
        items[randomNum] = items[--count];
        if (count > 0 && count == items.length/4) resize(items.length/2);
        return item;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("there are no more items to remove.");
        return items[getRandomNum()];
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < count; i++)
            copy[i] = items[i];
        items = copy;
    }

    private int getRandomNum() {
        return StdRandom.uniform(count);
    }

/*    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("RandomizedLinkQueue{");
        Iterator<Item> it = iterator();
        while (it.hasNext()) {
            Item item = it.next();
            sb.append(item + ", ");
        }
        if (sb.indexOf(", ") != -1) sb.delete(sb.length()-2, sb.length());
        sb.append("}");
        return sb.toString();
    }*/

    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private RandomizedQueue<Item> queue;

        public RandomizedQueueIterator() {
            queue = new RandomizedQueue<>(count);
            for (int i = 0; i < count; i++)
                queue.enqueue(items[i]);
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

    public static void main(String[] args) {
        /*RandomizedLinkQueue<String> queue = new RandomizedLinkQueue<>();
        for (int i = 0; i < 1000; i++) {
            System.out.println(queue);
            if (StdRandom.bernoulli() && !queue.isEmpty()) {
                System.out.println("dequeue:" + queue.dequeue());
            } else {
                System.out.println("enqueue:a" + i);
                queue.enqueue("a" + i);
            }
        }*/
/*        test(1000);
        test(2000);
        test(4000);
        test(8000);
        test(16000);
        test(32000);
        test(64000);
        test(128000);
        test(256000);*/
    }

/*    private static void test(int times) {
        RandomizedLinkQueue<String> queue = new RandomizedLinkQueue<>();
        long begin = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
//            System.out.println(queue);
            if (StdRandom.bernoulli() && !queue.isEmpty()) {
                queue.dequeue();
//                System.out.println("dequeue:" + queue.dequeue());
            } else {
//                System.out.println("enqueue:a" + i);
                queue.enqueue("a" + i);
            }
        }
        System.out.println(times + " cost:" + (System.currentTimeMillis() - begin));
    }*/
}
