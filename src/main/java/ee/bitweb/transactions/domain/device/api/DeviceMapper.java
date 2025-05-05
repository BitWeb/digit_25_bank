package ee.bitweb.transactions.domain.device.api;

import ee.bitweb.transactions.domain.device.common.Device;
import org.springframework.data.domain.Page;

import java.util.List;

public class DeviceMapper {

    public static DeviceResponse toResponse(Device device) {

        return new DeviceResponse(
                device.getMac(),
                device.getBlacklisted()
        );
    }

    public static List<DeviceResponse> toResponse(List<Device> devices) {
        return devices.stream().map(DeviceMapper::toResponse).toList();
    }

    public static List<DeviceResponse> toResponse(Page<Device> devices) {
        return devices.map(DeviceMapper::toResponse).toList();
    }
}
