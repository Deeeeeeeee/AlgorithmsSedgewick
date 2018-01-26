import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class InsertionSort {
    public static void sort(Comparable[] a) {
        for (int i = 1; i < a.length; i++)
            for (int j = i; j > 0; j--)
                if (less(a[j], a[j-1]))
                    exch(a, j, j-1);
                else break;
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
        InsertionSort.show(inputs);
        InsertionSort.sort(inputs);
        StdOut.println("is sorted:" + InsertionSort.isSorted(inputs));
    }
}
