package com.practice.freelancebd;

public class Post {
    private String employerName;
    private String type;
    private String title;
    private String postStatus;
    private String budget;
    private String applyLastDay;
    private String applyLastMonth;
    private String applyLastYear;
    private String description;

    public Post() {
    }

    public Post(String employerName, String type, String title, String postStatus, String budget, String applyLastDay, String applyLastMonth, String applyLastYear, String description) {
        this.employerName = employerName;
        this.type = type;
        this.title = title;
        this.postStatus = postStatus;
        this.budget = budget;
        this.applyLastDay = applyLastDay;
        this.applyLastMonth = applyLastMonth;
        this.applyLastYear = applyLastYear;
        this.description = description;
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
}


