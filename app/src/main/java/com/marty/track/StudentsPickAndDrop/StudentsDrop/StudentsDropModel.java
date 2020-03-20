package com.marty.track.StudentsPickAndDrop.StudentsDrop;

public class StudentsDropModel {

    private String studentFName;
    private String studentLName;
    private String classNumber;
    private String sectionName;
    private String pdLocation;
    private String ap_guardian_phone;
    private String pdloc_latitude;
    private String pdloc_longitude;

    public StudentsDropModel(String studentFName, String studentLName, String classNumber, String sectionName,
                             String pdLocation, String ap_guardian_phone, String pdloc_latitude, String pdloc_longitude) {
        this.studentFName = studentFName;
        this.studentLName = studentLName;
        this.classNumber = classNumber;
        this.sectionName = sectionName;
        this.pdLocation = pdLocation;
        this.ap_guardian_phone = ap_guardian_phone;
        this.pdloc_latitude = pdloc_latitude;
        this.pdloc_longitude = pdloc_longitude;
    }

    public String getStudentFName() {
        return studentFName;
    }

    public String getStudentLName() {
        return studentLName;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getAp_guardian_phone() {
        return ap_guardian_phone;
    }

    public String getPdLocation() {
        return pdLocation;
    }

    public String getPdloc_latitude() {
        return pdloc_latitude;
    }

    public String getPdloc_longitude() {
        return pdloc_longitude;
    }
}
