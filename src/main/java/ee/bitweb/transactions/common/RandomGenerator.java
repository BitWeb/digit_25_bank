package ee.bitweb.transactions.common;

import java.util.Random;

public class RandomGenerator {

    private static final Random random = new Random();

    public static long nextLong(long start, long end) {
        return random.nextLong(start, end);
    }
    public static boolean nextBoolean(int trueProbability) {
        return trueProbability > random.nextInt(101);
    }
}
