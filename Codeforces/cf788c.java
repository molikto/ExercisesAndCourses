import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class cf788c {



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


    public static int gcd(int a, int b) {
        for (int i = Math.min(a, b); i >=1; i--) {
            if (a % i == 0 && b % i == 0) {
                return i;
            }
        }
        return 1;
    }

    public static void main(String args[]) throws Exception {
        // input
        if (args.length != 0 && "test".equals(args[0])) {
            DEBUG = true;
            String TEST = "999 2 1 1000";
            cin = new Reader(new ByteArrayInputStream(TEST.getBytes()));
        } else {
            cin = new Reader(System.in);
        }

        int N = cin.nextInt();
        int K = cin.nextInt();
        TreeSet<Integer> orig = new TreeSet<Integer>();
        if (N == 0) {
            for (int i = 0; i < K; i++) {
                int a = cin.nextInt();
                if (a == 0) {
                    out.print("1");
                    return;
                }
            }
            out.print("-1");
            return;
        }
        if (N == 1000) {
            for (int i = 0; i < K; i++) {
                int a = cin.nextInt();
                if (a == 1000) {
                    out.print("1");
                    return;
                }
            }
            out.print("-1");
            return;
        }
        for (int i = 0; i < K; i++) {
            int a = cin.nextInt() - N;
            if (a == 0) {
                out.print("1");
                return;
            }
            if (a < 1000 && a > -1000) {
                orig.add(a);
            }
        }
        int[] ks= new int[orig.size()];
        int iii = 0;
        for (int k : orig) ks[iii++] = k;
        Arrays.sort(ks);

        // sum (ni * ki)) = 0
        // find sum ni > 0 such above is true
        TreeMap<Integer, Integer> sols = new TreeMap<Integer, Integer>();
        int num = 1;
        for (int i = 0; i < ks.length; i++) {
            sols.put(ks[i], 1);
        }
        int k0 = Arrays.binarySearch(ks, 0);
        if (k0 >= 0) {
            out.println("1");
        } else {
            int j = -k0 - 1;
            if (j == 0 || j == ks.length) {
                out.println("-1");
            } else {
                int pMin = ks[j];
                int nMin = ks[j - 1];
                int minStep = Integer.MAX_VALUE;
                for (int i = 0; i < j; i++) {
                    for (int l = j; l < ks.length; l++) {
                        int p = ks[l];
                        int n = ks[i];
                        int gcd = gcd(p, -n);
                        int alt = p / gcd + (-n) / gcd;
                        if (minStep > alt) minStep = alt;
                    }
                }
                if (ks.length == 2) {
                    out.println(minStep);
                    return;
                }
                while (!sols.containsKey(0)) {
                    num += 1;
                    TreeSet<Integer> added = new TreeSet<Integer>();
                    for (int k : sols.keySet()) {
                        for (int a : ks) {
                            int r = k + a;
                            if ((r > 0 && r / (-nMin) <= minStep) || (r <= 0 && (-r) / pMin <= minStep)) {
                                if (!sols.containsKey(r)) {
                                    added.add(r);
                                }
                            }
                        }
                    }
                    for (int a: added) sols.put(a, num);
                }
                out.println(num);
            }
        }
    }
}
