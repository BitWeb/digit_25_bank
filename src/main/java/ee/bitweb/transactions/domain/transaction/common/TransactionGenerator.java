package ee.bitweb.transactions.domain.transaction.common;

import ee.bitweb.transactions.common.RandomBooleanGenerator;
import ee.bitweb.transactions.domain.account.common.Account;
import ee.bitweb.transactions.domain.account.common.AccountCache;
import ee.bitweb.transactions.domain.device.common.Device;
import ee.bitweb.transactions.domain.device.common.DeviceCache;
import ee.bitweb.transactions.domain.person.common.Person;
import ee.bitweb.transactions.domain.person.common.PersonCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionGenerator {

    private final PersonCache personCache;
    private final DeviceCache deviceCache;
    private final AccountCache accountCache;
    Random random = new Random();

    public List<Transaction> generate(int amount) {

        List<Transaction> transactions = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            Person sender = personCache.getRandom();
            Person receiver = personCache.getRandom();
            Device device = deviceCache.getRandom();

            Transaction transaction = new Transaction();
            transaction.setId(UUID.randomUUID().toString());
            transaction.setSender(sender);
            transaction.setRecipient(receiver);
            transaction.setDevice(device);
            transaction.setSenderAccount(generateAccount(sender));
            transaction.setRecipientAccount(generateAccount(receiver));
            transaction.setAmount(BigDecimal.valueOf(random.nextDouble()).setScale(2, RoundingMode.HALF_UP));
            transaction.setValid(TransactionValidator.isValid(transaction));
            transaction.setTimestamp(LocalDateTime.now());
            transaction.setDue(LocalDateTime.now().plusSeconds(1+ random.nextInt(4)));

            transactions.add(transaction);
        }

        return transactions;
    }

    public Account generateAccount(Person owner) {
        if (RandomBooleanGenerator.nextBoolean(5)) {
            return accountCache.getRandom();
        } else {
            return accountCache.getRandomForPerson(owner.getPersonCode());
        }
    }


}
