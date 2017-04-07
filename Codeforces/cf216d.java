import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class cf216d {



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
            String TEST = "7 3 1 6 7 4 3 5 2 9 2 8 1 4 3 7 6 4 3 2 5 9 3 6 3 8 3 4 2 9";
            cin = new Reader(new ByteArrayInputStream(TEST.getBytes()));
        } else {
            cin = new Reader(System.in);
        }

        int n = cin.nextInt();
        int[][] bridges = new int[n][];
        for (int i = 0; i < bridges.length; i++) {
            bridges[i] = new int[cin.nextInt()];
            for (int j = 0; j < bridges[i].length; j++) bridges[i][j] = cin.nextInt();
            Arrays.sort(bridges[i]);
        }

        int unstableCount = 0;
        for (int is = 0; is < bridges.length; is++) {
            int[] bs = bridges[is];
            int[] left = bridges[is == 0 ? bridges.length - 1: is - 1];
            int[] right = bridges[is == bridges.length - 1 ? 0 : is + 1];
            for (int i = 0; i < bs.length - 1; i++) {
                int cellStart = bs[i];
                int cellEnd = bs[i + 1];
                int l = (Arrays.binarySearch(left, cellStart) - Arrays.binarySearch(left, cellEnd));
                int r = (Arrays.binarySearch(right, cellStart) - Arrays.binarySearch(right, cellEnd));
                if (l != r) unstableCount += 1;
            }
        }
        out.println(unstableCount);
    }
}
