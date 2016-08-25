package com.airtel.gurinder.schedulers;

import com.airtel.gurinder.domain.ScheduleTask;
import com.airtel.gurinder.service.ScheduledService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by gurinder on 9/7/16.
 */

@Component
@EnableScheduling
public class ScheduleTaskDandlingScheduler {
    private ScheduledService scheduledService;

    @Autowired
    public ScheduleTaskDandlingScheduler(ScheduledService scheduledService) {
        this.scheduledService = scheduledService;
    }


    @Scheduled(fixedRate = 900000)
    public void run() {
        List<ScheduleTask> scheduleTaskList = null;
        Optional<ScheduleTask> all = scheduledService.findAll();
        if (all.isPresent()) {
            scheduleTaskList = (List<ScheduleTask>) all.get();
            System.out.println("********calling method******");
            scheduleTaskList.stream().filter(scheduleTask1 -> scheduleTask1.getCallBackAt().equals(new Date())).forEach(scheduleTask -> scheduleTask.setStatus(true));
            scheduledService.save(scheduleTaskList);
        }

    }
}
