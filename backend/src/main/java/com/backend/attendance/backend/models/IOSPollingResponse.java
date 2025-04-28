package com.backend.attendance.backend.models;

public class IOSPollingResponse {
    private Boolean success;

    public IOSPollingResponse(Boolean success) {
        this.success = success;
    }

    public IOSPollingResponse() {}

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
