package com.marty.track.Students;

public class StudentsModel {

    private String studentFName;
    private String studentLName;
    private String admissionNumber;
    private String classNumber;
    private String sectionName;
    private String rollNumber;
    private String guardianName;
    private String guardianPhone;
    private String address;
    private String city;
    private String country;

    public StudentsModel(String studentFName, String studentLName, String admissionNumber, String classNumber,
                         String sectionName, String rollNumber, String guardianName, String guardianPhone,
                         String address, String city, String country){
        this.studentFName = studentFName;
        this.studentLName = studentLName;
        this.admissionNumber = admissionNumber;
        this.classNumber = classNumber;
        this.sectionName = sectionName;
        this.rollNumber = rollNumber;
        this.guardianName = guardianName;
        this.guardianPhone = guardianPhone;
        this.address = address;
        this.city = city;
        this.country = country;
    }

    public String getStudentFName() {
        return studentFName;
    }

    public String getStudentLName() {
        return studentLName;
    }

    public String getAdmissionNumber() {
        return admissionNumber;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public String getGuardianPhone() {
        return guardianPhone;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}
