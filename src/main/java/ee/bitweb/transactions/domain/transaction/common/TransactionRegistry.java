package ee.bitweb.transactions.domain.transaction.common;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionRegistry {

    private ConcurrentHashMap<Long, TransactionContext> registry = new ConcurrentHashMap<>();
    private final TransactionGenerator generator;
    private final MeterRegistry meterRegistry;

    public TransactionContext get(Long id, String name) {
        if (!registry.containsKey(id)) {
            registry.put(id, new TransactionContext(name, generator, meterRegistry));
        }

        return registry.get(id);
    }

    @Scheduled(fixedRate = 1000)
    private void cleanup() {
        for (TransactionContext context : registry.values()) {
            context.cleanup();
        }
    }
}
