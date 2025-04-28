package com.backend.attendance.backend.models;

public class IOSPollingRequest {
    private String email;
    private String bssid;

    public IOSPollingRequest() {}

    public IOSPollingRequest(String email, String bssid) {
        this.email = email;
        this.bssid = bssid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }
}
