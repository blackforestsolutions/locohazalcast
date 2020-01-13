package de.blackforestsolutions.hazelcast.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hazelcast.core.HazelcastInstance;
import de.blackforestsolutions.datamodel.Journey;
import de.blackforestsolutions.datamodel.util.LocoJsonMapper;
import de.blackforestsolutions.hazelcast.controller.HazelcastController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static de.blackforestsolutions.hazelcast.config.ConfigurationUtils.HAZELCAST_INSTANCE;
import static de.blackforestsolutions.hazelcast.config.ConfigurationUtils.JOURNEY_MAP;

@Service
public class HazelcastRepositoryServiceImpl implements HazelcastRepositoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HazelcastController.class);

    private Map<String, Journey> hazelcastMap;

    private LocoJsonMapper locoJsonMapper;

    @Autowired
    public HazelcastRepositoryServiceImpl(@Qualifier(HAZELCAST_INSTANCE) HazelcastInstance hazelcastInstance) {
        Objects.requireNonNull(hazelcastInstance, "HazelcastInstance is required");
        this.hazelcastMap = hazelcastInstance.getMap(JOURNEY_MAP);
        this.locoJsonMapper = new LocoJsonMapper();
    }

    public HazelcastRepositoryServiceImpl setLocoJsonMapper(LocoJsonMapper locoJsonMapper) {
        this.locoJsonMapper = locoJsonMapper;
        return this;
    }

    @Override
    public void writeDataToHazelcast(String key, String value) {
        try {
            hazelcastMap.put(key, locoJsonMapper.mapJsonToJourney(value));
        } catch (IOException e) {
            LOGGER.error("Error during mapping: {}", e);
        }
        LOGGER.debug("Value was store in cache: {}", value);
    }

    @Override
    public void deleteDataToHazelcast(String key) {
        hazelcastMap.remove(key);
        LOGGER.debug("Value was removed from cache: {}", key);
    }


    @Override
    public String readDataFromHazelcast(String key) {
        String requestedValue = "";
        try {
            requestedValue = locoJsonMapper.map(hazelcastMap.get(key));
        } catch (JsonProcessingException e) {
            LOGGER.error("Error during mapping: {}", e);
        }
        LOGGER.debug("Value was retreived from cache: {}", requestedValue);
        return requestedValue;
    }

    @Override
    public Map<String, String> readAllDataFromHazelcast() {
        Map<String, String> allEntriesMap = transformedFrom(hazelcastMap);
        LOGGER.debug("All values retreived from cache: {}", allEntriesMap);
        return allEntriesMap;
    }

    private Map<String, String> transformedFrom(Map<String, Journey> hazelcastJsonValueMap) {
        Map<String, String> transformedMap = new HashMap<>();
        for (Map.Entry<String, Journey> entry : hazelcastJsonValueMap.entrySet()) {
            try {
                transformedMap.put(entry.getKey(), locoJsonMapper.map(entry.getValue()));
            } catch (JsonProcessingException e) {
                LOGGER.error("Error during mapping: {}", e);
            }
        }
        return transformedMap;
    }
}
