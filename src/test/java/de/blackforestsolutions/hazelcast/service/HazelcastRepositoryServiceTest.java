package de.blackforestsolutions.hazelcast.service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.test.TestHazelcastInstanceFactory;
import de.blackforestsolutions.datamodel.Journey;
import de.blackforestsolutions.datamodel.util.LocoJsonMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static de.blackforestsolutions.hazelcast.objectmothers.JourneyObjectMother.getJourneyBerlinHamburg;
import static org.assertj.core.api.Assertions.assertThat;

public class HazelcastRepositoryServiceTest {

    private final TestHazelcastInstanceFactory factory = new TestHazelcastInstanceFactory(1);

    private final HazelcastInstance hazelcastMock = factory.newHazelcastInstance();

    private final HazelcastRepositoryService classUnderTest = new HazelcastRepositoryServiceImpl(hazelcastMock);

    private final Journey testJourney = getJourneyBerlinHamburg();

    private final LocoJsonMapper locoJsonMapper = new LocoJsonMapper();


    @Test
    public void test_isWriteDataToHazelcastSuccessfullyWith_provoke_exception_returns_false() {
        String testValue = "wrongValue";
        String testKey = "wrongKey";

        boolean result = classUnderTest.isWriteDataToHazelcastSuccessfullyWith(testKey, testValue);

        assertThat(result).isFalse();
    }

    @Test
    public void test_writeDataToHazelcast_add_one_entry_to_map_and_read_this_entry() throws IOException {
        String testValue = locoJsonMapper.map(testJourney);
        String testKey = testJourney.getId().toString();
        classUnderTest.isWriteDataToHazelcastSuccessfullyWith(testKey, testValue);

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
        classUnderTest.isWriteDataToHazelcastSuccessfullyWith(testKey, testValue);

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
        classUnderTest.isWriteDataToHazelcastSuccessfullyWith(testKey, testValue);

        String result = classUnderTest.readAllDataFromHazelcast().get(testKey);
        Journey resultObject = locoJsonMapper.mapJsonToJourney(result);

        assertThat(classUnderTest.readAllDataFromHazelcast().size()).isEqualTo(1);
        assertThat(resultObject).isEqualToIgnoringGivenFields(testJourney, "start", "destination", "betweenHolds", "price", "priceWithCommision");
        classUnderTest.deleteDataToHazelcast(testKey);
        assertThat(classUnderTest.readAllDataFromHazelcast().size()).isEqualTo(0);
    }

}
