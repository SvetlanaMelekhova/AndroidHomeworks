package by.htp.first.homework2;

import java.util.ArrayList;

public class Operation {
    public static int sum(ArrayList<Integer> numbers) {
        int sum = 0;
        for (Integer i : numbers) sum += i;
        return sum;
    }

    public static double arithmeticMean(ArrayList<Integer> numbers) {
        double sum = 0;
        for (int i : numbers) sum += i;
        return sum / numbers.size();
    }

    public static double half(ArrayList<Integer> numbers) {
        int half = numbers.size() / 2;
        int sum = 0, difference = numbers.get(half);
        for (int i = 0; i < half; i++) sum += numbers.get(i);
        for (int i = half + 1; i < numbers.size(); i++) difference -= numbers.get(i);
        if (difference != 0) return (double) sum / difference;
        else return 0;
    }
}
