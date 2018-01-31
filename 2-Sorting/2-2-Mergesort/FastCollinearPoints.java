import java.util.Arrays;

/**
 * Created by sealde on 1/28/18.
 */
public class FastCollinearPoints {
    private LineSegment[] lineSegments = new LineSegment[2];
    private int count = 0;

    /**
     * copy the Point array aux from points, and check this array points; when points is null, points
     * have any null point or points have any repeated point, throw IllegalArgumentException
     *
     * 1. make one point as the origin point, sort points by the comparator which compare the slope (dy/dx);
     *  the origin point is point[i];
     * 2. find more than 3 points' slope are equal, which mean they are on a line;
     * 3. put the couple points that contains min and max into the lineSegments
     *
     * @param points
     */
    public FastCollinearPoints(Point[] points) {    // finds all line segments containing 4 or more points
        Point[] aux = copyAndCheckInputs(points);
        int length = points.length, lo;
        double slopeLo;
        for (int i = 0; i < length && length > 3; i++) {
            Arrays.sort(aux, points[i].slopeOrder());   // point[i] as the origin point
            lo = 1;
            slopeLo = getOriginSlopeTo(aux, lo);    // at begin, origin point slope to point 1 as the lo slopeLo
            for (int j = lo+1; j < length; j++) {
                double slopeHi = getOriginSlopeTo(aux, j);
                if (slopeLo != slopeHi || j == length-1) {
                    if (j == length-1 && slopeLo == slopeHi) j = length;
                    if (j - lo >= 3 && isStartedPoint(aux, lo, j-1)) {
                        Arrays.sort(aux, lo, j);
                        addSegment(new LineSegment(aux[0], aux[j-1]));
                    }
                    lo = j;
                    slopeLo = slopeHi;
                }
            }
        }
        resizeSegments(count);
    }

    private Point[] copyAndCheckInputs(Point[] points) {
        Point[] copy = checkNotNullAndCopy(points);
        checkNotRepeatedWithSort(copy);
        return copy;
    }

    private Point[] checkNotNullAndCopy(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("illegal argument.please input an array contain points.");
        int length = points.length;
        Point[] copy = new Point[length];

        for (int i = 0; i < length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("illegal argument.every point must be not null.");
            copy[i] = points[i];
        }
        return copy;
    }

    private void checkNotRepeatedWithSort(Point[] points) {
        Arrays.sort(points);    // sort points to compare them
        for (int i = 1; i < points.length; i++)
            if (points[i].compareTo(points[i-1]) == 0)
                throw new IllegalArgumentException("illegal argument.can not construct two repeated points.");
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
