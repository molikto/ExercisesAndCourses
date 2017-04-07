
import java.io.*;
import java.util.*;

// NOT PASSED!!
public class cf312e {


    public cf312e() throws IOException {
    }

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
            String TEST = "8 3 0 1 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 0";
            cin = new Reader(new ByteArrayInputStream(TEST.getBytes()));
        } else {
            cin = new Reader(System.in);
        }
        n = cin.nextInt();
        k = cin.nextInt();
        us = new int[n][];
        for (int i = 0; i < n; i++) {
            us[i] = new int[n];
            for (int j = 0; j < n; j++) us[i][j] = cin.nextInt();
        }
        int maxBoatSize = n - k + 1;

        ns = new int[maxBoatSize + 1][];
        // boat size i, starts at something...
        for (int i = 1; i <= maxBoatSize; i++) {
            ns[i] = new int[n - i + 1];
            if (i > 1) {
                for (int j = 0; j < ns[i].length; j++) {
                    int u = ns[i - 1][j];
                    int can = j + i - 1;
                    for (int k = j; k < can; k++) {
                        u += us[k][can];
                    }
                    ns[i][j] = u;
                }
            }
        }
        // a sol with last boat is kth boat, and start at i
        HashMap<IntPair, Integer> sols = new HashMap<IntPair, Integer>();
        sols.put(new IntPair(0, 0), 0);
        for (int i = 1; i < n; i++) { // try add ith person into it
            // i = n - 2 => (n - 1)...
            int ramaining;
            if (n - i > maxBoatSize) {
                ramaining = Integer.MAX_VALUE / 2;
            } else {
                ramaining =  ns[n-i][i];
            }
//            System.out.println(n - i);
            //System.out.println(ramaining);
            int pmin = Integer.MAX_VALUE - ramaining - 10000;
            HashMap<IntPair, Integer> newSols = new HashMap<IntPair, Integer>();
            // forall old solutions
            for (Map.Entry<IntPair, Integer> entry : sols.entrySet()) {
                // try to create a new solution by opening a new boat
                int kk = entry.getKey().k + 1;
                if (kk < k) {
                    IntPair key = new IntPair(kk, i); // open new boat, wth pp at i
                    Integer min = newSols.get(key);
                    int vvv = entry.getValue();
                    if (min == null || min > vvv) { // the value of boat will not change
                        if (vvv <= pmin + ramaining) {
                            newSols.put(key, vvv);
                            if (kk < k - 1 && vvv < pmin) pmin = vvv;
                        }
                    }
                }
                // less boat but more people, we can fit a people in previous boat
                int boatLeft = k - entry.getKey().k - 1;
                if (boatLeft == 0 || boatLeft <= n - i - 1) {
                    IntPair key = entry.getKey();
                    // previous boat length, it contains pp [key.i, i)
                    int vvv = entry.getValue() - ns[i - key.i][key.i] + ns[i - key.i + 1][key.i];
                    if (vvv <= pmin + ramaining) {
                        newSols.put(key, vvv);
                        if (boatLeft > 0 && vvv < pmin) pmin = vvv;
                    }
                }
            }
            sols = newSols;
//            for (Map.Entry<IntPair, Integer> entry : sols.entrySet()) {
//                System.out.println(entry.getKey().i +" " + entry.getKey().k + " " + entry.getValue());
//            }
//            System.out.println();;
        }
        int min = Integer.MAX_VALUE;
        for (int m : sols.values()) if (m < min) min = m;
        out.println(min);
    }

    public static class IntPair {
        int k;
        int i;

        public IntPair(int i, int i1) {
            this.k = i;
            this.i = i1;
        }

        @Override
        public int hashCode() {
            return k * 131 + i;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof IntPair) {
                IntPair o = (IntPair) obj;
                if (k == o.k && i == o.i) {
                    return true;
                }
            }
            return false;
        }
    }

    //

    static int n;
    static int k;
    static int[][] us;

    static int[][] ns;

}
