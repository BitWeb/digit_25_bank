package ee.bitweb.transactions.domain.transaction.common;


import ee.bitweb.transactions.domain.account.common.Account;
import ee.bitweb.transactions.domain.device.common.Device;
import ee.bitweb.transactions.domain.person.common.Person;

import java.math.BigDecimal;

public class TransactionValidator {

    public static boolean isValid(Transaction transaction) {
        return validatePerson(transaction.getSender()) &&
               validatePerson(transaction.getRecipient()) &&
                validateDevice(transaction.getDevice()) &&
                validateAccount(transaction.getSenderAccount()) &&
                validateAccount(transaction.getRecipientAccount()) &&
                validateOwnership(transaction.getSender(), transaction.getSenderAccount()) &&
                validateOwnership(transaction.getRecipient(), transaction.getRecipientAccount()) &&
                transaction.getAmount().compareTo(transaction.getSenderAccount().getBalance()) <= 0;
    }

    private static boolean validatePerson(Person person) {
        return !person.getWarrantIssued() && !person.getBlacklisted() && person.getHasContract();
    }

    private static boolean validateAccount(Account account) {
        return account != null && !account.getClosed();
    }

    private static boolean validateDevice(Device device) {
        return !device.getBlacklisted();
    }

    private static boolean validateOwnership(Person person, Account account) {
        return account.getOwner().equals(person.getPersonCode());
    }
}
