package ee.bitweb.transactions.domain.account.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {

    private String number;
    private String owner;
    private Boolean closed;
    private BigDecimal balance;
}
