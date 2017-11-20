package ru.otus.hw05.tests;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

public class MyArrayList<T> implements List<T> {
    private Object[] elementData;
    private int size;
    private static final Object[] EMPTY_ELEMENTDATA = {};

    public MyArrayList() {
        this.elementData = EMPTY_ELEMENTDATA;
    }

    public MyArrayList(Collection<? extends T> c) {
        elementData = c.toArray();
        size = elementData.length;
        if (size != 0) {
            if (elementData.getClass() != Object[].class)
                elementData = Arrays.copyOf(elementData, size, Object[].class);
        } else {
            this.elementData = EMPTY_ELEMENTDATA;
        }
    }

    public ListIterator<T> iterator() {
        return new ListIterator<T>() {
            private int index = 0;
            public boolean hasNext() {
                return index < elementData.length;
            }
            public T next() { return (T) elementData[index++]; }

            @Override
            public boolean hasPrevious() {
                throw new UnsupportedOperationException();
            }

            @Override
            public T previous() {
                throw new UnsupportedOperationException();
            }

            @Override
            public int nextIndex() {
                throw new UnsupportedOperationException();
            }

            @Override
            public int previousIndex() {
                throw new UnsupportedOperationException();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void set(T t) {

            }

            @Override
            public void add(T t) {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public boolean add(T e) {
        elementData = Arrays.copyOf(elementData, ++size);
        elementData[size-1] = e;
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T get(int index) {
        return (T) elementData[index];
    }

    @Override
    public T set(int index, T element) {
        T oldValue = (T) elementData[index];
        elementData[index] = element;
        return oldValue;
    }

    @Override
    public Object[] toArray() {
        return elementData;
    }

    @Override
    public ListIterator<T> listIterator() {
        return iterator();
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++)
                if (elementData[i]==null)
                    return i;
        } else {
            for (int i = 0; i < size; i++)
                if (o.equals(elementData[i]))
                    return i;
        }
        return -1;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, T element) { throw new UnsupportedOperationException(); }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

}
