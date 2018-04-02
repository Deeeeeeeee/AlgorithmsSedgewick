import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

/**
 * Created by sealde on 4/1/18.
 */
public class KdTree {
    private KdNode root;
    private int size;

    public KdTree() {

    }

    public boolean isEmpty() {                     // is the set empty?
        return root == null;
    }

    public int size() {                        // number of points in the set
        return size;
    }

    public void insert(Point2D p) {             // add the point to the set (if it is not already in the set)
        if (p == null) throw new IllegalArgumentException("point should not be null");
        if (root == null) {
            root = new KdNode(p, true);
            size++;
        } else {
            root.add(p);        // add point
        }
    }

    public boolean contains(Point2D p) {           // does the set contain point p?
        return root.find(p);
    }

    public void draw() {                        // draw all points to standard draw
        draw(root);
    }

    private void draw(KdNode kdNode) {
        if (kdNode == null) return;
        StdDraw.point(kdNode.point.x(), kdNode.point.y());
        draw(kdNode.left);
        draw(kdNode.right);
    }

    public Iterable<Point2D> range(RectHV rect) {            // all points that are inside the rectangle (or on the boundary)
        SET<Point2D> rangeSet = new SET<>();
        range(rangeSet, rect, root);
        return rangeSet;
    }

    private void range(SET<Point2D> rangeSet, RectHV rect, KdNode kdNode) {
        if (kdNode == null) return;
        if (rect.contains(kdNode.point)) rangeSet.add(kdNode.point);
        if (kdNode.isOdd) {
            if (!(kdNode.point.x() < rect.xmin())) range(rangeSet, rect, kdNode.left);  // rect is not whole right kdNode
            if (!(kdNode.point.x() > rect.xmax())) range(rangeSet, rect, kdNode.right); // rect is not whole left kdNode
        } else if (!kdNode.isOdd) {
            if (!(kdNode.point.y() < rect.ymin())) range(rangeSet, rect, kdNode.left); // rect is not whole below kdNode
            if (!(kdNode.point.y() > rect.ymax())) range(rangeSet, rect, kdNode.right);  // rect is not whole above kdNode
        }
    }

    public Point2D nearest(Point2D p) {            // a nearest neighbor in the set to point p; null if the set is empty
        if (root == null) return null;
        return null;
    }

    private Point2D nearest(KdNode kdNode, Point2D p) {
        if (p.equals(kdNode.point)) return kdNode.point;
        if (kdNode.isOdd) {
//            double distance = p.
        }
        return null;
    }

    private class KdNode {
        private KdNode left;
        private KdNode right;
        private Point2D point;
        private RectHV rect;
        private boolean isOdd;  // true: this.left => left, this.right => right; false: left => below, this.right => above

        public KdNode(Point2D p, boolean isOdd) {
            this.point = p;
            this.isOdd = isOdd;
        }

        /** add point **/
        public void add(Point2D p) {
            add(this, p);
        }

        private void add(KdNode kdNode, Point2D p) {
            if (kdNode == null) {
                if (isLeft(p)) this.left = new KdNode(p, !isOdd);
                else this.right = new KdNode(p, !isOdd);
                size++;
                return;
            }
            if (kdNode.point.equals(p)) return;                 // if exist point, return
            if (kdNode.isLeft(p)) kdNode.add(kdNode.left, p);   // if kdNode will add point at left
            else kdNode.add(kdNode.right, p);
        }

        /** find point **/
        public boolean find(Point2D p) {
            return find(this, p);
        }

        private boolean find(KdNode kdNode, Point2D p) {
            if (kdNode.point.equals(p)) return true;
            if (kdNode.isLeft(p)) {
                if (kdNode.left == null) return false;
                else return kdNode.find(kdNode.left, p);
            } else {
                if (kdNode.right == null) return false;
                else return kdNode.find(kdNode.right, p);
            }
        }

        private boolean isLeft(Point2D p) { // p is on the left this kdNode
            return (isOdd && p.x() < this.point.x()) || (!isOdd && p.y() < this.point.y());
        }
    }

    public static void main(String[] args) {                 // unit testing of the methods (optional)
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.336885, 0.310118));
        kdTree.insert(new Point2D(0.659829, 0.983324));
        kdTree.insert(new Point2D(0.235270, 0.337294));
        kdTree.insert(new Point2D(0.832668, 0.004321));
        kdTree.insert(new Point2D(0.235270, 0.337294));
        System.out.println(kdTree.contains(new Point2D(0.235270, 0.337294)));
        System.out.println(kdTree.contains(new Point2D(0.336885, 0.310118)));
        System.out.println(kdTree.contains(new Point2D(0.832668, 0.004321)));
        System.out.println(kdTree.contains(new Point2D(0.832668, 0.004320)));
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        kdTree.draw();

/*        // initialize the data structures from file
        String filename = args[0];
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        kdtree.draw();
        StdDraw.show();*/
    }
}
