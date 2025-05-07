package ee.bitweb.transactions.domain.transaction.api;

import ee.bitweb.transactions.common.RandomGenerator;
import ee.bitweb.transactions.config.security.DetectorSecurityHelper;
import ee.bitweb.transactions.domain.transaction.common.TransactionContext;
import ee.bitweb.transactions.domain.transaction.common.TransactionRegistry;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionRegistry registry;

    @GetMapping("/unverified")
    public List<TransactionResponse> get(@RequestParam @Min(1) @Max(1000) Integer amount) {
        log.info("Getting {} unverified transactions for {} ", amount, DetectorSecurityHelper.getName());

        return TransactionMapper.toResponse(
                registry.get(DetectorSecurityHelper.getId(), DetectorSecurityHelper.getName()).generate(amount)
        );
    }

    @PostMapping("/{id}/verify")
    public void verify(@PathVariable String id) throws InterruptedException {

        registry.get(DetectorSecurityHelper.getId(), DetectorSecurityHelper.getName()).verify(id, true);
        Thread.sleep(RandomGenerator.nextLong(50, 100));
    }

    @PostMapping("/{id}/reject")
    public void reject(@PathVariable String id) throws InterruptedException {
        registry.get(DetectorSecurityHelper.getId(), DetectorSecurityHelper.getName()).verify(id, false);
        Thread.sleep(RandomGenerator.nextLong(50, 100));
    }

    @PostMapping("/verify")
    public void verify(@RequestBody List<String> ids) {
        TransactionContext c = registry.get(DetectorSecurityHelper.getId(), DetectorSecurityHelper.getName());

        for (String id : ids) {
            c.verify(id, true);
        }
    }

    @PostMapping("/reject")
    public void reject(@RequestBody List<String> ids) {
        TransactionContext c =registry.get(DetectorSecurityHelper.getId(), DetectorSecurityHelper.getName());

        for (String id : ids) {
            c.verify(id, false);
        }
    }
}
