import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class MergeSort {
    private static int mergeTimes = 0;
    private static int mergeArrayCount = 0;
    private static int sortTimes = 0;

    public static void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        sortTimes++;
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
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
        MergeSort.sort(inputs);
        MergeSort.show(inputs);
        StdOut.println("is sorted:" + MergeSort.isSorted(inputs));*/
        SortCompare.timeRandomInput("MergeSort", 10900, 1);
        StdOut.println("mergeTimes: " + mergeTimes);
        StdOut.println("mergeArrayCount: " + mergeArrayCount);
        StdOut.println("sortTimes: " + sortTimes);
        StdOut.println(10900 * (Math.log(10900)/Math.log(2)));
    }
}
