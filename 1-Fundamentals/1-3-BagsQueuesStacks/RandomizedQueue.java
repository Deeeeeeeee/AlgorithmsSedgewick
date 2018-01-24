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
        items[count] = null;
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
        private int[] seat;
        private int c;

        public RandomizedQueueIterator() {
            seat = new int[count];
            for (int i = 0; i < count; i++)
                seat[i] = i;
            c = count;
        }

        @Override
        public boolean hasNext() {
            return c != 0;
        }

        @Override
        public Item next() {
            if (c == 0) throw new NoSuchElementException("there are no more items to return.");
            int randomNum = StdRandom.uniform(c);
            Item item = items[seat[randomNum]];
            seat[randomNum] = seat[--c];
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("this iterator unsupported remove operation.");
        }
    }

    public static void main(String[] args) {
/*        RandomizedQueue<String> queue = new RandomizedQueue<>();
        for (int i = 0; i < 10; i++)
            queue.enqueue("a" + i);
        Iterator<String> it1 = queue.iterator();
        Iterator<String> it2 = queue.iterator();
        while (it1.hasNext()) {
            System.out.println("it1: " + it1.next());
            System.out.println("it2: " + it2.next());
        }*/
/*        RandomizedQueue<String> queue = new RandomizedQueue<>();
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
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        long begin = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            if (StdRandom.bernoulli() && !queue.isEmpty()) {
                queue.dequeue();
            } else {
                queue.enqueue("a" + i);
            }
        }
        System.out.println(times + " cost:" + (System.currentTimeMillis() - begin));
    }*/
}
