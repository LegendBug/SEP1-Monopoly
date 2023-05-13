package mdc.tools;

import java.util.Iterator;

/**
 * This is a class of the Bag data structure, which is used in the Graph class
 */
public class Bag<Item> implements Iterable<Item>{
    private class Node {
        Item item;
        Node next;
    }

    private Node first;

    public void add(Item item) { // 向栈顶添加元素
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}