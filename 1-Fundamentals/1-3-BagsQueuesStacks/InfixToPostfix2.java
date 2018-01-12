import java.util.Arrays;

/**
 * 2 * 3 / ( 2 - 1 ) + 3 * ( 4 - 1 )
 * ( ( 2 * 3 ) / ( 2 - 1 ) ) + ( 3 * ( 4 - 1 ) )
 * **/
public class InfixToPostfix2 {
    private static final String[] lo = {"+", "-"};
    private static final String[] hi = {"*", "/"};
    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();
        Queue<String> result = new Queue<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if ("+".equals(s) || "-".equals(s) || "*".equals(s) || "/".equals(s)) {
                if (stack.isEmpty()) stack.push(s);
                else {
                    while (!stack.isEmpty() && !isHigher(s, stack.peek())) {
                        result.enqueue(stack.pop());
                    }
                    stack.push(s);
                }
            } else if ("(".equals(s)) {
                stack.push(s);
            } else if (")".equals(s)) {
                while (!"(".equals(stack.peek()))
                    result.enqueue(stack.pop());
                stack.pop();
            } else
                result.enqueue(s);
        }
        while (!stack.isEmpty())
            result.enqueue(stack.pop());
        StdOut.println(result);
    }

    //s1 ”Î s2 ±»Ωœ
    private static boolean isHigher(String s1, String s2) {
        return "(".equals(s2) || (isHi(s1) && isLo(s2));
    }
    private static boolean isLo(String s) {
        return Arrays.asList(lo).contains(s);
    }
    private static boolean isHi(String s) {
        return Arrays.asList(hi).contains(s);
    }
}
