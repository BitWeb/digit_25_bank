package ee.bitweb.transactions.domain.device.feature;

import ee.bitweb.transactions.domain.device.common.Device;
import ee.bitweb.transactions.domain.device.common.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceCreator {

    private final DeviceRepository repository;

    public Device create(String mac, Boolean blacklisted) {
        log.info("Creating device with mac {}", mac);

        Device device = new Device();
        device.setMac(mac);
        device.setBlacklisted(blacklisted);

        return repository.save(device);
    }

    public void create(List<Device> devices) {
        log.info("Persisting {} devices", devices.size());

        repository.saveAll(devices);
    }
}
