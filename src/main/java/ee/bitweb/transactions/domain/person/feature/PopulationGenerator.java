package ee.bitweb.transactions.domain.person.feature;

import ee.bitweb.transactions.common.PersonCodeGenerator;
import ee.bitweb.transactions.common.RandomGenerator;
import ee.bitweb.transactions.domain.person.common.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PopulationGenerator {

    private final PersonCreator creator;
    private final ResourceLoader resourceLoader;

    public void generate() {
        log.info("Generating population...");
        HashSet<String> personCodes = new HashSet<>();

        try {
            List<String> lines = getLines(resourceLoader.getResource("classpath:person_names.csv"));
            List<Person> persons = new ArrayList<>();

            for (String line : lines) {
                Person person = convert(line);
                person.setPersonCode(generateAndStore(personCodes));
                person.setWarrantIssued(RandomGenerator.nextBoolean(5));
                person.setBlacklisted(RandomGenerator.nextBoolean(5));
                person.setHasContract(RandomGenerator.nextBoolean(95));

                persons.add(person);
            }

            creator.create(persons);
        } catch (Exception e) {
            log.error("Person generation failed with error: ", e);
        }
    }

    private List<String> getLines(Resource resource) throws IOException {
        String contents = resource.getContentAsString(StandardCharsets.UTF_8);

        return Arrays.asList(contents.split("\n"));
    }

    private Person convert(String line) {
        String[] values = line.split(","); // Split by comma
        String firstName = values[0].trim();
        String lastName = values[1].trim();

        Person person = new Person();

        person.setFirstName(firstName);
        person.setLastName(lastName);

        return person;
    }

    private String generateAndStore(HashSet<String> personCodes) {
        String personCode = PersonCodeGenerator.generate();

        while (personCodes.contains(personCode)) {
            personCode = PersonCodeGenerator.generate();
        }

        personCodes.add(personCode);

        return personCode;
    }

}
