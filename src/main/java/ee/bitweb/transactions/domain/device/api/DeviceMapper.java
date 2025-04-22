package ee.bitweb.transactions.domain.device.api;

import ee.bitweb.transactions.domain.device.common.Device;

public class DeviceMapper {

    public static DeviceResponse toResponse(Device device) {

        return new DeviceResponse(
                device.getMac(),
                device.getBlacklisted()
        );
    }
}
