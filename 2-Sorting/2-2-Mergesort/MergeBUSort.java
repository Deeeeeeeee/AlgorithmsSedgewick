import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by sealde on 1/26/18.
 */
public class MergeBUSort {
    private static int mergeTimes = 0;
    private static int mergeArrayCount = 0;

    public static void sort(Comparable[] a) {
        int N = a.length;
        Comparable[] aux = new Comparable[N];
        for (int sz = 1; sz < N; sz = sz+sz)
            for (int lo = 0; lo < N-sz; lo += sz+sz)
                merge(a, aux, lo, lo+sz-1, Math.min(lo+sz+sz-1, N-1));
    }

    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        mergeTimes++;
        mergeArrayCount += (hi - lo + 1);
        // copy
        for (int k = lo; k <= hi; k++)
            aux[k] = a[k];
        // merge
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid)                    a[k] = aux[j++];
            else if (j > hi)                a[k] = aux[i++];
            else if (less(aux[i], aux[j]))  a[k] = aux[i++];
            else                            a[k] = aux[j++];
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
        return isSorted(a, 0, a.length - 1);
    }

    public static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo; i < hi; i++)
            if (less(a[i + 1], a[i])) return false;
        return true;
    }

    public static void show(Comparable[] a) {
        for (Comparable i : a)
            StdOut.print(i + " ");
        StdOut.println();
    }

    public static void main(String[] args) {
        /*String[] inputs = StdIn.readAllStrings();
        MergeBUSort.sort(inputs);
        MergeBUSort.show(inputs);
        StdOut.println("is sorted:" + MergeBUSort.isSorted(inputs));*/
        SortCompare.timeRandomInput("MergeBUSort", 10900, 1);
        StdOut.println("mergeTimes: " + mergeTimes);
        StdOut.println("mergeArrayCount: " + mergeArrayCount);
    }
}
