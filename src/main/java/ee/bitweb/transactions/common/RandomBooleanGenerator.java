package ee.bitweb.transactions.common;

import java.util.Random;

public class RandomBooleanGenerator {

    private static final Random random = new Random();

    public static boolean nextBoolean(int trueProbability) {
        return trueProbability > random.nextInt(101);
    }
}
