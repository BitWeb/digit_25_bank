package ee.bitweb.transactions.domain.transaction.common;

import ee.bitweb.transactions.domain.account.common.Account;
import ee.bitweb.transactions.domain.device.common.Device;
import ee.bitweb.transactions.domain.person.common.Person;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
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

    @Getter
    @AllArgsConstructor
    class Summary {
        private LocalDateTime due;
        private boolean valid;

        public boolean isLate() {
            return LocalDateTime.now().isAfter(due);
        }
    }

    public Summary  toSummary() {
        return new Summary(due, valid);
    }
}
