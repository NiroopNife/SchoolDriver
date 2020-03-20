package com.marty.track.Login;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class LoginResponse {

    @SerializedName("resp")
    public List<DriverDetails> resp = new ArrayList();
    @SerializedName("status")
    private String status;

    public class DriverDetails {
        @SerializedName("dr_password")
        @Expose
        private String dr_password;
        @SerializedName("pdloc_latitude")
        private String pdloc_latitude;
        @SerializedName("pdloc_longitude")
        private String pdloc_longitude;
        @SerializedName("pdloc_name")
        private String pdloc_name;
        @SerializedName("dri_address")
        @Expose
        private String dri_address;
        @SerializedName("dri_cdate")
        @Expose
        private String dri_cdate;
        @SerializedName("dri_cid")
        @Expose
        private Integer dri_cid;
        @SerializedName("dri_city")
        @Expose
        private String dri_city;
        @SerializedName("dri_country")
        @Expose
        private String dri_country;
        @SerializedName("dri_dob")
        @Expose
        private String dri_dob;
        @SerializedName("dri_email")
        @Expose
        private String dri_email;
        @SerializedName("dri_fname")
        @Expose
        private String dri_fname;
        @SerializedName("dri_isactive")
        @Expose
        private Integer dri_isactive;
        @SerializedName("dri_join_date")
        @Expose
        private String dri_join_date;
        @SerializedName("dri_lname")
        @Expose
        private String dri_lname;
        @SerializedName("dri_mdate")
        @Expose
        private String dri_mdate;
        @SerializedName("dri_mid")
        @Expose
        private Integer dri_mid;
        @SerializedName("dri_number")
        @Expose
        private String dri_number;
        @SerializedName("dri_phone")
        @Expose
        private String dri_phone;
        @SerializedName("dri_pkid")
        @Expose
        private String dri_pkid;
        @SerializedName("dri_postcode")
        @Expose
        private String dri_postcode;
        @SerializedName("dri_school_fkid")
        @Expose
        private String dri_school_fkid;

        public DriverDetails() {
        }

        public String getDri_pkid() {
            return this.dri_pkid;
        }

        public void setDri_pkid(String dri_pkid2) {
            this.dri_pkid = dri_pkid2;
        }

        public String getDri_school_fkid() {
            return this.dri_school_fkid;
        }

        public void setDri_school_fkid(String dri_school_fkid2) {
            this.dri_school_fkid = dri_school_fkid2;
        }

        public String getDri_number() {
            return this.dri_number;
        }

        public void setDri_number(String dri_number2) {
            this.dri_number = dri_number2;
        }

        public String getDri_phone() {
            return this.dri_phone;
        }

        public void setDri_phone(String dri_phone2) {
            this.dri_phone = dri_phone2;
        }

        public String getDri_fname() {
            return this.dri_fname;
        }

        public void setDri_fname(String dri_fname2) {
            this.dri_fname = dri_fname2;
        }

        public String getDri_lname() {
            return this.dri_lname;
        }

        public void setDri_lname(String dri_lname2) {
            this.dri_lname = dri_lname2;
        }

        public String getDri_email() {
            return this.dri_email;
        }

        public void setDri_email(String dri_email2) {
            this.dri_email = dri_email2;
        }

        public String getDr_password() {
            return this.dr_password;
        }

        public void setDr_password(String dr_password2) {
            this.dr_password = dr_password2;
        }

        public String getDri_join_date() {
            return this.dri_join_date;
        }

        public void setDri_join_date(String dri_join_date2) {
            this.dri_join_date = dri_join_date2;
        }

        public String getDri_dob() {
            return this.dri_dob;
        }

        public void setDri_dob(String dri_dob2) {
            this.dri_dob = dri_dob2;
        }

        public String getDri_address() {
            return this.dri_address;
        }

        public void setDri_address(String dri_address2) {
            this.dri_address = dri_address2;
        }

        public String getDri_postcode() {
            return this.dri_postcode;
        }

        public void setDri_postcode(String dri_postcode2) {
            this.dri_postcode = dri_postcode2;
        }

        public String getDri_city() {
            return this.dri_city;
        }

        public void setDri_city(String dri_city2) {
            this.dri_city = dri_city2;
        }

        public String getDri_country() {
            return this.dri_country;
        }

        public void setDri_country(String dri_country2) {
            this.dri_country = dri_country2;
        }

        public String getDri_cdate() {
            return this.dri_cdate;
        }

        public void setDri_cdate(String dri_cdate2) {
            this.dri_cdate = dri_cdate2;
        }

        public Integer getDri_cid() {
            return this.dri_cid;
        }

        public void setDri_cid(Integer dri_cid2) {
            this.dri_cid = dri_cid2;
        }

        public String getDri_mdate() {
            return this.dri_mdate;
        }

        public void setDri_mdate(String dri_mdate2) {
            this.dri_mdate = dri_mdate2;
        }

        public Integer getDri_mid() {
            return this.dri_mid;
        }

        public void setDri_mid(Integer dri_mid2) {
            this.dri_mid = dri_mid2;
        }

        public Integer getDri_isactive() {
            return this.dri_isactive;
        }

        public void setDri_isactive(Integer dri_isactive2) {
            this.dri_isactive = dri_isactive2;
        }
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status2) {
        this.status = status2;
    }

}
