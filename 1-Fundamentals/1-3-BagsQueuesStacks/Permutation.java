import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int times = Integer.valueOf(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();

        while (times > 0) {
            queue.enqueue(StdIn.readString());
            times--;
        }
        System.out.println(queue);
    }
}
