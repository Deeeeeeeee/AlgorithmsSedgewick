import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

/**
 * Created by sealde on 4/1/18.
 */
public class PointSET {
    private SET<Point2D> set;

    public PointSET() {                              // construct an empty set of points
        this.set = new SET<>();
    }

    public boolean isEmpty() {                     // is the set empty?
        return set.isEmpty();
    }

    public int size() {                        // number of points in the set
        return set.size();
    }

    public void insert(Point2D p) {             // add the point to the set (if it is not already in the set)
        if (p == null) throw new IllegalArgumentException();
        set.add(p);
    }

    public boolean contains(Point2D p) {           // does the set contain point p?
        return set.contains(p);
    }

    public void draw() {                        // draw all points to standard draw
        for (Point2D p : set)
            StdDraw.point(p.x(), p.y());
    }

    public Iterable<Point2D> range(RectHV rect) {            // all points that are inside the rectangle (or on the boundary)
        SET<Point2D> rangeSet = new SET<Point2D>();
        for (Point2D p : set) {
            if (rect.contains(p))
                rangeSet.add(p);
        }
        return rangeSet;
    }

    public Point2D nearest(Point2D p) {            // a nearest neighbor in the set to point p; null if the set is empty
        Point2D nearestPoint2D = null;
        double distance = Double.MAX_VALUE;

        if (this.set.isEmpty()){
            return nearestPoint2D;
        }

        for (Point2D point : set) {
            if (point.distanceTo(p) < distance){
                nearestPoint2D = point;
                distance = point.distanceTo(p);
            }
        }
        return nearestPoint2D;
    }

    public static void main(String[] args) {                 // unit testing of the methods (optional)
    }
}
