/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {

      @Override
      public int compare(Point o1, Point o2) {
          if (o1 == null || o2 == null)
            throw new NullPointerException();
         double d = slopeTo(o1) - slopeTo(o2);
         if (d > 0)
            return 1;
         else if (d < 0)
            return -1;
         else
            return 0;
      }
    };

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
       if (that.x == this.x && that.y == this.y)
           return Double.NEGATIVE_INFINITY;
       if (that.x == this.x)
           return Double.POSITIVE_INFINITY;
       if (that.y == this.y)
          return 0.0;
       else
          return (that.y-this.y + 0.0)/(that.x-this.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
       if (y == that.y) {
          return x - that.x;
       } else {
          return y - that.y;
       }
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}