package com.practice.freelancebd;

public class Bidder {

    private String bidder, bidAmount, bidDay, bidDescription, bidderProfileImageLink;

    public Bidder() {
    }

    public Bidder(String bidder, String bidAmount, String bidDay, String bidDescription, String bidderProfileImageLink) {
        this.bidder = bidder;
        this.bidAmount = bidAmount;
        this.bidDay = bidDay;
        this.bidDescription = bidDescription;
        this.bidderProfileImageLink = bidderProfileImageLink;
    }

    public String getBidder() {
        return bidder;
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

    public String getBidderProfileImageLink() {
        return bidderProfileImageLink;
    }
}
