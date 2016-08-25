package com.airtel.gurinder.domain.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by gurinder on 9/7/16.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class ScheduleRequestBodyDto implements Serializable {
    @JsonProperty("id")
    private String referenceId;

    @JsonProperty("info")
    private String requestMethod;

}
