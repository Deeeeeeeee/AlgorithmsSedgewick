import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int times = Integer.parseInt(args[0]);
        String input = null;

        // read string and put in queue
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            input = StdIn.readString();
            queue.enqueue(input);
        }

        while (times > 0) {
            StdOut.println(queue.dequeue());
            times--;
        }
    }
}
