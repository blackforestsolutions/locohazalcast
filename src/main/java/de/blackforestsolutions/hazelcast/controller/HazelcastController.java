package de.blackforestsolutions.hazelcast.controller;

import de.blackforestsolutions.hazelcast.service.HazelcastRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

import static de.blackforestsolutions.hazelcast.configuration.ConfigurationUtils.CONTROLLER_FAILURE_MESSAGE;
import static de.blackforestsolutions.hazelcast.configuration.ConfigurationUtils.CONTROLLER_SUCCESS_MESSAGE;

@RestController
@RequestMapping("/hazelcast")
public class HazelcastController {

    private final HazelcastRepositoryService hazelcastRepository;

    @Autowired
    public HazelcastController(HazelcastRepositoryService hazelcastRepository) {
        Objects.requireNonNull(hazelcastRepository, "hazelcastRepositoryService is required");
        this.hazelcastRepository = hazelcastRepository;
    }

    @PostMapping(value = "/write-data")
    public String writeDataToHazelcast(@RequestParam String key, @RequestParam String value) {
        boolean result = hazelcastRepository.isWriteDataToHazelcastSuccessfullyWith(key, value);
        if (result) {
            return CONTROLLER_SUCCESS_MESSAGE;
        }
        return CONTROLLER_FAILURE_MESSAGE;
    }

    @GetMapping(value = "/read-data")
    public String readDataFromHazelcast(@RequestParam String key) {
        return hazelcastRepository.readDataFromHazelcast(key);
    }

    @GetMapping(value = "/read-all-data")
    public Map<String, String> readAllDataFromHazelcast() {
        return hazelcastRepository.readAllDataFromHazelcast();
    }


}
