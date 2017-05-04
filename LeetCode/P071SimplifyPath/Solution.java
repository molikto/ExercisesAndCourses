import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

public class Solution {
    public String simplifyPath(String path) {
        String[] split = path.substring(1).split("/");
        ArrayList<String> stack = new ArrayList<String>();
        for (String s : split) {
            System.out.println(s);
            if (s != null && s.length() > 0 && !s.equals(".")) {
                if (s.equals("..")) {
                    if (!stack.isEmpty()) stack.remove(stack.size() - 1);
                } else {
                    stack.add(s);
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (String s : stack) {
            sb.append('/');
            sb.append(s);
        }
        return sb.length() == 0 ? "/" : sb.toString();
    }
}