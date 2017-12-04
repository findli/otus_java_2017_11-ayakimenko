package ru.otus.L021;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by abyakimenko on 29.11.2017.
 */
public class PrimitivesTest {

    public static void main(String[] args) {

        byte byteZ = 8;
        short shortZ = 16;
        char charZ = 16;
        int intZ = 32;
        long longZ = 129;
        float floatZ = 32;
        double doubleZ = 64;


        char t = charZ;
        short t1 = (short) charZ;

        byte b11 = (byte) longZ;

        int n = 5;
        String str = "Hello World";

        List<String> strSplitted = Arrays.asList(str.split(" "));
        Collections.reverse(strSplitted);
        System.out.println(strSplitted.stream().collect(Collectors.joining(" ")));

        int x = 2;
        int y = 12;

        //x = x * 3;
        y = x + y;//14
        x = y - x;//12
        y = y - x;

        System.out.println(x);
        System.out.println(y);
    }
}
