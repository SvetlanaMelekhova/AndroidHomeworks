package by.htp.first.homework2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class NumberGenerator {
    final private Random random = new Random();

    public ArrayList<Integer> generateNumbers() {

        HashSet<Integer> busyNumbers = new HashSet<>();
        int length;
        do {
            length = random.nextInt(190) + 10;
        } while (length % 2 != 0);

        while (busyNumbers.size() < length) {
            busyNumbers.add(random.nextInt(1000));
        }
        return new ArrayList<>(busyNumbers);
    }
}
