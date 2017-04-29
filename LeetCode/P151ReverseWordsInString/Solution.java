
import java.util.Stack;

public class Solution {


    public String reverseWords(String s) {
        Stack<String> words = new Stack<String>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') {
                if (sb.length() != 0) {
                    words.push(sb.toString());
                    sb.setLength(0);
                }
            } else {
                sb.append(c);
            }
        }
        if (sb.length() != 0) {
            words.push(sb.toString());
            sb.setLength(0);
        }
        while (!words.isEmpty()) sb.append(words.pop() + " ");
        return sb.toString().trim();
    }
}