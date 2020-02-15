package com.marty.track.StudentsPickAndDrop.StudentsPickDrop;

public class StudentsPickDropModel {

    private String schoolPDLat;
    private String schoolPDLng;
    private String pdName;
    private String pdLatitude;
    private String pdLongitude;

    public StudentsPickDropModel(String schoolPDLat, String schoolPDLng, String pdName, String pdLatitude, String pdLongitude) {
        this.schoolPDLat = schoolPDLat;
        this.schoolPDLng = schoolPDLng;
        this.pdName = pdName;
        this.pdLatitude = pdLatitude;
        this.pdLongitude = pdLongitude;
    }

    public String getSchoolPDLat() {
        return schoolPDLat;
    }

    public String getSchoolPDLng() {
        return schoolPDLng;
    }

    public String getPdName() {
        return pdName;
    }

    public String getPdLatitude() {
        return pdLatitude;
    }

    public String getPdLongitude() {
        return pdLongitude;
    }
}
