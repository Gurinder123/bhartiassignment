package com.airtel.gurinder.domain.response;

import com.airtel.gurinder.domain.request.ScheduleRequestBodyDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by gurinder on 9/7/16.
 */
@JsonInclude(JsonInclude.Include.ALWAYS)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScheduleResponseBodyDto {

    @JsonProperty("callback_at")
    private String callBackAt;

    @JsonProperty("call_status")
    private boolean callStatus;

    @JsonProperty("created_at")
    private String createdAT;

    @JsonProperty("request_body")
    private ScheduleRequestBodyDto requestBody;

    @JsonProperty("errors")
    private String errors;

    @JsonProperty("reference_id")
    private String referenceId;

    @JsonProperty("request_method")
    private String requestMethod;

    @JsonProperty("id")
    private Integer id = 10;

    @JsonProperty("updated_at")
    private String updatedAT;

    @JsonProperty("url")
    private String url;

}

