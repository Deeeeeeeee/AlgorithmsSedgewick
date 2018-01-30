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
                    if (slopeIJ != pointI.slopeTo(pointK)) {
                        continue;
                    }
                    if (min.compareTo(pointK) > 0) min = pointK;
                    if (max.compareTo(pointK) < 0) max = pointK;
                    for (int r = k + 1; r < points.length; r++) {
                        Point pointR = points[r];
                        if (slopeIJ == pointI.slopeTo(pointR)) {
                            if (min.compareTo(pointR) > 0) min = pointR;
                            if (max.compareTo(pointR) < 0) max = pointR;
                            this.addSegment(new LineSegment(min, max));
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