package ee.bitweb.transactions.domain.person.api;

import ee.bitweb.transactions.domain.person.feature.PersonFinder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/persons")
public class PersonController {

    private final PersonFinder finder;

    @GetMapping("/{personCode}")
    public PersonResponse get(@PathVariable("personCode") String personCode) {
        log.info("Processing request to get person with person code {}", personCode);

        return PersonMapper.toResponse(finder.getByPersonCode(personCode));
    }
}
