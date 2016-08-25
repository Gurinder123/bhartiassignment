package com.airtel.gurinder.controller;

import com.airtel.gurinder.Constants;
import com.airtel.gurinder.domain.MyResponse;
import com.airtel.gurinder.domain.ScheduleStatusResponse;
import com.airtel.gurinder.domain.request.ScheduleRequestDto;
import com.airtel.gurinder.domain.request.ScheduleUpdateRequestBody;
import com.airtel.gurinder.domain.response.ScheduleResponseDto;
import com.airtel.gurinder.service.ScheduledService;
import com.airtel.gurinder.utility.RandomTokenUtililty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Created by gurinder on 9/7/16.
 */

@RestController
@RequestMapping("/")
@Async
public class TaskSchedulingController {

    private ScheduledService scheduledService;

    @Autowired
    public TaskSchedulingController(ScheduledService scheduledService) {
        this.scheduledService = scheduledService;
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity startTask(@RequestBody ScheduleRequestDto requestBody, @RequestHeader(Constants.AUTH_TOKEN) String authToken) {
        String status = scheduledService.findByAuthToken(authToken);
        if ("new Request".equals(status) || "Allowed".equals(status)) {
            String token = RandomTokenUtililty.getRandomToken();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Auth-Token", token);
            Optional<ScheduleResponseDto> newTask = scheduledService.createNewTask(requestBody, token);
            if (newTask.isPresent())
                return new ResponseEntity(newTask.get(), HttpStatus.ACCEPTED);
            else
                return new ResponseEntity(new ScheduleStatusResponse(MyResponse.FAILURE.getStatus()), HttpStatus.ACCEPTED);
        }

        return new ResponseEntity(new ScheduleStatusResponse(MyResponse.FAILURE.getStatus()), HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.PUT)
    public ResponseEntity updateTaskInfo(@RequestParam("id") Integer id, @RequestBody ScheduleUpdateRequestBody updateRequestBody) {
        Optional<ScheduleResponseDto> updateInfo = scheduledService.upDateTaskInfo(id, updateRequestBody);
        if (updateInfo.isPresent())
            return new ResponseEntity(updateInfo.get(), HttpStatus.ACCEPTED);
        else
            return new ResponseEntity(new ScheduleStatusResponse(MyResponse.FAILURE.getStatus()), HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity getTaskInfo(@RequestParam("id") Integer id) {
        Optional<ScheduleResponseDto> record = scheduledService.findById(id);
        if (record.isPresent())
            return new ResponseEntity(record.get(), HttpStatus.ACCEPTED);
        else
            return new ResponseEntity(new ScheduleStatusResponse(MyResponse.FAILURE.getStatus()), HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.DELETE)
    public ResponseEntity<MyResponse> deleteTask(@RequestParam("id") Integer id) {
        boolean recordDeleted = scheduledService.deleteById(id);
        if (recordDeleted)
            return new ResponseEntity(new ScheduleStatusResponse(MyResponse.OK.getStatus()), HttpStatus.ACCEPTED);
        else
            return new ResponseEntity(new ScheduleStatusResponse(MyResponse.FAILURE.getStatus()), HttpStatus.ACCEPTED);
    }

}
