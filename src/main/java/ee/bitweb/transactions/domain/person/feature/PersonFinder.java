package ee.bitweb.transactions.domain.person.feature;

import ee.bitweb.core.exception.persistence.Criteria;
import ee.bitweb.core.exception.persistence.EntityNotFoundException;
import ee.bitweb.transactions.domain.person.common.Person;
import ee.bitweb.transactions.domain.person.common.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonFinder {

    private final PersonRepository repository;

    public Person getByPersonCode(String personCode) {
        log.info("Getting person by person code {}", personCode);

        Person person = new Person();
        person.setPersonCode(personCode);

        return repository.findOne(Example.of(person)).orElseThrow(
                () -> new EntityNotFoundException("Not found", Person.class.getSimpleName(), Set.of(
                        new Criteria("personCode", personCode)
                ))
        );
    }

    public List<Person> findAll() {
        log.info("Finding all persons");

        return repository.findAll();
    }

    public Long count() {
        log.info("Counting persons");
        return repository.count();
    }
}
