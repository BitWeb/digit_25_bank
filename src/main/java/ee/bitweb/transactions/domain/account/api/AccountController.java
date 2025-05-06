package ee.bitweb.transactions.domain.account.api;

import ee.bitweb.transactions.domain.account.feature.AccountFinder;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/by-numbers")
    public List<AccountResponse> get(@RequestBody List<String> numbers) {
        log.info("Processing request to get accounts by numbers {}", numbers);

        return AccountMapper.toResponse(accountFinder.getByNumbers(numbers));
    }

    @GetMapping
    public List<AccountResponse> get(@RequestParam @Min(0) int  pageNumber, @RequestParam @Min(1) @Max(1000) int pageSize) {
        log.info("Processing request to get accounts");

        return AccountMapper.toResponse(accountFinder.findAll(pageNumber, pageSize));
    }
}
