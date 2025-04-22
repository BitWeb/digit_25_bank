package ee.bitweb.transactions.domain.account.api;

import ee.bitweb.transactions.domain.account.common.Account;

public class AccountMapper {

    public static AccountResponse toResponse(Account account) {
        return new AccountResponse(
                account.getNumber(),
                account.getOwner(),
                account.getClosed(),
                account.getBalance()
        );
    }
}
