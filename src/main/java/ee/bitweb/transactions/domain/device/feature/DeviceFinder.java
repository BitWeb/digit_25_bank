package ee.bitweb.transactions.domain.device.feature;

import ee.bitweb.core.exception.persistence.Criteria;
import ee.bitweb.core.exception.persistence.EntityNotFoundException;
import ee.bitweb.transactions.domain.device.common.Device;
import ee.bitweb.transactions.domain.device.common.DeviceRepository;
import ee.bitweb.transactions.domain.device.common.DeviceSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceFinder {

    private final DeviceRepository repository;

    public Device findByMac(String mac) {
        log.info("Finding device by MAC {}", mac);

        Device device = new Device();
        device.setMac(mac);

        return repository.findOne(Example.of(device)).orElseThrow(
                () -> new EntityNotFoundException("Not found", Device.class.getSimpleName(), Set.of(
                        new Criteria("mac", mac)
                ))
        );
    }

    public List<Device> findByMacs(List<String> macs) {
        log.info("Finding devices by MACs {}", macs);

        return repository.findAll(DeviceSpecification.byMacIn(macs));
    }

    public Page<Device> findAll(int page, int size) {
        log.info("Getting page {} of size {} of Device ", page, size);

        return repository.findAll(PageRequest.of(page, size));
    }
}
