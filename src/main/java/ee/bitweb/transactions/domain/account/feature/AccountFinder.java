package ee.bitweb.transactions.domain.account.feature;

import ee.bitweb.core.exception.persistence.Criteria;
import ee.bitweb.core.exception.persistence.EntityNotFoundException;
import ee.bitweb.transactions.domain.account.common.Account;
import ee.bitweb.transactions.domain.account.common.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountFinder {

    private final AccountRepository repository;

    @Transactional
    public Account getByNumber(String number) {
        log.info("Getting account by number {}", number);

        Account account = new Account();
        account.setNumber(number);

        return repository.findOne(Example.of(account)).orElseThrow(
                () -> new EntityNotFoundException("Not found", Account.class.getSimpleName(), Set.of(
                        new Criteria("number", number)
                ))
        );
    }
}
