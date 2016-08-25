package com.airtel.gurinder.repository;

import com.airtel.gurinder.domain.ScheduleTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by gurinder on 9/7/16.
 */
@Repository
public interface ScheduleTaskRepository extends JpaRepository<ScheduleTask, Integer> {
    ScheduleTask findByAuthToken(String authToken);
}
