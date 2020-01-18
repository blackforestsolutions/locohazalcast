package de.blackforestsolutions.hazelcast.service;

import java.util.Map;

public interface HazelcastRepositoryService {

    boolean isWriteDataToHazelcastSuccessfullyWith(String key, String value);

    void deleteDataToHazelcast(String key);

    String readDataFromHazelcast(String key);

    Map<String, String> readAllDataFromHazelcast();
}
