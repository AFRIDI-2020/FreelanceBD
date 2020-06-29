package com.practice.freelancebd.ModelClasses;

public class AllUserPost {

    private String fullName, date, time,title, budget, duration, status,profileImage;

    public String getFullName() {
        return fullName;
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

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
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

    public AllUserPost(String fullName, String date, String time, String title, String budget, String duration, String status, String profileImage) {
        this.fullName = fullName;
        this.date = date;
        this.time = time;
        this.title = title;
        this.budget = budget;
        this.duration = duration;
        this.status = status;
        this.profileImage = profileImage;
    }

    public AllUserPost() {
    }
}
