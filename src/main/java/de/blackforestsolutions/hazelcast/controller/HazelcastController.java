package de.blackforestsolutions.hazelcast.controller;

import com.hazelcast.core.HazelcastInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/hazelcast")
public class HazelcastController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HazelcastController.class);

    private final HazelcastInstance hazelcastInstance;

    @Autowired
    public HazelcastController(@Qualifier("hazelcastInstance") HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    @PostMapping(value = "/write-data")
    public String writeDataToHazelcast(@RequestParam String key, @RequestParam String value) {
        Map<String, String> hazelcastMap = hazelcastInstance.getMap("travel-data");
        hazelcastMap.put(key, value);
        return "Data was stored";
    }

    @GetMapping(value = "/read-data")
    public String readDataFromHazelcast(@RequestParam String key) {
        Map<String, String> hazelcastMap = hazelcastInstance.getMap("travel-data");
        return hazelcastMap.get(key);
    }

    @GetMapping(value = "/read-all-data")
    public Map<String, String> readAllDataFromHazelcast() {
        Map<String, String> hazelcastMap = hazelcastInstance.getMap("travel-data");
        return hazelcastMap;
    }


}
