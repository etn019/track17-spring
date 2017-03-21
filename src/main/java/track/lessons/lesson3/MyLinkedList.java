package track.lessons.lesson3;

import java.util.NoSuchElementException;

/**
 * Должен наследовать List
 * Односвязный список
 */
public class MyLinkedList extends List implements Stack, Queue {


    /**
     * private - используется для сокрытия этого класса от других.
     * Класс доступен только изнутри того, где он объявлен
     * <p>
     * static - позволяет использовать Node без создания экземпляра внешнего класса
     */
    private static class Node {
        Node prev;
        Node next;
        int val;

        Node(Node prev, Node next, int val) {
            this.prev = prev;
            this.next = next;
            this.val = val;
        }
    }

    private int size;
    private Node head;

    @Override
    public void add(int item) {
        if (size() == 0) {
            head = new Node(null, null, item);
        } else {
            Node current = head;
            for (int i = 0; i < size() - 1; i++) {
                current = current.next;
            }
            current.next = new Node(current, null, item);
        }
        size++;
    }

    @Override
    public int remove(int idx) throws NoSuchElementException {
        if (idx < size() && idx >= 0) {
            Node current = head;
            for (int i = 0; i < idx; i++) {
                current = current.next;
            }
            if ((current.next != null) && (current.prev != null)) {
                current.prev.next = current.next;
                current.next.prev = current.prev;
            } else if (current.prev != null) {
                current.prev.next = null;
            } else if (current.next != null) {
                current.next.prev = null;
                head = current.next;
            } else {
                head = null;
            }
            size--;
            return current.val;
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public int get(int idx) throws NoSuchElementException {
        if (idx < size() && idx >= 0) {
            Node current = head;
            for (int i = 0; i < idx; i++) {
                current = current.next;
            }
            return current.val;
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void push(int value) {
        add(value);
    }

    @Override
    public int pop() {
        return remove(size() - 1);
    }

    @Override
    public void enqueue(int value) {
        add(value);
    }

    @Override
    public int dequeu() {
        return remove(0);
    }

}
