package ee.bitweb.transactions.domain.person.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonResponse {

    private String firstName;
    private String lastName;
    private String personCode;
    private Boolean warrantIssued;
    private Boolean hasContract;
    private Boolean blacklisted;
}
