package com.airtel.gurinder.domain.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by gurinder on 9/7/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScheduleUpdateRequestBody {
    @JsonProperty("url")
    private String url;

    @JsonProperty("callback_at")
    private String callBackAt;

    @JsonProperty("request_method")
    private String requestMethod;

}
