package com.practice.freelancebd.ModelClasses;

public class PersonalInfo {

    private String name;
    private String email;
    private String mobileNo;
    private String description;
    private String professionalTag;

    public PersonalInfo() {
    }

    public PersonalInfo(String name, String email, String mobileNo, String description, String professionalTag) {
        this.name = name;
        this.email = email;
        this.mobileNo = mobileNo;
        this.description = description;
        this.professionalTag = professionalTag;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getDescription() {
        return description;
    }

    public String getProfessionalTag() {
        return professionalTag;
    }
}
