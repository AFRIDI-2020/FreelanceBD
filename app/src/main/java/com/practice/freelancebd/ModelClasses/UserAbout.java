package com.practice.freelancebd.ModelClasses;

public class UserAbout {

    private String fullName;
    private String sex;
    private String occupation;
    private String mobile;
    private String skills;
    private String interestedCategory1;

    public String getFullName() {
        return fullName;
    }

    public String getSex() {
        return sex;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getMobile() {
        return mobile;
    }

    public String getSkills() {
        return skills;
    }

    public String getInterestedCategory1() {
        return interestedCategory1;
    }

    public String getInterestedCategory2() {
        return interestedCategory2;
    }

    public UserAbout(String fullName, String sex, String occupation, String mobile, String skills, String interestedCategory1, String interestedCategory2) {
        this.fullName = fullName;
        this.sex = sex;
        this.occupation = occupation;
        this.mobile = mobile;
        this.skills = skills;
        this.interestedCategory1 = interestedCategory1;
        this.interestedCategory2 = interestedCategory2;
    }

    private String interestedCategory2;

    public UserAbout() {
    }
}
