package ee.bitweb.transactions.domain.person.api;

import ee.bitweb.transactions.domain.person.common.Person;
import org.springframework.data.domain.Page;
import java.util.List;

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

    public static List<PersonResponse> toResponse(List<Person> persons) {
        return persons.stream().map(PersonMapper::toResponse).toList();
    }

    public static List<PersonResponse> toResponse(Page<Person> persons) {
        return persons.stream().map(PersonMapper::toResponse).toList();
    }
}
