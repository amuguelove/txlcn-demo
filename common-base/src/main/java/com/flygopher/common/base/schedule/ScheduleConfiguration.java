package com.flygopher.common.base.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "schedule.enabled", havingValue = "true")
public class ScheduleConfiguration {

    @PostConstruct
    public void setUp() {
        log.info("---------------------Enabled Schedule-----------------------------");
    }

}
