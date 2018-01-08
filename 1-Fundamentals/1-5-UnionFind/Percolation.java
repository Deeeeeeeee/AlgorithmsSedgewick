import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

/**
 * Created by sealde on 12/31/17.
 */
public class Percolation {
    private boolean[] isOpened;
    private final int n;
    private int openSiteCount = 0;
    private final WeightedQuickUnionUF quickUnionUF;
    private final WeightedQuickUnionUF quickUnionUFNoButton;

    public Percolation(int n) {               // create n-by-n grid, with all sites blocked
        if (n <= 0) throw new IllegalArgumentException("n must bigger than zero.");
        this.isOpened = new boolean[n*n + 2];
        this.n = n;
        this.quickUnionUF = new WeightedQuickUnionUF(n*n + 2);
        this.quickUnionUFNoButton = new WeightedQuickUnionUF(n*n + 1);
        this.isOpened[getVirtualTop()] = true;
        this.isOpened[getVirtualButton()] = true;
    }

    public void open(int row, int col) {  // open site (row, col) if it is not open already
        int i = getArrayNum(row, col);
        if (isOpen(row, col))
            return;
        else {
            isOpened[i] = true;
            openSiteCount++;
            if (findTop(row, col) != -1 && isOpened[findTop(row, col)]) {
                this.quickUnionUF.union(i, findTop(row, col));
                this.quickUnionUFNoButton.union(i, findTop(row, col));
            }
            if (findLeft(row, col) != -1 && isOpened[findLeft(row, col)]) {
                this.quickUnionUF.union(i, findLeft(row, col));
                this.quickUnionUFNoButton.union(i, findLeft(row, col));
            }
            if (findButton(row, col) != -1 && isOpened[findButton(row, col)]) {
                this.quickUnionUF.union(i, findButton(row, col));
                if (findButton(row, col) != n*n + 1)
                    this.quickUnionUFNoButton.union(i, findButton(row, col));
            }
            if (findRight(row, col) != -1 && isOpened[findRight(row, col)]) {
                this.quickUnionUF.union(i, findRight(row, col));
                this.quickUnionUFNoButton.union(i, findRight(row, col));
            }
        }
    }

    public boolean isOpen(int row, int col) { // is site (row, col) open?
        checkInput(row, col);
        return isOpened[getArrayNum(row, col)];
    }

    public boolean isFull(int row, int col) { // is site (row, col) full?
        checkInput(row, col);
        return this.quickUnionUFNoButton.connected(getArrayNum(row, col), getVirtualTop());
    }

    public int numberOfOpenSites() {      // number of open sites
        return openSiteCount;
    }

    public boolean percolates() {             // does the system percolate?
        return this.quickUnionUF.connected(getVirtualTop(), getVirtualButton());
    }

    private void checkInput(int row, int col) {
        if (row >= 1 && row <= n && col >= 1 && col <= n) return;
        else throw new IllegalArgumentException("illegal input. please input row and col which in [1, n].");
    }

    private int getArrayNum(int row, int col) {
        try {
            checkInput(row, col);
            return (row - 1) * n + col - 1;
        } catch (IllegalArgumentException e) {
            return -1;
        }
    }

    private int findTop(int row, int col) {
        if (row == 1 && col >= 1 && col <= n) return getVirtualTop();
        return getArrayNum(row - 1, col);
    }

    private int findLeft(int row, int col) {
        return getArrayNum(row, col - 1);
    }

    private int findButton(int row, int col) {
        if (row == n && col >= 1 && col <= n) return getVirtualButton();
        return getArrayNum(row + 1, col);
    }

    private int findRight(int row, int col) {
        return getArrayNum(row, col + 1);
    }

    private int getVirtualTop() {
        return n*n;
    }

    private int getVirtualButton() {
        return n*n + 1;
    }

    public static void main(String[] args) {  // test client (optional)
        int[] inputs = StdIn.readAllInts();
        Percolation percolation = new Percolation(inputs[0]);
        for (int i = 1; i < inputs.length; i += 2) {
            percolation.open(inputs[i], inputs[i+1]);
        }
        StdOut.println(percolation.percolates());
        StdOut.println(percolation.numberOfOpenSites());
    }
}
