import java.util.ArrayList;

public class Solution {
    public String fractionToDecimal(int nn, int dd) {
        long numerator = nn;
        long denominator = dd;
        long k = numerator % denominator;
        if (k == 0) {
            return Long.toString(numerator / denominator);
        } else {
            StringBuilder sb = new StringBuilder();
            if (numerator < 0 && denominator < 0) {
                numerator = -numerator;
                denominator = -denominator;
            } else if (numerator < 0) {
                numerator = -numerator;
                sb.append('-');
            } else if (denominator < 0) {
                denominator = -denominator;
                sb.append('-');
            }
            k = numerator % denominator;
            sb.append(numerator / denominator);
            sb.append('.');
            StringBuilder frac = new StringBuilder();
            int rep = -1;
            ArrayList<Long> fs = new ArrayList<Long>();
            while (rep < 0) {
                k = k * 10;
                /*
                1
                6
                6
                */
                if (k % denominator == 0) {
                    frac.append(k / denominator);
                    break;
                } else {
                    for (int i = fs.size() - 1; i >= 0; i--) {
                        if (k % denominator == fs.get(i) && frac.charAt(i) == '0' + k /denominator ) {
                            rep = fs.size() - i;
                            break;
                        }
                    }
                    if (rep < 0) {
                        frac.append(k / denominator);
                        k = k % denominator;
                        fs.add(k);
                    }
                }
            }
            String ff = frac.toString();
            if (rep >= 0) {
                sb.append(ff.substring(0, ff.length() - rep));
                sb.append("(");
                sb.append(ff.substring(ff.length() - rep, ff.length()));
                sb.append(")");
            } else {
                sb.append(ff);
            }
            return sb.toString();
        }
    }
}