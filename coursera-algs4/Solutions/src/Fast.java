import java.util.Arrays;


public class Fast {

    public static void main(String[] args) {
       if (args.length < 1)
          throw new IllegalArgumentException();
       In in = new In(args[0]);
       int num = in.readInt();
       StdDraw.setYscale(0, 32768);
       StdDraw.setXscale(0, 32768);
       Point[] ps = new Point[num];
       for (int i = 0; i < num; i++) {
          ps[i] = new Point(in.readInt(), in.readInt());
       }
       Arrays.sort(ps);
       Point[] ps2 = new Point[num-1];
       double[] slops = new double[num-1];
       for (int i = 0; i < num; i++) {
          ps[i].draw();
          int mm = 0;
          for (int j = 0; j < num; j++) {
             if (j != i)
                ps2[mm++] = ps[j];
          }
          Arrays.sort(ps2, ps[i].SLOPE_ORDER);
          for (int j = 0; j < num - 1; j++) {
             slops[j] = ps[i].slopeTo(ps2[j]);
          }
          int j = 0;
          for (; j < num - 3; j++) {
             int k = j+1;
             for (; k < num - 1; k++) {
                if (slops[k] != slops[j])
                   break;
             }
             if (ps[i].compareTo(ps2[j]) > 0) {
                 j = k-1;
                 continue;
             }
             if (k - j >= 3) {
                  ps[i].drawTo(ps2[k-1]);
                   StdOut.print(ps[i]);
                   StdOut.print(" -> ");
                   for (int t = j; t < k - 1; t++) {
                       StdOut.print(ps2[t]);
                       StdOut.print(" -> ");
                   }
                   StdOut.println(ps2[k-1]);
             }
             j = k-1;
          }
       }
    }
    
}
