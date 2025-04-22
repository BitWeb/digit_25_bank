package ee.bitweb.transactions.domain.person.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class PersonCache {

    private Random random = new Random();
    private List<Person> persons = new ArrayList<>();
    private final PersonRepository repository;

    public void load() {
        log.info("Loading persons");

        persons = repository.findAll();
    }

    public List<Person> getAll() {
        return persons;
    }

    public Person getRandom() {
        return persons.get(random.nextInt(persons.size()));
    }
}
