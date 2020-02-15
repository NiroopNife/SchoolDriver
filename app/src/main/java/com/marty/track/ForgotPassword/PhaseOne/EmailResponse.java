package com.marty.track.ForgotPassword.PhaseOne;

import com.google.gson.annotations.SerializedName;

public class EmailResponse {

    @SerializedName("status")
    private String status;

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status2) {
        this.status = status2;
    }

}
