import java.util.Arrays;


public class Brute {

      public static void main(String[] args) {
         if (args.length < 1)
            throw new IllegalArgumentException();
         In in = new In(args[0]);
         int num = in.readInt();
         Point[] ps = new Point[num];
         for (int i = 0; i < num; i++) {
            ps[i] = new Point(in.readInt(), in.readInt());
         }
         Arrays.sort(ps);
         for (int i1 = 0; i1 < num; i1++) {
             ps[i1].draw();
            for (int i2 = i1+1; i2 < num; i2++) {
               for (int i3 = i2+1; i3 < num; i3++) {
                  for (int i4 = i3+1; i4 < num; i4++) {
                     double s1 = ps[i1].slopeTo(ps[i2]);
                     double s2 = ps[i2].slopeTo(ps[i3]);
                     double s3 = ps[i3].slopeTo(ps[i4]);
                     if (s1 == s2 && s2 == s3) {
                         ps[i1].drawTo(ps[i4]);
                        StdOut.print(ps[i1]);
                        StdOut.print(" -> ");
                        StdOut.print(ps[i2]);
                        StdOut.print(" -> ");
                        StdOut.print(ps[i3]);
                        StdOut.print(" -> ");
                        StdOut.println(ps[i4]);
                     }
                  }
               }
            }
         }
      }
      
}
