import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

/**
 * Created by sealde on 1/28/18.
 */
public class FastCollinearPoints {
    private LineSegment[] lineSegments = new LineSegment[2];
    private int count = 0;

    public FastCollinearPoints(Point[] points) {    // finds all line segments containing 4 or more points
        int N = points.length, lo;
        double slopeLo;
        Point[] aux = copyInputs(points);
        for (int i = 0; i < N; i++) {
            Point origin = points[i];
            Arrays.sort(aux, origin.slopeOrder());
            lo = 1;
            slopeLo = aux[0].slopeTo(aux[1]);
            for (int j = 1+1; j < N; j++) {
                double slopeTemp = aux[0].slopeTo(aux[j]);
                if (slopeLo != slopeTemp || j == N-1) {
                    if (j == N-1 && slopeLo == slopeTemp) j = N;
                    if (j - lo >= 3 && isStartedPoint(aux, lo, j-1)) {
                        Arrays.sort(aux, lo, j);
                        addSegment(new LineSegment(aux[0], aux[j-1]));
                    }
                    lo = j;
                    slopeLo = slopeTemp;
                }
            }
        }
        resizeSegments(count);
    }

    private Point[] copyInputs(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("illegal argument.please input an array contain points.");
        int N = points.length;
        Point[] copy = new Point[N];
        for (int i = 0; i < N; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("illegal argument.every point must be not null.");
            copy[i] = points[i];
        }
        return copy;
    }

    private boolean isStartedPoint(Point[] points, int lo, int hi) {
        for (int i = lo; i < hi + 1; i++)
            if (!(points[0].compareTo(points[i]) < 0)) return false;
        return true;
    }

    private void addSegment(LineSegment segment) {
        if (count == lineSegments.length)
            resizeSegments(count * 2);
        lineSegments[count++] = segment;
    }

    private void resizeSegments(int capacity) {
        LineSegment[] result = new LineSegment[capacity];
        for (int i = 0; i < count; i++) {
            result[i] = lineSegments[i];
        }
        lineSegments = result;
    }

    public int numberOfSegments() {       // the number of line segments
        return count;
    }

    public LineSegment[] segments() {               // the line segments
        return lineSegments;
    }

    public static void main(String[] args) throws FileNotFoundException {

        System.setIn(new FileInputStream(args[0]));

        // read the n points from a file
        edu.princeton.cs.algs4.In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
