package de.blackforestsolutions.hazelcast.configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration {

    private static final int HAZELCAST_SIZE = 200;
    private static final int TIME_TO_LIVE_IN_SECONDS = -1;

    @Bean
    public Config hazelCastConfig() {
        Config config = new Config();
        config.setInstanceName("hazelcast-instance")
                .addMapConfig(
                        new MapConfig()
                                .setName("configuration")
                                .setMaxSizeConfig(new MaxSizeConfig(HAZELCAST_SIZE, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                                .setEvictionPolicy(EvictionPolicy.LRU)
                                .setTimeToLiveSeconds(TIME_TO_LIVE_IN_SECONDS));
        return config;
    }



}
