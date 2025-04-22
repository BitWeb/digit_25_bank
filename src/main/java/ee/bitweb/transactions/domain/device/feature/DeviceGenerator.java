package ee.bitweb.transactions.domain.device.feature;

import ee.bitweb.transactions.common.RandomBooleanGenerator;
import ee.bitweb.transactions.domain.device.common.Device;
import ee.bitweb.transactions.domain.person.common.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceGenerator {

    private final PersonRepository personRepository;
    private final DeviceCreator creator;

    public void generate() {
        log.info("Generating devices ...");

        Long populationAmount = personRepository.count();
        Long deviceAmount = populationAmount * 3;

        List<Device> devices = new ArrayList<>();

        for (int i = 0; i < deviceAmount; i++) {
            devices.add(create());
        }

        creator.create(devices);
    }

    private Device create() {
        Device device = new Device();

        device.setBlacklisted(RandomBooleanGenerator.nextBoolean(10));
        device.setMac(UUID.randomUUID().toString());

        return device;
    }
}
