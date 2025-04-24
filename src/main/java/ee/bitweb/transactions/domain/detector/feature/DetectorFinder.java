package ee.bitweb.transactions.domain.detector.feature;

import ee.bitweb.transactions.domain.detector.common.Detector;
import ee.bitweb.transactions.domain.detector.common.DetectorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DetectorFinder {

    private final DetectorRepository repository;

    public Detector findByName(String name) {
        Detector detector = new Detector();
        detector.setName(name);

        return repository.findOne(Example.of(detector)).orElse(null);
    }

    public Detector findByToken(String token) {

        Detector detector = new Detector();
        detector.setToken(token);

        return repository.findOne(Example.of(detector)).orElse(null);
    }
}
