package ee.bitweb.transactions.domain.transaction.api;

import ee.bitweb.transactions.domain.transaction.common.Transaction;

import java.util.List;

public class TransactionMapper {

    public static List<TransactionResponse> toResponse(List<Transaction> transactions) {
        return transactions.stream().map(TransactionMapper::toResponse).toList();
    }

    public static TransactionResponse toResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getSender().getPersonCode(),
                transaction.getRecipient().getPersonCode(),
                transaction.getSenderAccount().getNumber(),
                transaction.getRecipientAccount().getNumber(),
                transaction.getDevice().getMac(),
                transaction.getTimestamp(),
                transaction.getDue()
        );
    }
}
