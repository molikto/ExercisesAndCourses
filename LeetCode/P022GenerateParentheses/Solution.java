import java.util.ArrayList;
import java.util.List;

public class Solution {
    
    public List<String> generateParenthesis(int n) {
        List<List<String>> res = new ArrayList<List<String>>();
        res.add(new ArrayList<String>()); // n = 0
        res.get(0).add("");
        for (int i = 1; i <= n; i++) {
            ArrayList<String> r = new ArrayList<String>();
            for (int j = 0; j <= i - 1; j++) { // left part
                int l = i - j - 1;
                List<String> left = res.get(l);
                List<String> right = res.get(j);
                for (String ll : left) {
                    for (String rr : right) {
                        r.add(ll + "(" + rr + ")");
                    }
                }
            }
            res.add(r);
        }
        return res.get(n);
    }
}