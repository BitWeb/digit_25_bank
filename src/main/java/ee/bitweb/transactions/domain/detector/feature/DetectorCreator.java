package ee.bitweb.transactions.domain.detector.feature;

import ee.bitweb.core.exception.persistence.ConflictException;
import ee.bitweb.transactions.domain.detector.common.Detector;
import ee.bitweb.transactions.domain.detector.common.DetectorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DetectorCreator {

    private final DetectorRepository repository;
    private final DetectorFinder finder;

    public Detector create(String name) {
        log.info("Creating token {}", name);

        assertUnique(name);
        Detector detector = new Detector();
        detector.setName(name);
        detector.setToken(RandomStringUtils.random(32, true, true));

        return repository.save(detector);
    }

    private void assertUnique(String name) {
        if (finder.findByName(name) != null) {
            throw new ConflictException("Name already taken", Detector.class.getSimpleName(), "name", name);
        };
    }
}
