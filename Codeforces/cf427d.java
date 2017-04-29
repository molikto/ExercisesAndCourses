import java.io.*;
import java.math.BigInteger;
import java.util.*;


// TIME OUT
public class cf427d {




    static boolean DEBUG = false;
    static BufferedReader cin;
    static PrintStream out = System.out;

    public static class SurTree {
        String str;
        int count1 = 0;
        int count2 = 0;
        HashMap<Character, SurTree> childs = new HashMap<Character, SurTree>();

        public static SurTree root() {
            return new SurTree();
        }

        public void add(String full, boolean is1, int lp) {
            add(full, 0, is1, lp);
        }
        public void add(String full, int pre, boolean is1, int lp) {
            //System.out.println("add " + full.substring(pre));
            //if (is1) count1 += 1; else count2 += 1;
            if (full.length() != pre) {
                // a | bcdeft...
                char c = full.charAt(pre);
                SurTree prev = childs.get(c);
                if (pre >= lp) {
                    if (prev == null) {
                        SurTree n = new SurTree();
                        n.str = full.substring(0, pre + 1);
                        prev = n;
                        childs.put(c, prev);
                    }
                    if (is1) prev.count1 += 1; else prev.count2 += 1;
                    prev.add(full, pre + 1, is1, lp);
                } else if (prev != null) {
                    if (prev.count1 > 0 && prev.count2 > 0) {
                        prev.add(full, pre + 1, is1, lp);
                    }
                }
            }
        }

        public int collect() {
            if (count2 == 1 && count1 == 1) {
                return 0;
            } else {
                int min = -1;
                for (SurTree c : childs.values()) {
                    int can = c.collect();
                    if (min == -1 || (can != -1 && can < min)) min = can;
                }
                return min == -1 ? -1 : min + 1;
            }
        }

        public boolean trim() {
            if (count1 == 0 || count2 == 0) {
                return false;
            }
            if (count1 == 1 && count2 == 1) {
                return true;
            }
            Iterator<Map.Entry<Character, SurTree>> it = childs.entrySet().iterator();
            boolean hasContent = false;
            while (it.hasNext()) {
                Map.Entry<Character, SurTree> n = it.next();
                boolean ret = n.getValue().trim();
                if (!ret) it.remove();
                hasContent |= ret;
            }
            return hasContent;
        }
    }

    public static void main(String args[]) throws Exception {
        // input
        if (args.length != 0 && "test".equals(args[0])) {
            DEBUG = true;
            String TEST = "aaaaaa\naaaaaa";
            cin = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(TEST.getBytes())));
        } else {
            cin = new BufferedReader(new InputStreamReader(System.in));
        }

        String s1 = cin.readLine();
        String s2 = cin.readLine();
        int max = Math.min(s1.length(), s2.length());
        int lp = -1;
        SurTree root = SurTree.root();
        for (int l = Math.min(4, max); l <= max;) {
            for (int i = 0; i < s1.length(); i++) {
                root.add(s1.substring(i, Math.min(i + l, s1.length())), true, lp);
            }
            for (int i = 0; i < s2.length(); i++) {
                root.add(s2.substring(i, Math.min(i + l, s2.length())), false, lp);
            }
            int res = root.collect();
            if (res != -1 || l == max) {
                out.println(root.collect());
                break;
            }
            root.trim();
            lp = l;
            l = Math.min(max, l * l);
        }

    }
}
