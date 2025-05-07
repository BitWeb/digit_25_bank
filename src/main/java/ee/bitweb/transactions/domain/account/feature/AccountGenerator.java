package ee.bitweb.transactions.domain.account.feature;

import ee.bitweb.transactions.common.RandomGenerator;
import ee.bitweb.transactions.domain.account.common.Account;
import ee.bitweb.transactions.domain.person.common.Person;
import ee.bitweb.transactions.domain.person.common.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountGenerator {

    private final PersonRepository personRepository;
    private final AccountCreator accountCreator;

    public void generate() {
        Random random = new Random();
        List<Person> persons = personRepository.findAll();

        List<Account> accounts = new ArrayList<>();

        for (Person person : persons) {
            for (int i = 0; i < random.nextInt(2) + 1; i++) {
                Account account = new Account();
                account.setOwner(person.getPersonCode());
                account.setClosed(RandomGenerator.nextBoolean(10));
                account.setBalance(BigDecimal.valueOf(random.nextInt(100000)).setScale(2, RoundingMode.HALF_UP));
                account.setNumber(UUID.randomUUID().toString());

                accounts.add(account);
            }
        }

        accountCreator.create(accounts);
    }
}
