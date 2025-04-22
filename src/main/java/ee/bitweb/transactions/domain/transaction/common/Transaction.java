package ee.bitweb.transactions.domain.transaction.common;

import ee.bitweb.transactions.domain.account.common.Account;
import ee.bitweb.transactions.domain.device.common.Device;
import ee.bitweb.transactions.domain.person.common.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    private String id;
    private Person sender;
    private Account senderAccount;
    private Account recipientAccount;
    private Person recipient;
    private Device device;
    private BigDecimal amount;

    private LocalDateTime timestamp;
    private LocalDateTime due;
    private Boolean valid;

    public Boolean isLate() {
        return LocalDateTime.now().isAfter(due);
    }
}
