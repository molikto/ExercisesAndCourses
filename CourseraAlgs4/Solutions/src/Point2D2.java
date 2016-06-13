import java.util.Arrays;
import java.util.Comparator;


public class Point2D2 {
	private final double x;
	private final double y;
	String str ="";
	
	public Point2D2(double xx, double yy) {
		x = xx; y = yy;
	}
	public Point2D2(String str) {
		x = Double.parseDouble(str.substring(3, 6));
		y = Double.parseDouble(str.substring(7, 11));
		this.str = str;
	}
	
	public String toString() {
		return str;
	}
	
	public static int ccw(Point2D2 a, Point2D2 b, Point2D2 c) {
		if (a == b)
			return 1;
		else if (a == c)
			return -1;
		double area2 = (b.x-a.x)*(c.y-a.y) - (b.y-a.y)*(c.x-a.x);
	      if      (area2 < 0) return -1;  // clockwise
	      else if (area2 > 0) return +1;  // counter-clockwise
	      else                return  0;  // collinear
	}
	
	public static String STR =  "A (0.0, 3.0)*B (3.0, 6.0)*C (4.0, 5.0)*D (2.0, 2.0)*E (1.0, 0.0)*F (5.0, 9.0)*G (8.0, 4.0)*H (7.0, 7.0)*I (6.0, 1.0)*J (9.0, 8.0)*";
	 public static void main(String[] args) {
		Point2D2[] points = new Point2D2[10];
		for (int i = 0; STR.indexOf('*') > 0; i++) {
			points[i] = new Point2D2(STR.substring(0, STR.indexOf('*')));
			STR = STR.substring(STR.indexOf('*')+1);
		}
		hull(points);
	}
	
	public static void hull(Point2D2[] points) {
		Point2D2 start = points[0];
		for (Point2D2 p : points)
			if (p.y < start.y)
				start = p;
		Arrays.sort(points, start.new PolarOrder());
		Point2D2[] results = new Point2D2[points.length];
		results[0] = points[0];
		results[1] = points[1];
		for (int i = 2, j = 1; i < points.length; i++) {
			results[++j] = points[i];
			if (ccw(results[j-2], results[j-1], results[j]) < 0) {
				System.out.println(results[j-1]);
				j-=2;
				i--;
			}

		}
	}


class PolarOrder implements Comparator<Point2D2>
  {
     public int compare(Point2D2 q1, Point2D2 q2)
     {
   	  return -ccw(Point2D2.this, q1, q2);
     }
  }
}
