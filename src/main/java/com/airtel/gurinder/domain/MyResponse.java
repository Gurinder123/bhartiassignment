package com.airtel.gurinder.domain;

/**
 * Created by gurinder on 9/7/16.
 */
public enum MyResponse {
    OK("ok"), FAILURE("failure record not exist");

    private String status;

    MyResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
