package ee.bitweb.transactions.domain.account.api;

import ee.bitweb.transactions.domain.account.feature.AccountFinder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping ("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountFinder accountFinder;

    @GetMapping("/{number}")
    public AccountResponse get(@PathVariable String number) {
        log.info("Processing request to get account by number {}", number);

        return AccountMapper.toResponse(accountFinder.getByNumber(number));
    }
}
