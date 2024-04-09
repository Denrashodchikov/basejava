package ru.javawebinar.basejava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainStream {

    public static int minValue(int[] values) {
        return Arrays.stream(values).sorted().distinct().reduce(0, (a, b) -> 10 * a + b);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().mapToInt(i -> i).sum();
        return integers.stream().filter(n -> n % 2 != sum % 2).toList();
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 3, 2, 3};
        int[] b = {9, 8};
        System.out.println(minValue(a));
        System.out.println(minValue(b));
        System.out.println("-----------");
        List<Integer> ints = new ArrayList<>();
        ints.add(1);
        ints.add(2);
        ints.add(3);
        ints.add(4);
        ints.add(5);
        //ints.add(1);
        oddOrEven(ints).forEach(System.out::println);
    }
}
