package ee.bitweb.transactions.common;

import org.apache.commons.lang3.RandomStringUtils;

public class PersonCodeGenerator {

    public static Integer PERSON_CODE_LENGTH = 11;

    public static String generate() {
        return RandomStringUtils.random(PERSON_CODE_LENGTH, false, true);
    }
}
