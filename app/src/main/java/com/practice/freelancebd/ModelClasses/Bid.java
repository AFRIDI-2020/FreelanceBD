package com.practice.freelancebd.ModelClasses;

public class Bid {

    private String bidAmount, bidDay, bidDescription, bidStatus,bidder, bidderProfileImageLink;

    public Bid() {
    }

    public Bid(String bidAmount, String bidDay, String bidDescription, String bidStatus, String bidder, String bidderProfileImageLink) {
        this.bidAmount = bidAmount;
        this.bidDay = bidDay;
        this.bidDescription = bidDescription;
        this.bidStatus = bidStatus;
        this.bidder = bidder;
        this.bidderProfileImageLink = bidderProfileImageLink;
    }

    public String getBidAmount() {
        return bidAmount;
    }

    public String getBidDay() {
        return bidDay;
    }

    public String getBidDescription() {
        return bidDescription;
    }

    public String getBidStatus() {
        return bidStatus;
    }

    public String getBidder() {
        return bidder;
    }

    public String getBidderProfileImageLink() {
        return bidderProfileImageLink;
    }
}
