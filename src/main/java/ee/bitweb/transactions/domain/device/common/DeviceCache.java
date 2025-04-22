package ee.bitweb.transactions.domain.device.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeviceCache {

    private Random random = new Random();
    private List<Device> devices = new ArrayList<>();
    private final DeviceRepository repository;

    public void load() {
        log.info("Loading devices");

        devices = repository.findAll();
    }

    public List<Device> getAll() {
        return devices;
    }

    public Device getRandom() {
        return devices.get(random.nextInt(devices.size()));
    }
}
