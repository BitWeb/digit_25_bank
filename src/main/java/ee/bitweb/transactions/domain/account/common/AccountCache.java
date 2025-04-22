package ee.bitweb.transactions.domain.account.common;

import ee.bitweb.transactions.domain.person.common.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountCache {

    private final AccountRepository accountRepository;
    private Random random = new Random();

    // Personcode, List of accounts
    HashMap<String, List<Account>> registry = new HashMap<>();

    public void load() {
        List<Account> accounts = accountRepository.findAll();
        for (Account account : accounts) {
            if (!registry.containsKey(account.getOwner())) {
                registry.put(account.getOwner(), new ArrayList<>());
            }

            registry.get(account.getOwner()).add(account);
        }
    }

    public Account getRandomForPerson(String personCode) {
        if (registry.containsKey(personCode)) {
            List<Account> accounts = registry.get(personCode);

            return accounts.get(random.nextInt(accounts.size()));
        }
        throw new RuntimeException("No account found for person " + personCode);
    }

    public Account getRandom() {
        String[] keys = registry.keySet().toArray(new String[0]);
        String randomKey = keys[random.nextInt(keys.length)];

        return getRandomForPerson(randomKey);
    }
}
