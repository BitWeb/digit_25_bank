package ee.bitweb.transactions.domain.account.feature;

import ee.bitweb.core.exception.persistence.Criteria;
import ee.bitweb.core.exception.persistence.EntityNotFoundException;
import ee.bitweb.transactions.domain.account.common.Account;
import ee.bitweb.transactions.domain.account.common.AccountRepository;
import ee.bitweb.transactions.domain.account.common.AccountSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountFinder {

    private final AccountRepository repository;

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

    public List<Account> getByNumbers(List<String> numbers) {
        log.info("Getting account by numbers {}", numbers);

        return repository.findAll(AccountSpecification.byNumberIn(numbers));
    }

    public Page<Account> findAll(int page, int size) {
        log.info("Getting page {} of size {} of Accounts ", page, size);

        return repository.findAll(PageRequest.of(page, size));
    }
}
