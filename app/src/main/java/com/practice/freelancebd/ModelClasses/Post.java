package com.practice.freelancebd.ModelClasses;

public class Post {
    private String profileImageLink;
    private String employerName;
    private String type;
    private String title;
    private String postStatus;
    private String budget;
    private String applyLastDay;
    private String applyLastMonth;
    private String applyLastYear;
    private String description;
    private String userID;

    public Post() {
    }

    public Post(String profileImageLink, String employerName, String type, String title, String postStatus, String budget, String applyLastDay, String applyLastMonth, String applyLastYear, String description, String userID) {
        this.profileImageLink = profileImageLink;
        this.employerName = employerName;
        this.type = type;
        this.title = title;
        this.postStatus = postStatus;
        this.budget = budget;
        this.applyLastDay = applyLastDay;
        this.applyLastMonth = applyLastMonth;
        this.applyLastYear = applyLastYear;
        this.description = description;
        this.userID = userID;
    }


    public String getProfileImageLink() {
        return profileImageLink;
    }

    public String getEmployerName() {
        return employerName;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getPostStatus() {
        return postStatus;
    }

    public String getBudget() {
        return budget;
    }

    public String getApplyLastDay() {
        return applyLastDay;
    }

    public String getApplyLastMonth() {
        return applyLastMonth;
    }

    public String getApplyLastYear() {
        return applyLastYear;
    }

    public String getDescription() {
        return description;
    }

    public String getUserID() {
        return userID;
    }
}


