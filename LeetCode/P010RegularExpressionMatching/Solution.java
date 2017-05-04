public class Solution {


    public boolean isMatch(String s, String p) {
        boolean[][] match = new boolean[s.length() + 1][];
        for (int i = 0; i < match.length; i++) {
            match[i] = new boolean[p.length() + 1];
        }
        for (int i = 0; i < match.length; i++) {
            for (int j = 0; j < match[0].length; j++) {
                boolean res;
                if (i == 0 && j == 0) {
                    res = true;
                } else if (i != 0 && j == 0) {
                    res = false;
                } else {
                    char pp = p.charAt(j - 1);
                    if (pp == '.') {
                        res = (i > 0 && match[i - 1][j - 1]);
                    } else if (pp == '*') {
                        // addddfasdfa
                        // a.*
                        // bc
                        // .*
                        char ppp = p.charAt(j - 2);
                        if (ppp == '*') {
                            res = match[i][j - 1];
                        } else {
                            // bbb
                            // b*
                            if (match[i][j - 2]) {
                                res = true;
                            } else if (match[i][j - 1]) {
                                res = true;
                            } else {
                                if (ppp == '.') {
                                    res = i > 1 && match[i - 1][j];
                                } else {
                                    res = i > 1 && match[i - 1][j] && s.charAt(i - 1) == ppp;
                                }
                            }
                        }
                    } else {
                        res = (i > 0 && match[i - 1][j - 1] && s.charAt(i - 1) == pp);
                    }
                }
                match[i][j] = res;
            }
        }
        return match[s.length()][p.length()];
    }
}