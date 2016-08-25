package com.airtel.gurinder.domain;

import com.airtel.gurinder.domain.request.ScheduleRequestBodyDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by gurinder on 9/7/16.
 */
@Entity
@Table(name = "schedule_task")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScheduleTask extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "url")
    private String url;

    @Column(name = "schedule_request_method")
    private String requestMethod;

    @Column(name = "callback_at")
    private Date callBackAt;

    @Column(name = "auth_token")
    private String authToken;

    @Column(name = "retry_count")
    private Integer retryCount;

    @Embedded
    private ScheduleRequestBodyDto requestBody;

}
