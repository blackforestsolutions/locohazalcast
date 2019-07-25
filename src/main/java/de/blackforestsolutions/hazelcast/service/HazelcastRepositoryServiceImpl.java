package de.blackforestsolutions.hazelcast.service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastJsonValue;
import de.blackforestsolutions.hazelcast.controller.HazelcastController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static de.blackforestsolutions.hazelcast.config.ConfigurationUtils.HAZELCAST_INSTANCE;
import static de.blackforestsolutions.hazelcast.config.ConfigurationUtils.JOURNEY_MAP;

@Service
public class HazelcastRepositoryServiceImpl implements HazelcastRepositoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HazelcastController.class);

    private Map<String, HazelcastJsonValue> hazelcastMap;

    @Autowired
    public HazelcastRepositoryServiceImpl(@Qualifier(HAZELCAST_INSTANCE) HazelcastInstance hazelcastInstance) {
        Objects.requireNonNull(hazelcastInstance, "HazelcastInstance is required");
        this.hazelcastMap = hazelcastInstance.getMap(JOURNEY_MAP);
    }

    @Override
    public void writeDataToHazelcast(String key, String value) {
        hazelcastMap.put(key, new HazelcastJsonValue(value));
        LOGGER.debug("Value was store in cache: {}", value);
    }

    @Override
    public void deleteDataToHazelcast(String key) {
        hazelcastMap.remove(key);
        LOGGER.debug("Value was removed from cache: {}", key);
    }


    @Override
    public String readDataFromHazelcast(String key) {
        String requestedValue = hazelcastMap.get(key).toString();
        LOGGER.debug("Value was retreived from cache: {}", requestedValue);
        return requestedValue;
    }

    @Override
    public Map<String, String> readAllDataFromHazelcast() {
        Map<String, String> allEntriesMap = transformedFrom(hazelcastMap);
        LOGGER.debug("All values retreived from cache: {}", allEntriesMap);
        return allEntriesMap;
    }

    private Map<String, String> transformedFrom(Map<String, HazelcastJsonValue> hazelcastJsonValueMap) {
        Map<String, String> transformedMap = new HashMap<>();
        for (Map.Entry<String, HazelcastJsonValue> entry : hazelcastJsonValueMap.entrySet()) {
            transformedMap.put(entry.getKey(), entry.getValue().toString());
        }
        return transformedMap;
    }
}
