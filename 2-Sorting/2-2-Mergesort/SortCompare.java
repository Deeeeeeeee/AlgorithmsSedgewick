import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by sealde on 1/26/18.
 */
public class SortCompare {
    public static void main(String[] args) {
/*        String alg1 = args[0];
        String alg2 = args[1];
        int N = Integer.parseInt(args[2]);
        int T = Integer.parseInt(args[3]);
        double t1 = timeRandomInput(alg1, N, T);
        double t2 = timeRandomInput(alg2, N, T);
        StdOut.printf("For %d random Doubles\n  %s is", N, alg1);
        StdOut.printf(" %.4f times faster than %s\n", t2/t1, alg2);*/
        double t1 = timeRandomInput("MergeBUSort", 10900, 1000);
        StdOut.println(t1);
//        double t2 = timeRandomInput("MergeBUSort", 10900, 1000);
//        StdOut.println(t1 + ":" + t2 + "    :" + (t2/t1));
    }

    public static double timeRandomInput(String alg, int N, int T) {
        // 长度为 N 的数组
        double total = 0.0;
        String[] a = new String[N];
        for (int t = 0; t < T; t++) {
            // 进行 T 次试验
            for (int i = 0; i < N; i++)
                a[i] = "i" + StdRandom.uniform();
            total += time(alg, a);
        }
        return total;
    }

    public static double time(String alg, String[] a) {
        Stopwatch timer = new Stopwatch();
        if ("MergeSort".equals(alg)) MergeSort.sort(a);
        if ("MergeBUSort".equals(alg)) MergeBUSort.sort(a);
        return timer.elapsedTime();
    }
}
