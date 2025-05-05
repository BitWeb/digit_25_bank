package ee.bitweb.transactions.domain.device.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.NONE)
public class DeviceSpecification {

    public static Specification<Device> byMacIn(List<String> mac) {
        return (root, query, builder) -> root.get(Device_.mac).in(mac);
    }
}
