import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
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
            root = new KdNode(p, true, new RectHV(0, 0, 1, 1));
            size++;
        } else {
            add(root, p);        // add point
        }
    }

    private void add(KdNode kdNode, Point2D p) {        // add point
        if (kdNode.point.equals(p)) return;             // if exist point, return
        if (kdNode.isLeftOrBelow(p)) {                  // if kdNode will add point at left or below
            if (kdNode.lb == null) {
                kdNode.lb = new KdNode(p, kdNode, true);
                size++;
                return;
            }
            add(kdNode.lb, p);
        } else {
            if (kdNode.rt == null) {
                kdNode.rt = new KdNode(p, kdNode, false);
                size++;
                return;
            }
            add(kdNode.rt, p);
        }
    }

    public boolean contains(Point2D p) {           // does the set contain point p?
        return find(root, p);
    }

    private boolean find(KdNode kdNode, Point2D p) {
        if (kdNode.point.equals(p)) return true;
        if (kdNode.isLeftOrBelow(p)) {
            if (kdNode.lb == null) return false;
            else return find(kdNode.lb, p);
        } else {
            if (kdNode.rt == null) return false;
            else return find(kdNode.rt, p);
        }
    }

    public void draw() {                        // draw all points to standard draw
        draw(root);
    }

    private void draw(KdNode kdNode) {
        if (kdNode == null) return;

        // draw the point
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.005);
        StdDraw.point(kdNode.point.x(), kdNode.point.y());

        // draw the line
        StdDraw.setPenColor(kdNode.isVertical ? StdDraw.RED : StdDraw.BLUE);
        StdDraw.setPenRadius(0.001);
        if (kdNode.isVertical) StdDraw.line(kdNode.point.x(), kdNode.rect.ymin(), kdNode.point.x(), kdNode.rect.ymax());
        else StdDraw.line(kdNode.rect.xmin(), kdNode.point.y(), kdNode.rect.xmax(), kdNode.point.y());

        draw(kdNode.lb);
        draw(kdNode.rt);
    }

    public Iterable<Point2D> range(RectHV rect) {            // all points that are inside the rectangle (or on the boundary)
        SET<Point2D> rangeSet = new SET<>();
        range(rangeSet, rect, root);
        return rangeSet;
    }

    private void range(SET<Point2D> rangeSet, RectHV rect, KdNode kdNode) {
        if (kdNode == null) return;
        if (rect.contains(kdNode.point)) rangeSet.add(kdNode.point);
        if (kdNode.isVertical) {
            if (!(kdNode.point.x() < rect.xmin())) range(rangeSet, rect, kdNode.lb);    // rect is not whole right kdNode
            if (!(kdNode.point.x() > rect.xmax())) range(rangeSet, rect, kdNode.rt);    // rect is not whole left kdNode
        } else if (!kdNode.isVertical) {
            if (!(kdNode.point.y() < rect.ymin())) range(rangeSet, rect, kdNode.lb);    // rect is not whole below kdNode
            if (!(kdNode.point.y() > rect.ymax())) range(rangeSet, rect, kdNode.rt);    // rect is not whole above kdNode
        }
    }

    public Point2D nearest(Point2D p) {            // a nearest neighbor in the set to point p; null if the set is empty
        if (root == null) return null;
        return nearest(root, p, root.point);
    }

    private Point2D nearest(KdNode kdNode, Point2D queryP, Point2D championP) {
        if (kdNode == null) return championP;
        if (queryP.equals(kdNode.point)) return kdNode.point;   // if find the query point, return this point

        double distanceC = championP.distanceSquaredTo(queryP);
        double distanceR = kdNode.rectHVDistanceSquaredTo(queryP);
        /*
         * if distance(champion point to query point) is closer than the distance(rectHV to query point)
         * return champion point
         * no need to explore the node(or its subtrees)
         */
        if (distanceC < distanceR) return championP;

        double distanceK = kdNode.distanceSquaredTo(queryP);
        if (distanceK <= distanceC) championP = kdNode.point;   // update champion point


        championP = nearest(kdNode.lb, queryP, championP);
        championP = nearest(kdNode.rt, queryP, championP);
        return championP;
    }

    private static class KdNode {
        private KdNode lb;      // left or below
        private KdNode rt;      // right or top
        private Point2D point;
        private RectHV rect;
        private boolean isVertical;  // true: this.lb => left, this.rt => right; false: lb => below, this.rt => above

        public KdNode(Point2D p, boolean isVertical, RectHV rect) {
            this.point = p;
            this.isVertical = isVertical;
            this.rect = rect;
        }

        public KdNode(Point2D p, KdNode parentNode, boolean isLeftOrBelow) {
            this.point = p;
            this.isVertical = !parentNode.isVertical;

            RectHV parentRect = parentNode.rect;
            Point2D parentPoint = parentNode.point;
            double xMin = (parentNode.isVertical && !isLeftOrBelow) ? parentPoint.x() : parentRect.xmin();
            double xMax = (parentNode.isVertical && isLeftOrBelow) ? parentPoint.x() : parentRect.xmax();
            double yMin = (!parentNode.isVertical && !isLeftOrBelow) ? parentPoint.y() : parentRect.ymin();
            double yMax = (!parentNode.isVertical && isLeftOrBelow) ? parentPoint.y() : parentRect.ymax();
            this.rect = new RectHV(xMin, yMin, xMax, yMax);
        }

        /** this.point distance square to p: dx*dx + dy*dy **/
        public double distanceSquaredTo(Point2D p) {
            return this.point.distanceSquaredTo(p);
        }

        /** this.rect distance square to p: dx*dx + dy*dy **/
        public double rectHVDistanceSquaredTo(Point2D p) {
            return this.rect.distanceSquaredTo(p);
        }

        /** p is on the left or below of this kdNode **/
        public boolean isLeftOrBelow(Point2D p) {
            return (isVertical && p.x() < this.point.x()) || (!isVertical && p.y() < this.point.y());
        }
    }

    public static void main(String[] args) {                 // unit testing of the methods (optional)
        /*KdTree kdTree = new KdTree();
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
        kdTree.draw();*/

        // initialize the data structures from file
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
        StdDraw.show();
    }
}
