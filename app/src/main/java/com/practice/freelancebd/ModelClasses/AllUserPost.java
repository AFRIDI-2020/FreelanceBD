package com.practice.freelancebd.ModelClasses;

public class AllUserPost {
    private String title;
    private String budget;
    private String employerName;
    private String postStatus;

    public AllUserPost() {
    }

    public AllUserPost(String title, String budget, String employerName, String postStatus) {
        this.title = title;
        this.budget = budget;
        this.employerName = employerName;
        this.postStatus = postStatus;
    }

    public String getTitle() {
        return title;
    }

    public String getBudget() {
        return budget;
    }

    public String getEmployerName() {
        return employerName;
    }

    public String getPostStatus() {
        return postStatus;
    }
}
