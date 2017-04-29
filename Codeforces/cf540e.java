import java.io.*;
import java.util.*;

public class cf540e {

    static boolean DEBUG = false;
    static Scanner cin;
    static PrintStream out = System.out;


    public static void main(String args[]) throws Exception {
        // input
        if ((args.length != 0 && "test".equals(args[0]))) {
            DEBUG = true;
            String TEST = "3\n" +
                    "1 6\n" +
                    "3 4\n" +
                    "2 5";
            cin = new Scanner(new ByteArrayInputStream(TEST.getBytes()));
        } else {
            cin =  new Scanner(System.in);
        }
        int n = cin.nextInt();
        TreeMap<Integer, Seq> seqs = new TreeMap<Integer, Seq>();
        seqs.put(1, new Seq(1, 1000000000));
        for (int i = 0; i < n; i++) {
            int l = cin.nextInt();
            int r = cin.nextInt();
            Seq ll = breakBy(l, seqs);
            Seq rr = breakBy(r, seqs);
            ll.exchange(rr);
        }
        Elem[] elems = new Elem[seqs.size()];
        int ii = 0;
        for (Seq entry : seqs.values()) {
            elems[ii++] = new Elem(entry);
        }
        sort(elems);
        out.println(swaps);
    }

    static long swaps = 0;

    private static long merge(Elem[] a, Elem[] aux, int lo, int mid, int hi, long l1, long l2) {
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }
        int i = lo, j = mid+1;
        int ll1 = 0;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > hi) {
                a[k] = aux[i++];
            } else if (aux[j].elem < aux[i].elem) {
                swaps += (l1 - ll1) * aux[j].length;
                a[k] = aux[j++];
            } else {
                ll1 += aux[i].length;
                a[k] = aux[i++];
            }
        }
        return l1 + l2;
    }

    // mergesort a[lo..hi] using auxiliary array aux[lo..hi]
    private static long sort(Elem[] a, Elem[] aux, int lo, int hi) {
        if (hi <= lo) return a[hi].length;
        int mid = lo + (hi - lo) / 2;
        long l1 = sort(a, aux, lo, mid);
        long l2 = sort(a, aux, mid + 1, hi);
        return merge(a, aux, lo, mid, hi, l1, l2);
    }

    /**
     * Rearranges the array in ascending order, using the natural order.
     * @param a the array to be sorted
     */
    public static void sort(Elem[] a) {
        Elem[] aux = new Elem[a.length];
        sort(a, aux, 0, a.length-1);
    }


    static class Elem {
        int elem;
        int length;

        public Elem(Seq entry) {
            elem = entry.l;
            length = entry.length();
        }
    }

    private static Seq breakBy(int l, TreeMap<Integer, Seq> seqs) {
        Map.Entry<Integer, Seq> last = seqs.floorEntry(l);
        int key = last.getKey();
        Seq val = last.getValue();
        if (key == l && val.single()) {
            return val;
        } else {
            if (key == l) {
                int temp = val.r;
                val.r = val.l;
                seqs.put(key + 1, new Seq(val.l + 1, temp));
                return val;
            } else if (key + val.r - val.l == l) {
                val.r -= 1;
                Seq rr = new Seq(val.r + 1, val.r + 1);
                seqs.put(l, rr);
                return rr;
            } else {
                int temp = val.r;
                val.r = val.l + l - key - 1;
                Seq rr = new Seq(val.r + 1, val.r + 1);
                seqs.put(l, rr);
                seqs.put(l + 1, new Seq(rr.l + 1, temp));
                return rr;
            }
        }
    }

//    private static Seq search(int l, List<Seq> seqs) {
//        int index = seqs.size() / 2;
//        Seq center = seqs.get(index);
//        if (center.index <= l && center.index + center.r - center.l >= l) {
//            return center;
//        } else if (center.index > l) {
//            return search(l, seqs.subList(0, index));
//        } else {
//            return search(l, seqs.subList(index + 1, seqs.size()));
//        }
//    }


    public static class Seq {
        int l;
        int r;
        public boolean single() {
            return l == r;
        }
        public Seq(int ll, int rr) {
            l = ll;
            r = rr;
        }

        public void exchange(Seq rr) {
            int tl = l;
            int tr = r;
            l = rr.l;
            r = rr.r;
            rr.l = tl;
            rr.r = tr;
        }

        public int length() {
            return r - l + 1;
        }
    }
}
