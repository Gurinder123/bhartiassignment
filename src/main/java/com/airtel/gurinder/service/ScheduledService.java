package com.airtel.gurinder.service;

import com.airtel.gurinder.domain.ScheduleTask;
import com.airtel.gurinder.domain.request.ScheduleRequestDto;
import com.airtel.gurinder.domain.request.ScheduleUpdateRequestBody;
import com.airtel.gurinder.domain.response.ScheduleResponseBodyDto;
import com.airtel.gurinder.domain.response.ScheduleResponseDto;
import com.airtel.gurinder.repository.ScheduleTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by gurinder on 9/7/16.
 */

@Service
public class ScheduledService {

    private ScheduleTaskRepository scheduleTaskRepository;

    @Autowired
    public ScheduledService(ScheduleTaskRepository scheduleTaskRepository) {
        this.scheduleTaskRepository = scheduleTaskRepository;
    }


    public Optional<ScheduleResponseDto> createNewTask(ScheduleRequestDto requestBody, String token) {
        ScheduleTask scheduleTask = new ScheduleTask();
        scheduleTask.setUrl(requestBody.getUrl());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            scheduleTask.setCallBackAt(formatter.parse(requestBody.getCallBackAt().substring(0, requestBody.getCallBackAt().length() - 6)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        scheduleTask.setRequestMethod(requestBody.getRequestMethod());
        scheduleTask.setRequestBody(requestBody.getRequestBody());
        scheduleTask.setAuthToken(token);
        scheduleTask.setRetryCount(new Integer(1));
        System.out.println("*************saving now*************");
        scheduleTask = scheduleTaskRepository.save(scheduleTask);
        System.out.println("*************saving done*************");
        if (scheduleTask != null)
            return Optional.of(createResponse(scheduleTask));
        else
            return Optional.empty();

    }

    private ScheduleResponseDto createResponse(ScheduleTask scheduleTask) {
        ScheduleResponseBodyDto responseBodyDto = new ScheduleResponseBodyDto();
        ScheduleResponseDto dto = new ScheduleResponseDto();
        responseBodyDto.setUrl(scheduleTask.getUrl());
        responseBodyDto.setCallBackAt(scheduleTask.getCallBackAt().toString());
        responseBodyDto.setCallStatus(scheduleTask.isStatus());
        responseBodyDto.setCreatedAT(scheduleTask.getCreatedAt().toString());
        responseBodyDto.setReferenceId(String.valueOf(scheduleTask.getId()));
        responseBodyDto.setRequestMethod(scheduleTask.getRequestMethod());
        responseBodyDto.setRequestBody(scheduleTask.getRequestBody());
        dto.setScheduleResponseBodyDto(responseBodyDto);
        return dto;
    }

    public Optional<ScheduleResponseDto> findById(Integer id) {
        ScheduleTask scheduleTask = scheduleTaskRepository.findOne(id);
        if (scheduleTask != null)
            return Optional.of(createResponse(scheduleTask));
        else
            return Optional.empty();

    }

    public boolean deleteById(Integer id) {
        ScheduleTask scheduleTask = scheduleTaskRepository.findOne(id);
        if (scheduleTask != null) {
            scheduleTaskRepository.delete(id);
            return true;
        }
        return false;
    }

    public Optional<ScheduleResponseDto> upDateTaskInfo(Integer id, ScheduleUpdateRequestBody updateRequestBody) {
        ScheduleTask scheduleTask = scheduleTaskRepository.findOne(id);

        if (scheduleTask != null) {
            scheduleTask.setUrl(updateRequestBody.getUrl());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            try {
                scheduleTask.setCallBackAt(formatter.parse(updateRequestBody.getCallBackAt().substring(0, updateRequestBody.getCallBackAt().length() - 6)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            scheduleTask.setRequestMethod(updateRequestBody.getRequestMethod());
            scheduleTask.setRetryCount((scheduleTask.getRetryCount()) + 1);
            scheduleTask = scheduleTaskRepository.save(scheduleTask);
            if (scheduleTask != null)
                return Optional.of(createResponse(scheduleTask));
            else
                return Optional.empty();
        } else
            return Optional.empty();

    }


    public Optional findAll() {
        List<ScheduleTask> allScheduleTasks = scheduleTaskRepository.findAll();
        if (!allScheduleTasks.isEmpty())
            return Optional.of(allScheduleTasks);
        else
            return Optional.empty();

    }

    public void save(List<ScheduleTask> scheduleTaskList) {
        scheduleTaskRepository.save(scheduleTaskList);
    }

    public String findByAuthToken(String authToken) {
        ScheduleTask byAuthToken = scheduleTaskRepository.findByAuthToken(authToken);
        if (byAuthToken != null) {
            Date createdAt = byAuthToken.getCreatedAt();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, -1);
            Date oneMinuteBack = calendar.getTime();
            int i = createdAt.compareTo(oneMinuteBack);
            if (i < 1 && byAuthToken.getRetryCount() < 10)
                return "Allowed";
            else
                return "Not Allowed";
        }
        return "new Request";
    }
}
