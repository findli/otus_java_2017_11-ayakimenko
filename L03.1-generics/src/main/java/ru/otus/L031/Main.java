package ru.otus.L031;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Написать свою реализацию ArrayList на основе массива.
 * class MyArrayList<T> implements List<T>{...}
 * <p>
 * Проверить, что на ней работают методы
 * addAll(Collection<? super T> c, T... elements)
 * static <T> void copy(List<? super T> dest, List<? extends T> src)
 * static <T> void sort(List<T> list, Comparator<? super T> c)
 * из java.util.Collections
 */

public class Main {

    public static void main(String... args) {

        List<String> myArrayList = new CustomList<>();

        List<String> myStringList = new ArrayList<>();
        List<String> splittedString = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            splittedString.add("s:" + i);
            myStringList.add("mystr:" + i);
            myArrayList.add("custom:" + i);
        }

        myArrayList.addAll(myStringList);

        // Collections.sort();
        // Collections.copy();

        Collections.copy(myStringList, splittedString);
        System.out.println("Collections.copy() 'myStringList' values: " + myStringList);

        List<String> splittedStringErr = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            splittedStringErr.add("void");
        }
        Collections.copy(myStringList, splittedStringErr);
    }

   
}
