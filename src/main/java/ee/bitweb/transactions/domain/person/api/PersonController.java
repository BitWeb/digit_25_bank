package ee.bitweb.transactions.domain.person.api;

import ee.bitweb.transactions.common.RandomGenerator;
import ee.bitweb.transactions.domain.person.feature.PersonFinder;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/persons")
public class PersonController {

    private final PersonFinder finder;

    @GetMapping("/{personCode}")
    public PersonResponse get(@PathVariable("personCode") String personCode) throws InterruptedException {
        log.info("Processing request to get person with person code {}", personCode);
        Thread.sleep(RandomGenerator.nextLong(50, 100));

        return PersonMapper.toResponse(finder.getByPersonCode(personCode));
    }

    @PostMapping("/by-person-codes")
    public List<PersonResponse> byPersonCodes(@RequestBody List<String> personCodes) {
        log.info("Processing request to get persons by person codes {}", personCodes);

        return PersonMapper.toResponse(finder.getByPersonCodes(personCodes));
    }

    @GetMapping
    public List<PersonResponse> findAll(@RequestParam @Min(0) int  pageNumber, @RequestParam @Min(1) @Max(1000) int pageSize) {
        log.info("Processing request to get persons");

        return PersonMapper.toResponse(finder.findAll(pageNumber, pageSize));
    }
}
