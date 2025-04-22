package ee.bitweb.transactions.domain.detector.api;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@ToString
public class RegisterDetectorPayload {

    @NotBlank
    private String name;
}
