package de.blackforestsolutions.hazelcast.controller;

import de.blackforestsolutions.hazelcast.service.HazelcastRepositoryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Map;

import static de.blackforestsolutions.hazelcast.configuration.ConfigurationUtils.CONTROLLER_FAILURE_MESSAGE;
import static de.blackforestsolutions.hazelcast.configuration.ConfigurationUtils.CONTROLLER_SUCCESS_MESSAGE;
import static org.mockito.Mockito.*;

public class HazelcastControllerTest {

    private final HazelcastRepositoryService hazelcastRepositoryServiceMock = mock(HazelcastRepositoryService.class);

    private final HazelcastController classUnderTest = new HazelcastController(hazelcastRepositoryServiceMock);

    @Test
    public void test_writeDataToHazelcast_is_successful_returns_CONTROLLER_SUCCESS_MESSAGE() {
        String testKey = "testKey";
        String testValue = "testKey";
        when(hazelcastRepositoryServiceMock.isWriteDataToHazelcastSuccessfullyWith(testKey, testValue)).thenReturn(true);

        String result = classUnderTest.writeDataToHazelcast(testKey, testValue);

        Assertions.assertThat(result).isEqualTo(CONTROLLER_SUCCESS_MESSAGE);
        verify(hazelcastRepositoryServiceMock, Mockito.times(1)).isWriteDataToHazelcastSuccessfullyWith(testKey, testValue);
    }

    @Test
    public void test_writeDataToHazelcast_is_unSuccessful_returns_CONTROLLER_FAILURE_MESSAGE() {
        String testKey = "testKey";
        String testValue = "testKey";
        when(hazelcastRepositoryServiceMock.isWriteDataToHazelcastSuccessfullyWith(testKey, testValue)).thenReturn(false);

        String result = classUnderTest.writeDataToHazelcast(testKey, testValue);

        Assertions.assertThat(result).isEqualTo(CONTROLLER_FAILURE_MESSAGE);
        verify(hazelcastRepositoryServiceMock, Mockito.times(1)).isWriteDataToHazelcastSuccessfullyWith(testKey, testValue);
    }


    @Test
    public void test_readDataFromHazelcast_is_successful_returns_String() {
        String testKey = "testKey";
        String response = "testPositive";
        when(hazelcastRepositoryServiceMock.readDataFromHazelcast(testKey)).thenReturn(response);

        String result = classUnderTest.readDataFromHazelcast(testKey);

        Assertions.assertThat(result).isEqualTo(response);
        verify(hazelcastRepositoryServiceMock, Mockito.times(1)).readDataFromHazelcast(testKey);
    }

    @Test
    public void test_readAllDataFromHazelcast_is_successful_returns_Map() {
        Map<String, String> testStub = Collections.singletonMap("key", "value");

        when(hazelcastRepositoryServiceMock.readAllDataFromHazelcast()).thenReturn(testStub);

        Map<String, String> result = classUnderTest.readAllDataFromHazelcast();

        Assertions.assertThat(result).isEqualTo(testStub);
        verify(hazelcastRepositoryServiceMock, Mockito.times(1)).readAllDataFromHazelcast();
    }
}
