import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;

/**
 * Created by sealde on 1/27/18.
 */
public class BruteCollinearPoints {
    private LineSegment[] lineSegments = new LineSegment[2];
    private int count = 0;

    public BruteCollinearPoints(Point[] points) {    // finds all line segments containing 4 points
        checkInput(points);
        Point min = null;
        Point max = null;
        for (int i = 0; i < points.length; i++) {
            Point pointI = points[i];
            min = pointI;
            max = pointI;
            for (int j = i + 1; j < points.length; j++) {
                Point pointJ = points[j];
                double slopeIJ = pointI.slopeTo(pointJ);
                if (pointI.compareTo(pointJ) > 0) min = pointJ;
                else max = pointJ;
                for (int k = j + 1; k < points.length; k++) {
                    Point pointK = points[k];
                    double slopeIK = pointI.slopeTo(pointK);
                    if (slopeIJ != slopeIK) continue;
                    if (min.compareTo(pointK) > 0) min = pointK;
                    if (max.compareTo(pointK) < 0) max = pointK;
                    for (int l = k + 1; l < points.length; l++) {
                        Point pointL = points[l];
                        if (slopeIJ == pointI.slopeTo(pointL)) {
                            if (min.compareTo(pointL) > 0) min = pointL;
                            if (max.compareTo(pointL) < 0) max = pointL;
                            StdOut.println("min: " + min + " max:" + max);
                            this.addSegment(new LineSegment(min, max));
                        }
                    }
                }
            }
        }
        resizeSegments(count);
    }

    private void checkInput(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("illegal argument.please input an array contain points.");
        for (int i = 0; i < points.length; i++)
            for (int j = i + 1; j < points.length; j++)
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("illegal argument.can not construct two repeated points.");
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

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}