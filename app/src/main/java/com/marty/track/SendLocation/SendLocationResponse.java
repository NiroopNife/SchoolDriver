package com.marty.track.SendLocation;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SendLocationResponse {

    @SerializedName("resp")
    public List<LocationDetailsResponse> resp = new ArrayList<>();

    @SerializedName("status")
    private String status;

    public class LocationDetailsResponse{

        @SerializedName("loc_longitude")
        private String loc_longitude;

        @SerializedName("loc_latitude")
        private String loc_latitude;

        @SerializedName("loc_driver_id")
        private String loc_driver_id;

        @SerializedName("loc_school_id")
        private String loc_school_id;

        public LocationDetailsResponse(){

        }

        public String getLoc_longitude() {
            return loc_longitude;
        }

        public String getLoc_latitude() {
            return loc_latitude;
        }

        public String getLoc_driver_id() {
            return loc_driver_id;
        }

        public String getLoc_school_id() {
            return loc_school_id;
        }
    }

    public List<LocationDetailsResponse> getResp() {
        return resp;
    }

    public String getStatus() {
        return status;
    }
}
