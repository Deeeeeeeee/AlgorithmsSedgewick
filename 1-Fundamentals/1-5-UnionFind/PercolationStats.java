import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by sealde on 1/1/18.
 */
public class PercolationStats {
    private final int trials;
    private final int n;
    private double[] x;
    private double mean;
    private double stddev;

    public PercolationStats(int n, int trials) {   // perform trials independent experiments on an n-by-n grid
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("error input.please confirm n > 0 and trials > 0");
        this.n = n;
        this.trials = trials;
        this.x = new double[trials];
        for (int i = 0; i < trials; i++) {
            x[i] = cal();
        }
    }

    public double mean() {                         // sample mean of percolation threshold
        if (mean == 0)
            mean = StdStats.mean(x);
        return mean;
    }

    public double stddev() {                       // sample standard deviation of percolation threshold
        if (stddev == 0)
            stddev = StdStats.stddev(x);
        return stddev;
    }

    public double confidenceLo() {                 // low  endpoint of 95% confidence interval
        return mean() - 1.96 * stddev() / Math.sqrt(trials);
    }

    public double confidenceHi() {                 // high endpoint of 95% confidence interval
        return mean() + 1.96 * stddev() / Math.sqrt(trials);
    }

    private double cal() {
        Percolation percolation = new Percolation(n);
        while (!percolation.percolates()) {
            percolation.open(getRandom(), getRandom());
        }
        return (double) percolation.numberOfOpenSites() / (n*n);
    }

    private int getRandom() {
        return StdRandom.uniform(1, n+1);
    }

    public static void main(String[] args) {       // test client (described below)
        int input1 = Integer.valueOf(args[0]);
        int input2 = Integer.valueOf(args[1]);
        PercolationStats percolationStats = new PercolationStats(input1, input2);
        StdOut.printf("%-24s= %.16f\n", "mean", percolationStats.mean());
        StdOut.printf("%-24s= %.16f\n", "stddev", percolationStats.stddev());
        StdOut.printf("%-24s= [%.16f, %.16f]\n", "95% confidence interval",
                percolationStats.confidenceLo(), percolationStats.confidenceHi());
    }
}
