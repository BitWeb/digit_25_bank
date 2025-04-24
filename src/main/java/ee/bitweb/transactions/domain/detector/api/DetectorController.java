package ee.bitweb.transactions.domain.detector.api;

import ee.bitweb.transactions.domain.detector.feature.DetectorCreator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/detectors")
public class DetectorController {

    private final DetectorCreator creator;

    @PostMapping
    public RegisterDetectorResponse register(@RequestBody @Valid RegisterDetectorPayload payload) {
        log.info("Registering new detector: {}", payload);

        return new RegisterDetectorResponse(
                creator.create(payload.getName()).getToken()
        );
    }
}
