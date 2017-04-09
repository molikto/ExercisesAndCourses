
import javax.management.Query;
import java.io.*;
import java.util.*;

// 15:32
public class BKTree {



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
            String TEST = "test";
            cin = new Reader(new ByteArrayInputStream(TEST.getBytes()));
        } else {
            cin = new Reader(System.in);
        }
        String[] strs = new String[] {"books", "cake", "boo", "cape", "cart", "boon", "cook"};
        Node root = new Node("book");
        for (String str : strs) root.add(str);

        for (String str : strs) root.search(str, 4);
    }


    public static class Node {
        public Node(String s) {this.str = s;}
        String str;
        HashMap<Integer, Node> childs = new HashMap<Integer, Node>();

        public void add(String str) {
            int dis = distance(str, str);
            Node c = childs.get(dis);
            if (c == null) {
                childs.put(dis, new Node(str));
            } else {
                c.add(str);
            }
        }

        // dis(s, str) + dis(str, k) >= dis(s, k)
        public void search(String s, int i) {
            int dis = distance(str, s);
            if (dis <= i) {
                System.out.println(s + " " + str + " " + dis);
                if (dis != 0) {
                    int min = Math.max(dis - i, 0);
                    int max = dis + i;
                    for (int k = min; k < max; k++) {
                        Node n = childs.get(k);
                        if (n != null) {
                            n.search(s, i);
                        }
                    }
                }
            }
        }
    }

    static int [][] dp; // dp(i, j) = distance (s1[0, i), s2[0, j)
    private static int distance(String s1, String s2) {
        dp = new int[s1.length() + 1][];
        for (int i = 0; i <= s1.length(); i++) dp[i] = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) dp[i][0] = i;
        for (int i = 0; i <= s2.length(); i++) dp[0][i] = i;
        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                int diff = 1;
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    diff = 0;
                }
                dp[i][j] = Math.min(Math.min(dp[i - 1][j - 1] + diff, dp[i - 1][j] + 1), dp[i][j - 1] + 1);
            }
        }
        return dp[s1.length()][s2.length()];
    }
}
