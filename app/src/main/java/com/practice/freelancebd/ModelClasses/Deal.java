package com.practice.freelancebd.ModelClasses;

public class Deal {
    private String employerName;
    private String bidderName;
    private String jobTitle;
    private String jobAmount;
    private String jobDay;
    private String postKey;

    public Deal() {
    }

    public Deal(String employerName, String bidderName, String jobTitle, String jobAmount, String jobDay, String postKey) {
        this.employerName = employerName;
        this.bidderName = bidderName;
        this.jobTitle = jobTitle;
        this.jobAmount = jobAmount;
        this.jobDay = jobDay;
        this.postKey = postKey;
    }

    public String getEmployerName() {
        return employerName;
    }

    public String getBidderName() {
        return bidderName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getJobAmount() {
        return jobAmount;
    }

    public String getJobDay() {
        return jobDay;
    }

    public String getPostKey() {
        return postKey;
    }
}
