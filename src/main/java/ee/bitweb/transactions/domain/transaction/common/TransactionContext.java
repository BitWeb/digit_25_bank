package ee.bitweb.transactions.domain.transaction.common;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class TransactionContext {

    private ConcurrentHashMap<String, Transaction> transactions = new ConcurrentHashMap<>();
    private final TransactionGenerator generator;
    private final MeterRegistry meterRegistry;
    private Counter hitCounter;
    private Counter missCounter;
    private Counter lateCounter;


    public TransactionContext(String name, TransactionGenerator generator, MeterRegistry meterRegistry) {
        this.generator = generator;
        this.meterRegistry = meterRegistry;
        this.hitCounter = this.meterRegistry.counter("transactions.hit", "detector", name);
        this.missCounter = this.meterRegistry.counter("transactions.miss", "detector", name);
        this.lateCounter = this.meterRegistry.counter("transactions.late", "detector", name);
    }

    public List<Transaction> generate(int amount) {
        List<Transaction> transactions = generator.generate(amount);

        transactions.forEach(
                transaction -> {
                    this.transactions.put(transaction.getId(), transaction);
                }
        );

        return transactions;

    }

    public void verify(String id, Boolean valid) {
        if (transactions.containsKey(id)) {
            Transaction transaction = transactions.get(id);
            if (transaction.isLate()) {
                registerLate(transaction);
            }
            if (transaction.getValid() == valid) {
                registerHit(transaction);
            } else {
                registerMiss(transaction);
            }
        }
    }

    public void cleanup() {

        for (Transaction transaction : transactions.values()) {
            if (transaction.isLate()) {
                registerLate(transaction);
            }
        }
    }

    private void registerHit(Transaction transaction) {
        transactions.remove(transaction.getId());
        hitCounter.increment();
    }

    private void registerMiss(Transaction transaction) {
        transactions.remove(transaction.getId());
        missCounter.increment();
    }

    private void registerLate(Transaction transaction) {
        transactions.remove(transaction.getId());
        lateCounter.increment();
    }
}
