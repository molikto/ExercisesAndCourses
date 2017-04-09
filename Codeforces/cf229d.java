
import javax.management.Query;
import java.io.*;
import java.util.*;

// 13:45
public class cf229d {



    static class Reader
    {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader(InputStream in)
        {
            din = new DataInputStream(in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException
        {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException
        {
            byte[] buf = new byte[64]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1)
            {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() throws IOException
        {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do
            {
                ret = ret * 10 + c - '0';
            }  while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        public long nextLong() throws IOException
        {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }

        public double nextDouble() throws IOException
        {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();

            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');

            if (c == '.')
            {
                while ((c = read()) >= '0' && c <= '9')
                {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException
        {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException
        {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException
        {
            if (din == null)
                return;
            din.close();
        }
    }

    static boolean DEBUG = false;
    static Reader cin;
    static PrintStream out = System.out;

    public static void main(String args[]) throws Exception {
        // input
        if (args.length != 0 && "test".equals(args[0])) {
            DEBUG = true;
            String TEST = "50\n" +
                    "929 406 604 146 397 811 858 656 632 853 624 559 648 216 183 305 977 483 831 228 117 465 95 891 834 219 88 440 156 547 319 920 540 15 513 371 473 129 290 462 315 815 470 511 151 851 96 52 259 825";
            cin = new Reader(new ByteArrayInputStream(TEST.getBytes()));
        } else {
            cin = new Reader(System.in);
        }

        int[] ts = new int[cin.nextInt()];
        for (int i = 0; i < ts.length; i++) ts[i] = cin.nextInt();

        int[][] dp = new int[ts.length + 1][];
        // dp(number of towers used, number of towers we created) = last tower height
        // 8 2 7 3 1

        // 8
        // 10
        dp[1] = new int[2];
        dp[1][1] = ts[0];
        for (int i = 2; i <= ts.length; i++) {
            int k = ts[i - 1]; // adding k th tower adding
            dp[i] = new int[dp[i - 1].length + 1];
            for (int j = 1; j < dp[i].length; j++) {
                // one solution is add this tower to last solution with same number of tower
                int min = j < dp[i].length - 1 ? cut(dp[i - 1][j], k) : Integer.MAX_VALUE / 5;
                // the other solution is create a new tower, from elements [l...i - 1]
                // then the length of it is l, so l >= j - 1
                int s = 0;
                if (j - 1 > 0) {
                    for (int l = i - 1; l >= j - 1; l--) {
                        // add the l th tower to current tower
                        s = s + ts[l];
                        if (dp[l].length <= j - 1) {
                            break;
                        }
                        if (dp[l][j - 1] <= s) {
                            if (min > s) min = s;
                            break;
                        }
                    }
                }
                if (min == Integer.MAX_VALUE / 5) {
                    dp[i] = Arrays.copyOf(dp[i], j);
                    break;
                } else {
                    dp[i][j] = min;
                    //out.println(i + " " + j + " " + min);
                }
            }
        }
        out.println(ts.length - dp[ts.length].length + 1);
    }

    public static int cut(int a, int b) {
        int k = (a + b);
        if (k > Integer.MAX_VALUE/ 5) k = Integer.MAX_VALUE / 5;
        return k;
    }



    /*

    wrong answer


    int n = cin.nextInt();
    Seg start = new Seg(cin.nextInt());
        segs.add(start);
    boolean lastIncomplete = false;
        for (int i = 1; i < n; i++) {
        int k = cin.nextInt();
        if (!lastIncomplete) {
            segs.add(new Seg(k));
            lastIncomplete = true;
        } else {
            segs.get(segs.size() - 1).add(k);
        }
        if (segs.get(segs.size() - 1).sum >= segs.get(segs.size() - 2).sum) {
            lastIncomplete = false;
            relaxAt(segs.size() - 1);
        }
    }
        if (lastIncomplete) segs.remove(segs.size() - 1);
    relaxAt(segs.size() - 1);
        for (Seg s : segs) {
        out.print(s.sum + ": ");
        for (int i : s.nums) {
            out.print(i + " ");
        }
        out.println();
    }
        System.out.println(n - segs.size());

    private static void relaxAt(int rrr) {
        boolean changed = true;
        while (changed) {
            changed = false;
            int j = rrr;
            while (j >= 1) {
                Seg last = segs.get(j);
                Seg prelast = segs.get(j - 1);
                boolean moved = false;
                while (prelast.sum + last.nums.getFirst() <= last.sum - last.nums.getFirst()) {
                    prelast.add(last.nums.getFirst());
                    last.remove();
                    changed = true;
                    moved = true;
                }
                //if (!moved) return;
                j -= 1;
//                if (moved) j -= 1;
//                else j = 0;
            }
            Seg first = segs.get(0);
            if (first.nums.getFirst() <= first.sum - first.nums.getFirst()) {
                changed = true;
                Seg prefirst = new Seg(first.nums.getFirst());
                first.remove();
                segs.add(0, prefirst);
            }
        }
    }

    public static class Seg {
        int sum;
        LinkedList<Integer> nums = new LinkedList<Integer>();
        public Seg(int n) {
            this.sum = n;
            nums.add(n);
        }

        public void add(int k) {
            nums.add(k);
            sum += k;
        }

        public void remove() {
            sum -= nums.removeFirst();
        }
    }

    static ArrayList<Seg> segs = new ArrayList<Seg>();

    */
}
