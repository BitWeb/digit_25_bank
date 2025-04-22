package ee.bitweb.transactions;

import ee.bitweb.transactions.domain.account.common.AccountCache;
import ee.bitweb.transactions.domain.account.common.AccountRepository;
import ee.bitweb.transactions.domain.account.feature.AccountGenerator;
import ee.bitweb.transactions.domain.device.common.DeviceCache;
import ee.bitweb.transactions.domain.device.common.DeviceRepository;
import ee.bitweb.transactions.domain.device.feature.DeviceFinder;
import ee.bitweb.transactions.domain.device.feature.DeviceGenerator;
import ee.bitweb.transactions.domain.person.common.PersonCache;
import ee.bitweb.transactions.domain.person.feature.PersonFinder;
import ee.bitweb.transactions.domain.person.feature.PopulationGenerator;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataStateProcessor {

    private final PopulationGenerator populationGenerator;
    private final DeviceGenerator deviceGenerator;
    private final PersonFinder personFinder;
    private final DeviceRepository deviceRepository;
    private final AccountRepository accountRepository;
    private final AccountGenerator accountGenerator;

    private final PersonCache personCache;
    private final DeviceCache deviceCache;
    private final AccountCache accountCache;

    @PostConstruct
    public void init() {
        generateState();
        loadCaches();
    }

    private void generateState() {
        if (personFinder.count() ==  0) {
            populationGenerator.generate();
        }
        if (deviceRepository.count() == 0) {
            deviceGenerator.generate();
        }
        if (accountRepository.count() == 0) {
            accountGenerator.generate();
        }
    }

    private void loadCaches() {
        personCache.load();
        deviceCache.load();
        accountCache.load();
    }
}
