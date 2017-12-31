package ru.otus.L031;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

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

        List<Integer> fullList = generateList(10);
        fullList.add(2);
        fullList.add(33);
        assertThat(fullList.get(11), is(33));
    }

    @Test
    public void shouldAddOneElementToSpecifyPosition() {
    }

    @Test
    public void should_add_all_elements() {

        List<String> srcList = generateList(10);
        List<String> testList2 = generateList(20);

        srcList.addAll(testList2);

        assertEquals(srcList.size(), 30);

        List<String> actual = Arrays.asList("foo", "bar", "baz");
        assertThat(actual, hasItems("baz", "foo"));
        assertThat(srcList, hasItems("string_17"));
    }

    @Test
    public void addAll1() {
    }


    private <T> CustomList<T> generateList(int size) {

        CustomList<T> testList = new CustomList<>();
        for (int i = 0; i < size; i++) {
            testList.add((T) ("string_" + i));
        }

        return testList;
    }
}