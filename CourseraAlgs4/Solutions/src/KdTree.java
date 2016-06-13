import java.util.ArrayList;


public class KdTree {
   
   private Node root;
   private int count = 0;
    private class Node {
      private boolean usingX = true;
      private Point2D point;
        private Node left, right;
       public Node(Point2D p) {
          point = p;
      }
      public boolean isGoingLeft(Point2D p) {
         if (usingX && p.x() < point.x())
            return true;
         else if (!usingX && p.y() < point.y())
            return true;
         return false;
      }
      public RectHV getLeftRect(RectHV ser) {
         if (usingX)
            return new RectHV(ser.xmin(), ser.ymin(), point.x(), ser.ymax());
         else 
            return new RectHV(ser.xmin(), ser.ymin(), ser.xmax(), point.y());
      }
      public RectHV getRightRect(RectHV ser) {
         if (usingX)
            return new RectHV(point.x(), ser.ymin(), ser.xmax(), ser.ymax());
         else 
            return new RectHV(ser.xmin(), point.y(), ser.xmax(), ser.ymax());
      }
    }
   
      public KdTree() {
      }
      public boolean isEmpty() {
         return count == 0;
      }
      public int size() {
         return count;
      }
      public void insert(Point2D p) {
         count++;
         root = insert(root,  new Node(p));
      }
      private Node insert(Node x, Node node) {
         if (x == null) return node;
         if (x.point.equals(node.point)) {
              count--;
            return x;
         }
         node.usingX = !x.usingX;
         if (x.isGoingLeft(node.point)) x.left = insert(x.left, node);
         else x.right = insert(x.right, node);
         return x;
      }
      
      public boolean contains(Point2D p) {
         return contains(root, p);
      }

       private boolean contains(Node x, Point2D p) {
           if (x == null) return false;
           if (x.point.equals(p)) return true;
            if (x.isGoingLeft(p)) return contains(x.left, p);
            else return contains(x.right, p);
       }
      
      public void draw() {
         draw(root);
      }
      
      private void draw(Node r) {
         if (r != null) {
            r.point.draw();
            draw(r.left);
            draw(r.right);
         }
   }
   public Iterable<Point2D> range(RectHV rect) {
      RectHV searchRect = new RectHV(0, 0, 1, 1);
      ArrayList<Point2D> ps = new ArrayList<Point2D>();
         range(root, rect, ps, searchRect);
         return ps;
   }
      private void range(Node x, RectHV rect, ArrayList<Point2D> ps, RectHV ser) {
         if (x == null)
            return;
   //      System.out.println(ser);
         if (rect.contains(x.point))
            ps.add(x.point);
         RectHV left = x.getLeftRect(ser);
         if (rect.intersects(left))
            range(x.left, rect, ps, left);
         RectHV right = x.getRightRect(ser);
         if (rect.intersects(right))
            range(x.right, rect, ps, right);
   }
   public Point2D nearest(Point2D p) {
       if (root == null)
           return null;
       return nearest(root, root.point, p, new RectHV(0, 0, 1, 1));
      }
   private Point2D nearest(Node x, Point2D nearset, Point2D p, RectHV parRec) {
      if (x == null)
         return nearset;
      Point2D newNearset = nearset;
      if (p.distanceTo(nearset) > p.distanceTo(x.point))
         newNearset = x.point;
      RectHV left = x.getLeftRect(parRec);
      RectHV right = x.getRightRect(parRec);
      double toLeft = left.distanceTo(p);
      double toRight = right.distanceTo(p);
      if (toLeft < toRight) {
          if (toLeft < p.distanceTo(newNearset))
             newNearset = nearest(x.left, newNearset, p, left);
          if (right.distanceTo(p) < p.distanceTo(newNearset))
             newNearset = nearest(x.right, newNearset, p, right);
      } else {
          if (toRight < p.distanceTo(newNearset))
              newNearset = nearest(x.right, newNearset, p, right);
           if (left.distanceTo(p) < p.distanceTo(newNearset))
              newNearset = nearest(x.left, newNearset, p, left);
      }
      return newNearset;
   }
}
