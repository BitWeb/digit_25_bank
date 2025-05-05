package ee.bitweb.transactions.domain.account.api;

import ee.bitweb.transactions.domain.account.common.Account;
import org.springframework.data.domain.Page;

import java.util.List;

public class AccountMapper {

    public static AccountResponse toResponse(Account account) {
        return new AccountResponse(
                account.getNumber(),
                account.getOwner(),
                account.getClosed(),
                account.getBalance()
        );
    }

    public static List<AccountResponse> toResponse(List<Account> accounts) {
        return accounts.stream().map(AccountMapper::toResponse).toList();
    }

    public static List<AccountResponse> toResponse(Page<Account> accounts) {
        return accounts.stream().map(AccountMapper::toResponse).toList();
    }
}
