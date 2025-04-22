package ee.bitweb.transactions.domain.person.feature;

import ee.bitweb.transactions.domain.person.common.Person;
import ee.bitweb.transactions.domain.person.common.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonCreator {

    private final PersonRepository repository;

    @Transactional
    public List<Person> create(List<Person> personList) {
        log.info("Persisting persons in list (size:{})", personList.size());

        return repository.saveAll(personList);
    }
}
