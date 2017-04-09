
import javax.management.Query;
import java.io.*;
import java.util.*;

// 15:32
public class cf558d {



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
            String TEST = "4 3\n" +
                    "4 10 14 1\n" +
                    "3 6 6 0\n" +
                    "2 3 3 1";
            cin = new Reader(new ByteArrayInputStream(TEST.getBytes()));
        } else {
            cin = new Reader(System.in);
        }
        int h = cin.nextInt();
        // index 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15
        // level 1 2   3       4
        // [l, r]
        long l = (long) Math.pow(2, h - 1); long r = (long) Math.pow(2, h) - 1;
        segments.put(l, r);
        int qs = cin.nextInt();
        for (int i = 0; i < qs; i++) {
            Question q = new Question();
            long ll = q.L *((long) Math.pow(2, h - q.i));
            long rr = (q.R + 1) * ((long) Math.pow(2, h - q.i)) - 1;
            //out.println(ll + " @ " + rr + " " + q.i);
            if (q.ans)  {
                remove(l, ll - 1);
                remove(rr + 1, r);
            } else {
                remove(ll, rr);
            }
//            for (Map.Entry<Long, Long> seg : segments.entrySet()) {
//                long start = seg.getKey(); long end = seg.getValue();
//                out.println(start + " " + end);
//            }
//            out.println();
            if (segments.size() == 0) {
                out.println("Game cheated!");
                return;
            }
        }
        if (segments.size() == 1) {
            for (Map.Entry<Long, Long> seg : segments.entrySet()) {
                long start = seg.getKey(); long end = seg.getValue();
                if (start == end) {
                    out.println(start);
                    return;
                }
            }
        }
        out.println("Data not sufficient!");
    }

    private static void remove(long ll, long rr) {
        // [ ] [   ] [    ]
        //            []
        //       [   ]
        // it can only change the segments that starts before or equal it ends
        Iterator<Map.Entry<Long, Long>> it = segments.headMap(rr, true).descendingMap().entrySet().iterator();
        ArrayList<long[]> toAdd = new ArrayList<long[]>();
        while (it.hasNext()) {
            Map.Entry<Long, Long> seg = it.next();
            //  start <= rr
            long start = seg.getKey(); long end = seg.getValue();
            // end < ll
            if (end < ll) {
                break;
            }
            // end >= ll

            // start >= ll
            if (start >= ll) {
                it.remove();
                if (end > rr) {
                    toAdd.add(new long[]{rr + 1, end});
                }
            } else {
                // start < ll
                toAdd.add(new long[]{start, ll - 1});
                if (end > rr) {
                    toAdd.add(new long[] {rr + 1, end});
                }
            }
        }
        for (long[] add : toAdd) segments.put(add[0], add[1]);
    }


    static TreeMap<Long, Long> segments = new TreeMap<Long, Long>();




    public static class Question {
        int i = cin.nextInt();
        long L = cin.nextLong();
        long R = cin.nextLong();
        boolean ans = cin.nextInt() == 1;

        public Question() throws IOException {
        }
    }


}
