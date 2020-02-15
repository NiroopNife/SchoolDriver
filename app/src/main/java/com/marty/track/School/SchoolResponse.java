package com.marty.track.School;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SchoolResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("res")
    public List<SchoolDetails> res = new ArrayList<>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public class SchoolDetails{

        @SerializedName("ad_pkid")
        private String school_id;

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }
    }
}
