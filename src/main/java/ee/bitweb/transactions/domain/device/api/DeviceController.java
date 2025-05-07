package ee.bitweb.transactions.domain.device.api;

import ee.bitweb.transactions.common.RandomGenerator;
import ee.bitweb.transactions.domain.device.common.Device;
import ee.bitweb.transactions.domain.device.feature.DeviceFinder;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceFinder finder;

    @GetMapping("/{mac}")
    public DeviceResponse get(@PathVariable("mac") String mac) throws InterruptedException {
        log.info("Processing request to get device by mac {}", mac);
        Thread.sleep(RandomGenerator.nextLong(50, 100));

        return DeviceMapper.toResponse(finder.findByMac(mac));
    }

    @PostMapping("/by-macs")
    public List<DeviceResponse> getByMacs(@RequestBody List<String> macs) {
        log.info("Processing request to get device by macs {}", macs);

        return DeviceMapper.toResponse(finder.findByMacs(macs));
    }

    @GetMapping
    public List<DeviceResponse> get(@RequestParam @Min(0) int  pageNumber, @RequestParam @Min(1) @Max(1000) int pageSize) {
        log.info("Processing request to get devices page {} of size {}", pageNumber, pageSize);

        return DeviceMapper.toResponse(finder.findAll(pageNumber, pageSize));
    }
}
