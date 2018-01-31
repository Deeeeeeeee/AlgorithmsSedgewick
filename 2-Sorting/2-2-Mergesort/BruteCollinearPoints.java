import java.util.Arrays;

/**
 * Created by sealde on 1/27/18.
 */
public class BruteCollinearPoints {
    private LineSegment[] lineSegments = new LineSegment[2];
    private int count = 0;

    /**
     * point I, J, K, R; if not slopeIJ == slopeIK, then continue;
     * if slopeIJ == slopeIK, then test slopeIJ == slopeIR
     *
     * then sort I, J, K, R, to find the min and max
     * put the min point and max point into the lineSegments
     * @param points
     */
    public BruteCollinearPoints(Point[] points) {    // finds all line segments containing 4 points
        checkInput(points);
        for (int i = 0; i < points.length; i++) {
            Point pointI = points[i];
            for (int j = i + 1; j < points.length; j++) {
                double slopeIJ = pointI.slopeTo(points[j]);
                for (int k = j + 1; k < points.length; k++) {
                    if (slopeIJ != pointI.slopeTo(points[k])) continue;
                    for (int r = k + 1; r < points.length; r++) {
                        if (slopeIJ == pointI.slopeTo(points[r])) {
                            Point[] sortedPoints = copySortedPoints(points, i, j, k, r);
                            this.addSegment(new LineSegment(sortedPoints[0], sortedPoints[3]));
                            break;
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
            if (points[i] == null) throw new IllegalArgumentException("illegal argument.every point must not be null.");
        for (int i = 0; i < points.length; i++)
            for (int j = i + 1; j < points.length; j++)
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("illegal argument.can not construct two repeated points.");
    }

    private Point[] copySortedPoints(Point[] points, int i, int j, int k, int r) {
        Point[] copy = new Point[4];
        copy[0] = points[i];
        copy[1] = points[j];
        copy[2] = points[k];
        copy[3] = points[r];
        Arrays.sort(copy);
        return copy;
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
        LineSegment[] copy = new LineSegment[count];
        for (int i = 0; i < count; i++)
            copy[i] = lineSegments[i];
        return copy;
    }

    public static void main(String[] args) {
    }
}