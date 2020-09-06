package com.practice.freelancebd;

public class AllMyClass {
    private String title;
    private String type;
    private String postStatus;
    private String budget;
    private String applyLastDay;
    private String applyLastMonth;
    private String applyLastYear;
    private String description;

    public AllMyClass() {
    }

    public AllMyClass(String title, String type, String postStatus, String budget, String applyLastDay, String applyLastMonth, String applyLastYear, String description) {
        this.title = title;
        this.type = type;
        this.postStatus = postStatus;
        this.budget = budget;
        this.applyLastDay = applyLastDay;
        this.applyLastMonth = applyLastMonth;
        this.applyLastYear = applyLastYear;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
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
