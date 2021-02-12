package com.practice.freelancebd.ModelClasses;

public class Bid {

    private String bidAmount, bidDay, bidDescription, bidStatus,bidder, bidderProfileImageLink,bidderId,postKey;

    public Bid() {
    }

    public Bid(String bidAmount, String bidDay, String bidDescription, String bidStatus, String bidder, String bidderProfileImageLink, String bidderId, String postKey) {
        this.bidAmount = bidAmount;
        this.bidDay = bidDay;
        this.bidDescription = bidDescription;
        this.bidStatus = bidStatus;
        this.bidder = bidder;
        this.bidderProfileImageLink = bidderProfileImageLink;
        this.bidderId = bidderId;
        this.postKey = postKey;
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

    public String getBidderId() {
        return bidderId;
    }

    public String getPostKey() {
        return postKey;
    }
}
