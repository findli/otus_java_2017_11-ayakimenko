package ru.otus.L031;


import java.util.*;

/**
 * Created by abyakimenko on 04.12.2017.
 */
public class CustomList<T> implements List<T> {

    private int size = 0;

    private static final int DEFAULT_SIZE = 10;

    private Object[] data;


    public CustomList() {
        data = new Object[DEFAULT_SIZE];
    }

    public CustomList(int initSize) {

        if (initSize > 0) {
            data = new Object[initSize];
        } else if (initSize == 0) {
            data = new Object[DEFAULT_SIZE];
        } else {
            throw new IllegalArgumentException("Wrong init size=" + initSize + "Size must be greater or equal zero.");
        }
    }


    // --------------------------------------

    @Override
    public boolean add(T element) {
        ensureCapacity(size + 1);
        data[size++] = element;
        return true;
    }

    @Override
    public void add(int index, Object element) {
        checkIndexRange(index);
    }

    private void checkIndexRange(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public boolean addAll(Collection collectionToAdd) {

        Object[] objectsToAdd = collectionToAdd.toArray();
        int collectionSize = objectsToAdd.length;
        ensureCapacity(size + collectionSize);
        System.arraycopy(collectionToAdd.toArray(), 0, data, size, collectionSize);
        size += collectionSize;
        return collectionSize != 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator iterator() {
        return new CustomIterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(data, size);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndexRange(index);
        return (T) data[index];
    }

    @Override
    public T remove(int index) {

        checkIndexRange(index);

        T removedElement = get(index);

        int movePos = size - index - 1;
        if (movePos > 0) {
            System.arraycopy(data, index + 1, data, index, movePos);
        }
        data[--size] = null;

        return removedElement;
    }

    // ----------------------------- NOT MPLEMENTED YET ---------------------------- //

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("remove");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("clear");
    }

    @Override
    public T set(int index, Object element) {
        throw new UnsupportedOperationException("set");
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException("indexOf");
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("lastIndexOf");
    }

    @Override
    public ListIterator listIterator() {
        throw new UnsupportedOperationException("listIterator empty");
    }

    @Override
    public ListIterator listIterator(int index) {
        throw new UnsupportedOperationException("listIterator with index");
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("subList");
    }

    @Override
    public boolean retainAll(Collection c) {
        throw new UnsupportedOperationException("retainAll");
    }

    @Override
    public boolean removeAll(Collection c) {
        throw new UnsupportedOperationException("removeAll");
    }

    @Override
    public boolean containsAll(Collection c) {
        throw new UnsupportedOperationException("containsAll");
    }

    @Override
    public boolean addAll(int index, Collection c) {
        throw new UnsupportedOperationException("addAll from position");
    }

    @Override
    public void sort(Comparator<? super T> collection) {
        throw new UnsupportedOperationException("sort");
    }
    
    // ----------------------------- NOT MPLEMENTED YET ---------------------------- //

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }

    private class CustomIterator implements Iterator<T> {

        // next element's index
        private int cursor;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public T next() {

            if (hasNext()) {
                return (T) CustomList.this.data[cursor++];
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            int removeIndex = cursor - 1;
            CustomList.this.remove(removeIndex);
        }
    }

    private void ensureCapacity(int minCapacity) {

        if (data.length < minCapacity) {
            // simple increase current capacity by 2
            int newCapacity = minCapacity + (minCapacity >> 1);
            data = Arrays.copyOf(data, newCapacity);
        }
    }
}
