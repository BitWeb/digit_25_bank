package ee.bitweb.transactions.domain.device.api;

import ee.bitweb.transactions.domain.device.feature.DeviceFinder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceFinder finder;

    @GetMapping("/{mac}")
    public DeviceResponse get(@PathVariable("mac") String mac) {
        log.info("Processing request to get device by mac {}", mac);

        return DeviceMapper.toResponse(finder.findByMac(mac));
    }
}
