import java.math.BigInteger;

public class Solution {

    public String nearestPalindromic(String n) {
        BigInteger nn = new BigInteger(n);
        String res;
        if (isPal(n)) {
            char c = round(n.charAt(n.length() / 2));
            if (n.length() % 2 == 0) {
                String k = n.substring(0, n.length() / 2 - 1);
                res = k + c + c + reverse(k);
            } else {
                String k = n.substring(0, n.length() / 2);
                res = k + c + reverse(k);
            }
        } else {
            if (n.length() % 2 == 0) {
                res = n.substring(0, n.length() / 2) + reverse(n.substring(0, n.length() / 2));
                BigInteger rr = new BigInteger(res);
                char margin = n.charAt(n.length() / 2 - 1);
                if (margin != '0') {
                    char mm = (char) (margin - 1);
                    String res1 = n.substring(0, n.length() / 2 - 1) + mm + mm + reverse(n.substring(0, n.length() / 2 - 1));
                    BigInteger rr1 = new BigInteger(res1);
                    rr = choose(nn, rr, rr1);
                }
                if (margin != '9') {
                    char mm = (char) (margin + 1);
                    String res1 = n.substring(0, n.length() / 2 - 1) + mm + mm + reverse(n.substring(0, n.length() / 2 - 1));
                    BigInteger rr1 = new BigInteger(res1);
                    rr = choose(nn, rr, rr1);
                }
                res = rr.toString();
            } else {
                res = n.substring(0, (n.length() + 1) / 2) + reverse(n.substring(0, n.length() / 2));
                BigInteger rr = new BigInteger(res);
                char margin = n.charAt(n.length() / 2);
                if (margin != '0') {
                    char mm = (char) (margin - 1);
                    String res1 = n.substring(0, n.length() / 2) + mm + reverse(n.substring(0, n.length() / 2));
                    BigInteger rr1 = new BigInteger(res1);
                    rr = choose(nn, rr, rr1);
                }
                if (margin != '9') {
                    char mm = (char) (margin + 1);
                    String res1 = n.substring(0, n.length() / 2) + mm + reverse(n.substring(0, n.length() / 2));
                    BigInteger rr1 = new BigInteger(res1);
                    rr = choose(nn, rr, rr1);
                }
                res = rr.toString();
            }
        }
        // 101101
        // 99999
        if (n.length() > 1) {
            BigInteger rr = new BigInteger(res);
            StringBuilder nine = new StringBuilder();
            for (int i = 0; i < n.length() - 1; i++) nine.append('9');
            BigInteger c = new BigInteger(nine.toString());
            StringBuilder jj = new StringBuilder();
            jj.append('1');
            for (int i = 0; i < n.length() - 1; i++) jj.append('0');
            jj.append('1');
            BigInteger k = new BigInteger(jj.toString());
            BigInteger rrr = choose(nn, rr, c);
            return choose(nn, k, rrr).toString();
        } else {
            return res;
        }
    }

    private BigInteger choose(BigInteger nn, BigInteger rr, BigInteger c) {
        int com =  nn.add(rr.negate()).abs().add(nn.add(c.negate()).abs().negate()).compareTo(BigInteger.ZERO);
        if (com > 0) {
            return c;
        } else if (com < 0) {
            return rr;
        } else {
            return rr.min(c);
        }
    }

    private char round(char c) {
        if (c == '0') return '1';
        else return (char) (c - 1);
    }

    private boolean isPal(String n) {
        for (int i = 0; i < n.length() / 2; i++) {
            if (n.charAt(i) != n.charAt(n.length() - 1 - i)) return false;
        }
        return true;
    }

    private String reverse(String substring) {
        StringBuilder sb = new StringBuilder();
        for (int i = substring.length() - 1; i >= 0; i--) sb.append(substring.charAt(i));
        return sb.toString();
    }
}