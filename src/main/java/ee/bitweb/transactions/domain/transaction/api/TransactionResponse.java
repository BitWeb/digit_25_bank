package ee.bitweb.transactions.domain.transaction.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TransactionResponse {

    private String id;
    private BigDecimal amount;
    private String sender;
    private String recipient;
    private String senderAccount;
    private String recipientAccount;
    private String deviceMac;
    private LocalDateTime timestamp;
    private LocalDateTime deadline;
}
