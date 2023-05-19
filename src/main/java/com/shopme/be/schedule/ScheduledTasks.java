package com.shopme.be.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@Slf4j
public class ScheduledTasks {
    // Spring uses a local single-threaded scheduler to run the tasks
    // Config in AsyncConfig
    // fixedRate: every
    // fixedDelay: after last success
    // initialDelay: after start
    // cron: second(0-59) minus(0-59) hours(0-23) day(1-31) month(1-12 or JAN-DEC) day of week (0-7 or MON-SUN)
    // * for every, */number for every number of this
    // - for between
    // , for list
    // MON#2 the second Monday of week

    @Scheduled(cron = "0 */5 7 * * MON-SAT") // every 5 minus at 7h from Mon to Sat
    public void scheduleTaskWithCronExpression() {
        log.info("Wake up!!!");
    }
}
