import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Solution {

    public static class Status {
        boolean[] used;
        int goal;

        @Override
        public int hashCode() {
            return Arrays.hashCode(used);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Status && Arrays.equals(used, ((Status) obj).used);
        }

        public static Status init(int maxChoosableInteger, int desiredTotal) {
            Status s = new Status();
            s.used = new boolean[maxChoosableInteger];
            s.goal = desiredTotal;
            return s;
        }

        public Status use(int i) {
            Status s = new Status();
            s.used = Arrays.copyOf(used, used.length);
            s.used[i] = true;
            s.goal = goal - i - 1;
            return s;
        }
    }

    HashMap<Status, Boolean> set = new HashMap<Status, Boolean>();

    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        // we have diff
        // if a >= diff win
        // if all pair >= diff lose
        // else there is some pair < diff
        //   not exists a such that all a b < diff  lose
        //   exists a, such that all a b < diff
        //   the other side will not win in next step
        int sum  = (maxChoosableInteger + 1) * maxChoosableInteger / 2;
        if (sum < desiredTotal) return false;
        Status s = Status.init(maxChoosableInteger, desiredTotal);
        return canIWin(s);
    }

    private boolean canIWin(Status s) {
        Boolean res = set.get(s);
        if (res == null) {
            for (int i = 0; i < s.used.length; i++) {
                if (!s.used[i] && i + 1 >= s.goal) {
                    res = true;
                    break;
                }
            }
            if (res == null) {
                for (int i = 0; i < s.used.length; i++) {
                    if (!s.used[i]) {
                        if (!canIWin(s.use(i))) {
                            res = true;
                            break;
                        }
                    }
                }
                if (res == null) res = false;
            }
            set.put(s, res);
        }
        return res;
    }
}