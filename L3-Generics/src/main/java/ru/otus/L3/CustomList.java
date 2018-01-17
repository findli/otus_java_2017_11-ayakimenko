package ru.otus.L3;


import java.util.*;
import java.util.function.Predicate;

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

    @Override
    public boolean add(T element) {
        ensureCapacity(size + 1);
        data[size++] = element;
        return true;
    }

    @Override
    public void add(int index, Object element) {
        checkIndexRange(index);
        ensureCapacity(size + 1);
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = element;
        ++size;
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
    public Iterator iterator() {
        return new CustomListIterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(data, size);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndexRange(index);
        return getElementByIndex(index);
    }

    @Override
    public T remove(int index) {

        checkIndexRange(index);
        T removedElement = get(index);
        removeInternal(index);
        return removedElement;
    }

    @Override
    public boolean remove(Object object) {

        if (object == null) {
            return removeIteration(Objects::isNull);
        } else {
            return removeIteration(object::equals);
        }
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }

    @Override
    public boolean contains(Object object) {
        return indexOf(object) > -1;
    }

    @Override
    public int indexOf(Object object) {

        if (object == null) {
            for (int i = 0; i < size; i++) {
                if (data[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (object.equals(data[i])) {
                    return i;
                }
            }
        }

        return -1;
    }

    @Override
    public T set(int index, Object element) {

        checkIndexRange(index);
        T oldElement = getElementByIndex(index);
        data[index] = element;

        return oldElement;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ListIterator listIterator() {
        return new CustomListIterator(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ListIterator listIterator(int index) {
        return new CustomListIterator(index);
    }

    @Override
    public int lastIndexOf(Object object) {

        if (object == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (data[i] == null) {
                    return i;
                }
            }
        } else {

            for (int i = size - 1; i >= 0; i--) {
                if (object.equals(data[i])) {
                    return i;
                }
            }
        }

        return -1;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super T> comparator) {
        Arrays.sort((T[]) data, 0, size, comparator);
    }


    // ----------------------------- NOT MPLEMENTED YET ---------------------------- //

    @Override
    public void clear() {
        for (int i = size - 1; i >= 0; i--) {
            data[i] = null;
        }
        size = 0;
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("subList");
    }

    @Override
    public boolean retainAll(Collection collection) {
        throw new UnsupportedOperationException("retainAll");
    }

    @Override
    public boolean removeAll(Collection collection) {
        throw new UnsupportedOperationException("removeAll");
    }

    @Override
    public boolean containsAll(Collection collection) {
        throw new UnsupportedOperationException("containsAll");
    }

    @Override
    public boolean addAll(int index, Collection collection) {
        throw new UnsupportedOperationException("addAll from position");
    }

    // ----------------------------- NOT MPLEMENTED YET ---------------------------- //


    // PRIVATE MEMBERS

    private class CustomListIterator implements ListIterator<T> {

        // next element's index
        int cursor;

        public CustomListIterator() {
        }

        CustomListIterator(int index) {
            cursor = index;
        }

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        @SuppressWarnings("unchecked")
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

        @Override
        public boolean hasPrevious() {
            return cursor > 0;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T previous() {

            int i = cursor - 1;
            if (i < 0) {
                throw new NoSuchElementException();
            }

            return (T) CustomList.this.data[i];
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void set(T element) {
            CustomList.this.set(cursor, element);
        }

        @Override
        public void add(T element) {
            int i = cursor;
            CustomList.this.add(i, element);
            cursor = i + 1;
        }
    }

    private void ensureCapacity(int minCapacity) {

        if (data.length < minCapacity) {
            // simple increase current capacity by 2
            int newCapacity = minCapacity + (minCapacity >> 1);
            data = Arrays.copyOf(data, newCapacity);
        }
    }

    @SuppressWarnings("unchecked")
    private boolean removeIteration(Predicate predicate) {
        for (int i = 0; i < size; i++) {
            if (predicate.test(data[i])) {
                removeInternal(i);
                return true;
            }
        }
        return false;
    }

    private void removeInternal(int index) {

        int movePos = size - index - 1;
        if (movePos > 0) {
            System.arraycopy(data, index + 1, data, index, movePos);
        }
        data[--size] = null;
    }

    @SuppressWarnings("unchecked")
    private T getElementByIndex(int index) {
        return (T) data[index];
    }
}
