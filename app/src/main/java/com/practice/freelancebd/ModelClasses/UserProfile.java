package com.practice.freelancebd.ModelClasses;

public class UserProfile {

    private String fullName, professionalTag, aboutUser, profileImageLink;

    public UserProfile() {
    }


    public UserProfile(String fullName, String professionalTag, String aboutUser, String profileImageLink) {
        this.fullName = fullName;
        this.professionalTag = professionalTag;
        this.aboutUser = aboutUser;
        this.profileImageLink = profileImageLink;
    }

    public String getFullName() {
        return fullName;
    }

    public String getProfessionalTag() {
        return professionalTag;
    }

    public String getAboutUser() {
        return aboutUser;
    }

    public String getProfileImageLink() {
        return profileImageLink;
    }
}
