import java.util.TreeSet;


public class PointSET {
   
   private TreeSet<Point2D> ps = new TreeSet<Point2D>();
   public PointSET() {
   }
   
   public boolean isEmpty() {
      return ps.isEmpty();
   }
   public int size() {
      return ps.size();
   }
   public boolean contains(Point2D p) {
      return ps.contains(p);
   }
   
   public void draw() {
      for (Point2D p : ps)
         p.draw();
   }
   
   public Iterable<Point2D> range(RectHV rect) {
      TreeSet<Point2D> pis = new TreeSet<Point2D>();
      for (Point2D p : ps) {
         if (p.y() > rect.ymax())
            break;
         if (rect.contains(p))
            pis.add(p);
      }
      return pis;
   }
   
   public Point2D nearest(Point2D p) {
      Point2D np = null;
      double nd = Double.MAX_VALUE;
      for (Point2D pc : ps) {
         if (pc.distanceTo(p) < nd) {
            np = pc;
            nd = pc.distanceTo(p);
         }
      }
      return np;
   }

public void insert(Point2D p) {
   ps.add(p);
}
}