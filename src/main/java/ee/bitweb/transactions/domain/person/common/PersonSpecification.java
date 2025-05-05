package ee.bitweb.transactions.domain.person.common;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class PersonSpecification {

    public static Specification<Person> withPersonCodeIn(List<String> personCodes) {
        return (root, query, builder) -> root.get(Person_.personCode).in(personCodes);
    }
}
