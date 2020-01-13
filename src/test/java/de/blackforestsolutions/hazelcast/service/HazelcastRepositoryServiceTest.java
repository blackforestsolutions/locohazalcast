package de.blackforestsolutions.hazelcast.service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.test.TestHazelcastInstanceFactory;
import de.blackforestsolutions.datamodel.Journey;
import de.blackforestsolutions.datamodel.util.LocoJsonMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static de.blackforestsolutions.hazelcast.service.objectmothers.JourneyObjectMother.getJourneyBerlinHamburg;
import static org.assertj.core.api.Assertions.assertThat;

public class HazelcastRepositoryServiceTest {

    private TestHazelcastInstanceFactory factory = new TestHazelcastInstanceFactory(1);

    private HazelcastInstance hazelcastMock = factory.newHazelcastInstance();

    private HazelcastRepositoryService classUnderTest = new HazelcastRepositoryServiceImpl(hazelcastMock);

    Journey testJourney = getJourneyBerlinHamburg();

    LocoJsonMapper locoJsonMapper = new LocoJsonMapper();

    @Test
    public void test_writeDataToHazelcast_add_one_entry_to_map_and_read_this_entry() throws IOException {
        String testValue = locoJsonMapper.map(testJourney);
        String testKey = testJourney.getId().toString();
        classUnderTest.writeDataToHazelcast(testKey, testValue);

        String result = classUnderTest.readDataFromHazelcast(testKey);
        Journey resultObject = locoJsonMapper.mapJsonToJourney(result);

        assertThat(resultObject).isEqualToIgnoringGivenFields(testJourney, "start", "destination", "betweenHolds", "price", "priceWithCommision");
        assertThat(classUnderTest.readAllDataFromHazelcast().size()).isEqualTo(1);
        classUnderTest.deleteDataToHazelcast(testKey);
    }

    @Test
    public void test_writeDataToHazelcast_add_one_entry_to_map_and_read_this_entry_delete_it_and_check_it_is_gone() throws IOException {
        String testValue = locoJsonMapper.map(testJourney);
        String testKey = testJourney.getId().toString();
        classUnderTest.writeDataToHazelcast(testKey, testValue);

        String result = classUnderTest.readDataFromHazelcast(testKey);
        Journey resultObject = locoJsonMapper.mapJsonToJourney(result);

        assertThat(resultObject).isEqualToIgnoringGivenFields(testJourney, "start", "destination", "betweenHolds", "price", "priceWithCommision");
        classUnderTest.deleteDataToHazelcast(testKey);

        assertThat(classUnderTest.readAllDataFromHazelcast().size()).isEqualTo(0);
    }


    @Test
    public void test_readAllDataFromHazelcast_add_one_entry_to_map_and_read_this_entry() throws IOException {
        String testValue = locoJsonMapper.map(testJourney);
        String testKey = testJourney.getId().toString();
        classUnderTest.writeDataToHazelcast(testKey, testValue);

        String result = classUnderTest.readAllDataFromHazelcast().get(testKey);
        Journey resultObject = locoJsonMapper.mapJsonToJourney(result);

        assertThat(classUnderTest.readAllDataFromHazelcast().size()).isEqualTo(1);
        assertThat(resultObject).isEqualToIgnoringGivenFields(testJourney, "start", "destination", "betweenHolds", "price", "priceWithCommision");
        classUnderTest.deleteDataToHazelcast(testKey);
        assertThat(classUnderTest.readAllDataFromHazelcast().size()).isEqualTo(0);
    }

}
