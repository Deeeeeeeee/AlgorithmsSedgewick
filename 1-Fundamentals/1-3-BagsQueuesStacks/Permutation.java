import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        double count = 0;

        RandomizedQueue<String> queue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            count++;
            if (queue.size() == k) {
                if (StdRandom.bernoulli(k/count)) {
                    queue.dequeue();
                    queue.enqueue(StdIn.readString());
                } else {
                    StdIn.readString();
                }
            } else {
                queue.enqueue(StdIn.readString());
            }
        }

        Iterator<String> it = queue.iterator();
        while (it.hasNext()) {
            StdOut.println(it.next());
        }
    }
}
