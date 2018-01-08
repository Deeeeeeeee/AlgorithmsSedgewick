import java.util.NoSuchElementException;

/**
 * Created by sealde on 12/4/17.
 */
public class Steque_2<Item> {
    private int N;
    private Node first;
    private Node last;

    private class Node {
        private Node next;
        private Item item;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    /** 从队尾添加元素 **/
    public void enqueue(Item item) {
        Node n = new Node();
        n.item = item;
        if (isEmpty()) {
            first = n;
            last = n;
        } else {
            last.next = n;
            last = n;
        }
        N++;
    }

    /** 从队头删除元素 **/
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = first.item;
        first = first.next;
        if (isEmpty()) last = null;
        N--;
        return item;
    }

    /** 从队头添加元素，相当于从栈顶入栈 **/
    public void push(Item item) {
        Node n = new Node();
        n.item = item;
        if (isEmpty()) {
            first = n;
            last = n;
        } else {
            n.next = first;
            first = n;
        }
        N++;
    }

    /** 从队头删除元素，相当于从栈顶出栈 **/
    public Item pop() {
        return dequeue();
    }

    /**
     * to be or not to - be - - that - - - is
     * @param args
     */
    public static void main(String[] args) {
        Steque_2<String> steque = new Steque_2<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-"))
                steque.push(item);
            else if (!steque.isEmpty())
                StdOut.print(steque.pop() + "  ");
        }
        StdOut.println("(" + steque.size() + " left on queue)");
    }
}
