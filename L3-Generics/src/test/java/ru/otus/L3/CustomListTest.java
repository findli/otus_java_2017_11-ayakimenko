package ru.otus.L3;

import org.junit.Test;

import java.util.*;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.*;

/**
 * Created by abyakimenko on 17.12.2017.
 */
public class CustomListTest {

    @Test
    public void shouldAddOneElementToTheEnd() {

        List<Integer> testList1 = new CustomList<>();
        assertThat(testList1, empty());

        testList1.add(1);
        assertThat(testList1, not(empty()));

        testList1.add(2);
        assertThat(testList1, hasSize(2));
        assertThat(testList1.get(1), is(2));

        List<Integer> fullList = generateIntList(10);
        fullList.add(2);
        fullList.add(33);
        assertThat(fullList.get(11), is(33));
    }

    @Test
    public void shouldAddOneElementToPosition() {
    }

    @Test
    public void shouldAddAllElements() {

        List<String> srcList = generateList(10);
        List<String> testList2 = generateList(20);

        srcList.addAll(testList2);
        assertEquals(30, srcList.size());

        Collections.addAll(srcList, "testList2", "testList33");
        assertEquals(32, srcList.size());


        assertThat(srcList, hasItems("string_1", "string_11", "string_17", "string_12", "string_19", "testList33"));
    }

    @Test
    public void shouldRemoveByIndex() {

        List<String> srcList = generateList(10);
        assertThat(srcList.get(7), equalTo("string_7"));

        Iterator<String> iterator = srcList.iterator();
        iterator.next();
        iterator.next();
        iterator.next();
        iterator.remove();

        assertThat(srcList.get(1), equalTo("string_1"));
        assertThat(srcList.get(2), equalTo("string_3"));
        assertThat(srcList.get(8), equalTo("string_9"));
    }

    @Test
    public void shouldRemoveByRef() {

        List<String> srcList = generateList(5);
        srcList.remove("string_2");
        assertThat(srcList.get(2), equalTo("string_3"));
        srcList.remove("string_3");
        assertThat(srcList.get(2), equalTo("string_4"));
    }

    @Test
    public void shouldContainObject() {

        List<Integer> integerList = generateIntList(9);
        assertTrue(integerList.contains(4));
        assertTrue(integerList.contains(8));
        assertTrue(integerList.contains(7));
    }

    @Test
    public void shouldNotContainObject() {

        List<Integer> integerList = generateIntList(9);
        assertFalse(integerList.contains(-4));
        assertFalse(integerList.contains(18));
        assertFalse(integerList.contains(17));
    }

    @Test
    public void shouldCopyTargetToSource() {

        List<Integer> srcList = generateIntList(9);
        List<Integer> targetList = new CustomList<>();
        IntStream.range(0, 15).forEach(x -> targetList.add(1));

        Collections.copy(targetList, srcList);

        assertThat(targetList.size(), equalTo(15));
        assertThat(targetList, hasItems(1, 2, 3, 4, 5, 8));
    }

    @Test
    public void shouldSortDescending() {

        List<Integer> targetList = new CustomList<>();
        new Random().ints(0, 15).limit(10).forEach(targetList::add);
        Collections.sort(targetList, (x1, x2) -> x2 - x1);
        assertTrue(targetList.get(0) >= targetList.get(9));
    }

    @Test
    public void shouldSortAscending() {

        List<Integer> targetList = new CustomList<>();
        new Random().ints(0, 15).limit(10).forEach(targetList::add);
        Collections.sort(targetList, Comparator.comparingInt(x -> x));
        assertTrue(targetList.get(0) <= targetList.get(9));
    }

    @Test
    public void shouldClearList() {
        List<Integer> srcList = generateIntList(9);
        srcList.clear();
        assertTrue(srcList.isEmpty());
    }


    private CustomList<Integer> generateIntList(int size) {
        CustomList<Integer> testList = new CustomList<>();
        for (int i = 0; i < size; i++) {
            testList.add(i);
        }
        return testList;
    }

    private CustomList<String> generateList(int size) {

        CustomList<String> testList = new CustomList<>();
        for (int i = 0; i < size; i++) {
            testList.add("string_" + i);
        }
        return testList;
    }
}