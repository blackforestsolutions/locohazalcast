package de.blackforestsolutions.hazelcast.controller;

import com.hazelcast.core.HazelcastInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "logging.level.org.springframework.web=DEBUG")
@AutoConfigureMockMvc
public class HazelcastControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @Qualifier("hazelcastInstance")
    @Autowired
    HazelcastInstance hazelcastInstance;

    @Test
    public void writeDataToHazelcast_with_key_and_value_is_stored_and_returns_succes_string() throws Exception {
        mockMvc.perform(post("/hazelcast/write-data?key=matthias&value=burkert"))
                .andExpect(status().isOk());
    }

    @Test
    public void writeDataToHazelcast_with_key_but_not_value_is_stored_and_returns_400() throws Exception {
        mockMvc.perform(post("/hazelcast/write-data?key=matthias"))
                .andExpect(status().is(400));
    }

    @Test
    public void writeDataToHazelcast_with_no_key_but_value_is_stored_and_returns_400() throws Exception {
        mockMvc.perform(post("/hazelcast/write-data?value=burkert"))
                .andExpect(status().is(400));
    }

    @Test
    public void readDataFromHazelcast_with_key_returns_message() throws Exception {
        mockMvc.perform(post("/hazelcast/write-data?key=matthias&value=burkert"));
        mockMvc.perform(get("/hazelcast/read-data?key=matthias"))
                .andExpect(status().isOk());
    }

    @Test
    public void readDataFromHazelcast_with_key_but_not_value_is_stored_and_returns_400() throws Exception {
        mockMvc.perform(get("/hazelcast/read-data"))
                .andExpect(status().is(400));
    }

    @Test
    public void readAllDataFromHazelcast_delivers_map_with_data() throws Exception {
        mockMvc.perform(get("/hazelcast/read-all-data"))
                .andExpect(status().isOk());
    }
}

