package ee.bitweb.transactions.domain.person.api;

import ee.bitweb.transactions.domain.person.common.Person;

public class PersonMapper {

    public static PersonResponse toResponse(Person person) {

        return new PersonResponse(
                person.getFirstName(),
                person.getLastName(),
                person.getPersonCode(),
                person.getWarrantIssued(),
                person.getHasContract(),
                person.getBlacklisted()
        );

    }
}
