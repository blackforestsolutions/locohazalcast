package de.blackforestsolutions.hazelcast.service;

import java.util.Map;

public interface HazelcastRepositoryService {

    void writeDataToHazelcast(String key, String value);

    void deleteDataToHazelcast(String key);

    String readDataFromHazelcast(String key);

    Map<String, String> readAllDataFromHazelcast();
}
