package ee.bitweb.transactions.domain.transaction.common;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class TransactionContext {

    public static int LIMIT = 10000;
    private ConcurrentHashMap<String, Transaction.Summary> transactions = new ConcurrentHashMap<>();
    private final TransactionGenerator generator;
    private final MeterRegistry meterRegistry;
    private Counter hitCounter;
    private Counter missCounter;
    private Counter lateCounter;
    private Gauge commitmentGauge;


    public TransactionContext(String name, TransactionGenerator generator, MeterRegistry meterRegistry) {
        this.generator = generator;
        this.meterRegistry = meterRegistry;
        this.hitCounter = this.meterRegistry.counter("transactions.hit", "detector", name);
        this.missCounter = this.meterRegistry.counter("transactions.miss", "detector", name);
        this.lateCounter = this.meterRegistry.counter("transactions.late", "detector", name);
        this.commitmentGauge = Gauge.builder("transactions.commitment", transactions, ConcurrentHashMap::size).tags("detector", name).register(this.meterRegistry);
    }

    public List<Transaction> generate(int amount) {
        int actualAmount = Math.min(LIMIT - transactions.size(), amount);
        List<Transaction> transactions = generator.generate(actualAmount);

        transactions.forEach(
                transaction -> {
                    this.transactions.put(transaction.getId(), transaction.toSummary());
                }
        );

        return transactions;
    }

    public void verify(String id, boolean valid) {
        if (transactions.containsKey(id)) {
            Transaction.Summary summary = transactions.get(id);
            if (summary.isLate()) {
                registerLate(id);
            }
            if (summary.isValid() == valid) {
                registerHit(id);
            } else {
                registerMiss(id);
            }
        }
    }

    public void cleanup() {

        for (Map.Entry<String, Transaction.Summary> element : transactions.entrySet()) {
            if (element.getValue().isLate()) {
                registerLate(element.getKey());
            }
        }
    }

    private void registerHit(String id) {
        transactions.remove(id);
        hitCounter.increment();
    }

    private void registerMiss(String id) {
        transactions.remove(id);
        missCounter.increment();
    }

    private void registerLate(String id) {
        transactions.remove(id);
        lateCounter.increment();
    }
}
