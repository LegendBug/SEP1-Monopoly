package tools;

/**
 * This is a class of the Queue data structure, which is used in the Graph class
 */
public class Queue<Item> {
    private class Node {
        Item item;
        Node next;
    }

    private Node first;
    private Node last;
    private int size;

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) { // 向表尾添加元素
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }
        size++;
    }

    public Item dequeue() { // 从表头删除元素
        Item item = first.item;
        first = first.next;
        if (isEmpty()) {
            last = null;
        }
        size--;
        return item;
    }
}
