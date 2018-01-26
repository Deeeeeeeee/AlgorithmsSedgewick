import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class ShellSort {
    public static void sort(Comparable[] a) {
        int N = a.length;
        int h = 1;
        while (N/3 > h) h = h*3 + 1;    // 1, 4, 13, 40, 121, 364, ...
        while (h >= 1) {
            for (int i = h; i < N; i++) {
                for (int j = i - h; j >= 0; j -= h)
                    if (less(a[j + h], a[j]))
                        exch(a, j + h, j);
            }
            h = h / 3;
        }
    }

    public static  boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public static void exch(Comparable[] a, int i, int j) {
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }

    public static void show(Comparable[] a) {
        for (Comparable i : a)
            StdOut.print(i + " ");
        StdOut.println();
    }

    public static void show(Comparable[] a, int i, int j) {
        StdOut.print(i + " " + j + " ");
        for (Comparable ii : a)
            StdOut.print(ii + " ");
        StdOut.println();
    }

    public static void main(String[] args) {
        String[] inputs = StdIn.readAllStrings();
        ShellSort.sort(inputs);
        ShellSort.show(inputs);
        StdOut.println("is sorted:" + ShellSort.isSorted(inputs));
    }
}
