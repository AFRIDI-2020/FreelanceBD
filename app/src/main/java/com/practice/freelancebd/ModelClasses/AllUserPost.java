package com.practice.freelancebd.ModelClasses;

public class AllUserPost {

    private String userName;
    private String date;
    private String time;
    private String title;
    private String budget;
    private String duration;
    private String status;
    private String profileImage;

    public AllUserPost() {
    }

    public AllUserPost(String userName, String date, String time, String title, String budget, String duration, String status, String profileImage) {
        this.userName = userName;
        this.date = date;
        this.time = time;
        this.title = title;
        this.budget = budget;
        this.duration = duration;
        this.status = status;
        this.profileImage = profileImage;
    }

    public String getUserName() {
        return userName;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String getBudget() {
        return budget;
    }

    public String getDuration() {
        return duration;
    }

    public String getStatus() {
        return status;
    }

    public String getProfileImage() {
        return profileImage;
    }
}
