package track.lessons.lesson3;

import java.util.NoSuchElementException;

/**
 * Должен наследовать List
 *
 * Должен иметь 2 конструктора
 * - без аргументов - создает внутренний массив дефолтного размера на ваш выбор
 * - с аргументом - начальный размер массива
 */
public class MyArrayList extends List {

    private int[] list;
    private final int defaultCapacity = 10;
    private int size = 0;

    public MyArrayList() {
        list = new int[defaultCapacity];
    }

    public MyArrayList(int capacity) {
        if (capacity > 0) {
            list = new int[capacity];
        } else {
            list = new int[defaultCapacity];
        }
    }

    @Override
    public void add(int item) {
        if (size() < list.length) {
            list[size()] = item;
        } else {
            int[] tmp = new int[list.length + defaultCapacity];
            System.arraycopy(list, 0, tmp, 0, list.length);
            list = tmp;
            list[size()] = item;
        }
        size++;
    }

    @Override
    public int remove(int idx) throws NoSuchElementException {
        if (idx < size() && idx >= 0) {

            int[] tmp = new int[list.length - 1];
            System.arraycopy(list, 0, tmp, 0, idx);
            if (idx != list.length - 1) {
                System.arraycopy(list, idx + 1, tmp, idx, tmp.length - idx);
            }
            size--;
            int deleted = list[idx];
            list = tmp;
            return deleted;
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public int get(int idx) throws NoSuchElementException {
        if (idx < size() && idx >= 0) {
            return list[idx];
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public int size() {
        return size;
    }
}
