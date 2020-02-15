package com.marty.track.StudentsPickAndDrop.StudentsPick;

public class StudentsPickModel {

    private String studentFName;
    private String studentLName;
    private String classNumber;
    private String sectionName;
    private String rollNumber;

    public StudentsPickModel(String studentFName, String studentLName,  String classNumber,
                             String sectionName, String rollNumber) {
        this.studentFName = studentFName;
        this.studentLName = studentLName;
        this.classNumber = classNumber;
        this.sectionName = sectionName;
        this.rollNumber = rollNumber;

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

    public String getRollNumber() {
        return rollNumber;
    }


}
