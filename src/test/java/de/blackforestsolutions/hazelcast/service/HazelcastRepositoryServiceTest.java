package de.blackforestsolutions.hazelcast.service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.test.TestHazelcastInstanceFactory;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HazelcastRepositoryServiceTest {

    private TestHazelcastInstanceFactory factory = new TestHazelcastInstanceFactory(1);

    private HazelcastInstance hazelcastMock = factory.newHazelcastInstance();

    private HazelcastRepositoryService classUnderTest = new HazelcastRepositoryServiceImpl(hazelcastMock);

    @Test
    public void test_writeDataToHazelcast_add_one_entry_to_map_and_read_this_entry() {
        String testKey = "testKey";
        String testValue = "testValue";

        classUnderTest.writeDataToHazelcast(testKey, testValue);

        assertThat(classUnderTest.readDataFromHazelcast(testKey)).isEqualTo(testValue);
        classUnderTest.deleteDataToHazelcast(testKey);
    }

    @Test
    public void test_writeDataToHazelcast_add_one_entry_to_map_and_read_this_entry_delete_it_and_check_it_is_gone() {
        String testKey = "testKey";
        String testValue = "testValue";
        classUnderTest.writeDataToHazelcast(testKey, testValue);
        assertThat(classUnderTest.readDataFromHazelcast(testKey)).isEqualTo(testValue);

        classUnderTest.deleteDataToHazelcast(testKey);

        assertThat(classUnderTest.readAllDataFromHazelcast().size()).isEqualTo(0);
    }


    @Test
    public void test_readAllDataFromHazelcast_add_one_entry_to_map_and_read_this_entry() {
        String testKey = "testKey";
        String testValue = "testValue";
        classUnderTest.writeDataToHazelcast(testKey, testValue);

        assertThat(classUnderTest.readAllDataFromHazelcast().get(testKey)).isEqualTo(testValue);
        classUnderTest.deleteDataToHazelcast(testKey);
    }

}
