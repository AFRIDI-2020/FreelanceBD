package com.practice.freelancebd.ModelClasses;

public class JobPost {

    private String userName;
    private String profileImage;
    private String category;
    private String status;
    private String title;
    private String budget;
    private String duration;
    private String description;
    private String date;
    private String time;
    private String image;
    private String pdf;

    public String getUserName() {
        return userName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getCategory() {
        return category;
    }

    public String getStatus() {
        return status;
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

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getImage() {
        return image;
    }

    public String getPdf() {
        return pdf;
    }

    public JobPost(String userName, String profileImage, String category, String status, String title, String budget, String duration, String description, String date, String time, String image, String pdf) {
        this.userName = userName;
        this.profileImage = profileImage;
        this.category = category;
        this.status = status;
        this.title = title;
        this.budget = budget;
        this.duration = duration;
        this.description = description;
        this.date = date;
        this.time = time;
        this.image = image;
        this.pdf = pdf;
    }

    public JobPost() {
    }
}
