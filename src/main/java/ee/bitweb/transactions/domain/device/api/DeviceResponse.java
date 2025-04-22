package ee.bitweb.transactions.domain.device.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeviceResponse {

    private String mac;
    private Boolean isBlacklisted;
}
