public class E_1_3_09 {
    public static void main(String[] args) {
        Stack<String> ops = new Stack<>();
        Stack<String> vals = new Stack<>();

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if ("+".equals(s) || "-".equals(s) || "*".equals(s) || "/".equals(s))
                ops.push(s);
            else if (")".equals(s)) {
                String op = ops.pop();
                String val = vals.pop();
                vals.push(String.format("( %s %s %s )", vals.pop(), op, val));
            } else
                vals.push(s);
        }
        StdOut.println(vals.pop());
    }
}
