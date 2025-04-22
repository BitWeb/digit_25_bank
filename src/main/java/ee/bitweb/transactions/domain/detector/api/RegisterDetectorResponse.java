package ee.bitweb.transactions.domain.detector.api;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDetectorResponse {

    @ToString.Exclude
    private String token;
}
