
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Solution {

    public static boolean constant(String s) {
        return !s.equals("*") && ! s.equals("?");
    }
    public static void main(String[] args) {
        System.out.println(new Solution().isMatch("mississippi", "m*iss*iss*"));
        //System.out.println(new Solution().isMatch("abbbaaababbaaabaaabbbabbbbaaabbaaababaabbbbbbaababbabababbababaaabbbbbabababaababaaaaaaabbbaabaabbbaabbabaababbabaababbbabbaaabbbaaaababbaaabbaabaabbbbbaaababaabaabaaabbabaabbbabbbaabbababaabbbbbbbbaaa", "*ba***bba*b**abbaa***a*****b*a*bb*b***a*bbb***a***bba*****a****a*a*b**aaaba*aab*a*aa***a*a*b**b**a*b*"));
    }
    public boolean isMatch(String s, List<String> p) {
        ///System.out.println(s);
        if (p.isEmpty()) {
            return s.isEmpty();
        } else if (s.isEmpty()) {
            return regMatch(s, p);
        } else if (p.size() == 1) {
            if (p.get(0).equals("*")) {
                return true;
            } else if (p.get(0).equals("?")) {
                return s.length() == 1;
            } else {
                return p.get(0).equals(s);
            }
        } else if (constant(p.get(0))) {
            if (s.startsWith(p.get(0))) {
                return isMatch(s.substring(p.get(0).length()), p.subList(1, p.size()));
            } else {
                return false;
            }
        } else if (p.get(0).equals("?")) {
            return isMatch(s.substring(1), p.subList(1, p.size()));
        } else if (constant(p.get(p.size() - 1))) {
            if (s.endsWith(p.get(p.size() - 1))) {
                return isMatch(s.substring(0, s.length() - p.get(p.size() - 1).length()), p.subList(0, p.size() - 1));
            } else {
                return false;
            }
        } else if (p.get(p.size() - 1).equals("?")) {
            return isMatch(s.substring(0, s.length() - 1), p.subList(0, p.size() - 1));
        } else {
            if (p.size() < 5) {
                return regMatch(s, p);
            } else {
                int[] matches = new int[p.size()];
                int min = 8;
                int mini = 0;
                for (int i = 0; i < p.size(); i++) {
                    //System.out.println(p.get(i));
                    if (constant(p.get(i))) {
                        matches[i] = countMax5(s, p.get(i));
                        if (matches[i] == 0) {
                            return false;
                        }  else if (min > matches[i]) {
                            min = matches[i];
                            mini = i;
                        }
                    } else {
                        matches[i] = -1;
                    }
                }
                //System.out.println(min);
                String cons = p.get(mini);
                boolean matched = false;
                int index = s.indexOf(cons);
                while (!matched && index >= 0) {
                    matched = isMatch(s.substring(0, index), p.subList(0, mini)) &&
                            isMatch(s.substring(index + cons.length()), p.subList(mini + 1, p.size()));
                    if (!matched) {
                        index = s.indexOf(cons, index + 1);
                    }
                }
                return matched;
            }
        }
    }

    private int countMax5(String s, String s1) {
        int count = 0;
        while (count < 5) {
            int index = s.indexOf(s1);
            if (index >= 0) {
                count += 1;
                s = s.substring(index + 1);
            } else {
                break;
            }
        }
        return count;
    }

    public boolean isMatch(String s, String p) {
        ArrayList<String> pp = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < p.length(); i++) {
            switch (p.charAt(i)) {
                case '?':
                    if (sb.length() > 0) {
                        pp.add(sb.toString());
                        sb.setLength(0);
                    }
                    int k = pp.size();
                    while (k > 0 && pp.get(k - 1).equals("*")) {
                        k--;
                    }
                    pp.add(k, "?");
                    break;
                case '*':
                    if (sb.length() > 0) {
                        pp.add(sb.toString());
                        sb.setLength(0);
                    }
                    if (pp.size() == 0 || !pp.get(pp.size() - 1).equals("*")) pp.add("*");
                    break;
                default:
                    sb.append(p.charAt(i));
            }
        }
        if (sb.length() > 0) {
            pp.add(sb.toString());
            sb.setLength(0);
        }
        return isMatch(s, pp);
    }
    public static boolean regMatch(String s, List<String> pp) {
        StringBuilder sb = new StringBuilder();
        for (String p : pp) {
            if (p.equals("*")) {
                sb.append(".*");
            } else if (p.equals("?")) {
                sb.append(".");
            } else {
                sb.append(p);
            }
        }
        Pattern pat = Pattern.compile(sb.toString());
        return pat.matcher(s).matches();
    }
}