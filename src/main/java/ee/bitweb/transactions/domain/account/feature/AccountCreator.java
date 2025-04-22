package ee.bitweb.transactions.domain.account.feature;

import ee.bitweb.transactions.domain.account.common.Account;
import ee.bitweb.transactions.domain.account.common.AccountRepository;
import ee.bitweb.transactions.domain.device.common.Device;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountCreator {

    private final AccountRepository repository;

    public void create(List<Account> accounts) {
        log.info("Persisting {} accounts", accounts.size());

        repository.saveAll(accounts);
    }
}
