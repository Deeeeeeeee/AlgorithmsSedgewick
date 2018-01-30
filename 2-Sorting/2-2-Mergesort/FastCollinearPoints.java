import java.util.Arrays;

/**
 * Created by sealde on 1/28/18.
 */
public class FastCollinearPoints {
    private LineSegment[] lineSegments = new LineSegment[2];
    private int count = 0;

    public FastCollinearPoints(Point[] points) {    // finds all line segments containing 4 or more points
        int length = points.length, lo;
        double slopeLo;
        Point[] aux = copyInputs(points);
        for (int i = 0; i < length; i++) {
            Point origin = points[i];
            Arrays.sort(aux, origin.slopeOrder());
            lo = 1;
            slopeLo = getOriginSlopeTo(aux, lo);
            for (int j = lo+1; j < length; j++) {
                double slopeTemp = getOriginSlopeTo(aux, j);
                if (slopeLo != slopeTemp || j == length-1) {
                    if (j == length-1 && slopeLo == slopeTemp) j = length;
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
        int length = points.length;
        Point[] copy = new Point[length];
        for (int i = 0; i < length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("illegal argument.every point must be not null.");
            copy[i] = points[i];
        }
        Arrays.sort(copy);
        for (int i = 1; i < length; i++)
            if (copy[i].compareTo(copy[i-1]) == 0)
                throw new IllegalArgumentException("illegal argument.can not construct two repeated points.");
        return copy;
    }

    private double getOriginSlopeTo(Point[] points, int i) {
        return points[0].slopeTo(points[i]);
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
        LineSegment[] copy = new LineSegment[count];
        for (int i = 0; i < count; i++)
            copy[i] = lineSegments[i];
        return copy;
    }

    public static void main(String[] args) {

    }
}
